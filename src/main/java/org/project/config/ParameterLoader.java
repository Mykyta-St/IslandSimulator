package org.project.config;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import org.project.abstraction.annotations.Config;
import org.project.entity.map.Island;
import org.project.entity.organism.Organism;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is responsible for loading parameters from config .xml files.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Loading and re-writing parameters from-to config .xml files</li>
 * </ul>
 */
public class ParameterLoader
{
    private static ParameterLoader instance;
    @Getter
    private Map <Class<? extends Organism>, Map<String, String>> organismParameters;
    @Getter
    private Map<String, String> islandParameters;

    private ParameterLoader ()
    {
        createNullParametersMap();
    }

    public static ParameterLoader getInstance() {
        if (instance == null) {
            instance = new ParameterLoader();
        }
        return instance;
    }
    private void createNullParametersMap()
    {
        organismParameters = new HashMap<>();
        for (Class <?extends Organism> type: GameObjectsScanner
                            .getInstance()
                            .getAllGameObjectsClasses())
        {
            organismParameters.put(type, new HashMap<>());
            organismParameters.get(type).put("maxQuantity", "0");
            organismParameters.get(type).put("childQuantity", "0");
            organismParameters.get(type).put("maxSpeed", "0");
            organismParameters.get(type).put("maxAge", "0");
            organismParameters.get(type).put("maxWeight", "0");
            organismParameters.get(type).put("maxFood", "0");

        }
        islandParameters = new HashMap<>();
        islandParameters.put("height", "0");
        islandParameters.put("width", "0");
        islandParameters.put("organismQuantity", "0");

    }
    public void updateParameters()
    {
        for (Class<? extends Organism> type: organismParameters.keySet())
        {
            updateOrganismParameters(type);
        }
        updateIslandParameters();

    }
    public void updateOrganismParameters (Class<? extends Organism> type)
    {
        String text = getStringFromConfigFile(type);
        for (String parameterName: organismParameters.get(type).keySet())
        {
            String parameterValue
                    = getParameterFromLine
                    (getLineFromString(text, parameterName), parameterName);
            organismParameters.get(type).replace(parameterName, parameterValue);
        }
    }
    public void updateIslandParameters ()
    {
        String text = getStringFromConfigFile(Island.class);
        for (String parameterName: islandParameters.keySet())
        {
            String parameterValue
                    = getParameterFromLine
                    (getLineFromString(text, parameterName), parameterName);
            if (parameterValue!= null)
            {
                islandParameters.replace(parameterName, parameterValue);
            }

        }
    }

    public Path getPathToConfigFile (@NotNull Class<?> type)
    {
        Config config = type.getAnnotation(Config.class);

        return Paths.get(System.getProperty("user.dir")
                //, "src/main/resources"

                , config.fileName());
    }
    public String getStringFromConfigFile (@NotNull Class<?> type)
    {
        String content;
        try
        (
                Scanner scanner =
                     new Scanner(
                             getPathToConfigFile(type))
        )
        {
            content = scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }

    //TODO remake without returning null if no line found
    private String getLineFromString (String text, String parameterName)
    {
        Scanner scanner = new Scanner(text);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(parameterName)) return line;
        }
        scanner.close();
        return null;
    }
    private String getParameterFromLine(String line, String parameterName)
    {
        if (line == null) return null;
        int startIndex = parameterName.length() + 2;
        int endIndex = line.indexOf("</" + parameterName);
        return line.substring(startIndex, endIndex);
    }
    //TODO add NullPointerException try-catch for Strings
    public void changeConfigXMLFile (Class type, String parameterName, String newValue)
    {
        Path fileName = getPathToConfigFile(type);

        String oldText = getStringFromConfigFile(type);

        String parameterOldLine = getLineFromString(oldText, parameterName);

        String parameterNewLine = changeParameterInLine(parameterOldLine, parameterName, newValue);

        String newXMLText = changeParameterLine(oldText, parameterName, parameterNewLine);

        changeXMLFile (fileName, newXMLText);
    }

    private void changeXMLFile (Path configFile, String newXMLText)
    {

        try
                (FileOutputStream fos = new FileOutputStream(configFile.toFile());
                 DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos)))
        {
            outStream.writeBytes(newXMLText);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     private String changeParameterInLine(String line, String parameterName, String newParameter)
    {
        String start = line.substring(0, parameterName.length() + 2);
        String end = line.substring(line.indexOf("</" + parameterName));
        return start.concat(newParameter).concat(end);
    }
    private String changeParameterLine(String text, String parameterName, String newLine)
    {
        String newText = "";
        Scanner scanner = new Scanner(text);
        while (scanner.hasNextLine()) {
            String oldLine = scanner.nextLine();
            if (!oldLine.contains(parameterName))
            {
                newText = newText.concat(oldLine).concat("\n");
            } else
            {
                newText = newText.concat(newLine).concat("\n");
            }
        }
        scanner.close();
        return newText;
    }
}

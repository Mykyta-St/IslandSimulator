package org.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import org.project.InitGameException;
import org.project.abstraction.annotations.Config;
import org.project.abstraction.annotations.GameObject;
import org.project.entity.organism.Organism;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Scans all classes in the project
 * and returns all classes that are implementing {@link Organism}
 * with {@link GameObject} annotation.
 */
public class GameObjectsScanner {
    @Getter
    private static GameObjectsScanner instance;
    private static final Reflections reflections = new Reflections("org.project.entity");
    private final ObjectMapper objectMapper = new XmlMapper();
    private GameObjectsScanner() {}

    public static GameObjectsScanner getInstance() {
        if (instance == null) {
            instance = new GameObjectsScanner();
        }
        return instance;
    }

    /**
     * Returns all classes that are implementing {@link Organism}
     * annotated with {@link GameObject}.
     *
     * @return set of classes extending Organism and annotated with {@link GameObject}.
     */
    public Set<Class<? extends Organism>> getAllGameObjectsClasses() {
        return reflections.getSubTypesOf(Organism.class)
                .stream()
                .filter(c -> c.isAnnotationPresent(GameObject.class))
                .filter(c -> c.isAnnotationPresent(Config.class))
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Loads prototype object from yaml file.
     *
     * @param type class of prototype object you want to load.
     * @param <T>  type of prototype object.
     * @return prototype object.
     */
    public <T> T loadPrototype(@NotNull Class<T> type) {
        if (!type.isAnnotationPresent(Config.class)) {
            throw new IllegalArgumentException(String.format("Prototype class %s must have @Config annotation", type));
        }
        return loadObject(getURLToConfigFilePath(type), type);
    }

    public URL getURLToConfigFilePath(@NotNull Class<?> type) {
        Config config = type.getAnnotation(Config.class);
        //return type.getClassLoader().getResource(config.fileName());
        URL configFilePath;
        try {
            configFilePath = Paths.get(
                            //System.getProperty("user.dir"), "src/main/resources", config.fileName())
                            System.getProperty("user.dir"),config.fileName())
                    .toUri().toURL();
        } catch (MalformedURLException e) {
            String message = String.format("Cannot find config file",
                    config.fileName());
            throw new InitGameException(message, e);
        }
        return configFilePath;
    }

    private <T> T loadObject(@NotNull URL configFilePath, Class<T> type) {
        T gameObject;

        try {
            gameObject = objectMapper.readValue(configFilePath, type);
        } catch (IOException e) {
            String message = String.format("Cannot find config file %s for class %s",
                    configFilePath.getFile(),
                    type);
            throw new InitGameException(message, e);
        }

        return gameObject;
    }

}

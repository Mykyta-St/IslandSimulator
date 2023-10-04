package org.project.ui;

import org.project.config.ParameterLoader;
import org.project.entity.map.Island;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class IslandParamPanel extends JPanel
{
    Map<String, JTextField> textFields;
    ParameterLoader parameterLoader = ParameterLoader.getInstance();

    IslandParamPanel (MainFrame mainFrame)
    {
        this.setLayout(new GridLayout());
        this.setSize(500, 400);
        createTextFields();
        updateFields();
        addButtons(mainFrame);
    }

    private void createTextFields()
    {
        this.setLayout(new GridLayout(4,2));
        this.textFields = new HashMap<>();
        textFields.put("height", new JTextField(""));
        textFields.put("width", new JTextField(""));
        textFields.put("organismQuantity", new JTextField(""));
        for (String label: textFields.keySet()) {
            this.add(new JLabel(label));
            this.add(textFields.get(label));
        }
    }
    private void updateFields()
    {
        this.parameterLoader.updateParameters();
        for (String label: textFields.keySet()) {

            textFields.get(label)
                    .setText(parameterLoader
                            .getIslandParameters()
                            .get(label));

        }
    }

    private void addButtons(MainFrame mainFrame)
    {
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> updateConfig());
        JButton backButton = new JButton("Back to main");
        backButton.addActionListener(e -> mainFrame.loadMainPanel());
        this.add(saveButton);
        this.add(backButton);
    }

    //TODO implement verification for textfields input values
    private void updateConfig ()
    {
        for (String parameterName: textFields.keySet())
        {
            parameterLoader
                    .changeConfigXMLFile
                            (Island.class,
                                    parameterName,
                                    textFields.get(parameterName).getText()
                            );
        }

    }
}

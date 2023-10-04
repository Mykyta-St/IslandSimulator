package org.project.ui;

import org.project.config.ParameterLoader;
import org.project.entity.organism.Organism;
import org.project.entity.organism.plants.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class OrgParamPanel extends JPanel
{
    Map <String, Class <? extends Organism>> organismTypes;
    Map<String, JTextField> textFields;
    JComboBox<String> comboBox;
    ParameterLoader parameterLoader = ParameterLoader.getInstance();
    OrgParamPanel (MainFrame mainFrame)
    {
        this.setSize(500, 400);
        createCheckField();
        createTextFields();
        updateFields((String) comboBox.getSelectedItem());
        addButtons(mainFrame);

    }

    private void updateOrganismTypes()
    {
        organismTypes = new HashMap<>();
        this.parameterLoader.updateParameters();
        for (Class <? extends Organism>
                type
                : this.parameterLoader.getOrganismParameters().keySet())
        {
            this.organismTypes.put(type.getSimpleName(), type);
        }
    }

    private void createCheckField() {
        updateOrganismTypes();
        this.setLayout(new GridLayout(8,2));
        this.comboBox = new JComboBox<>();
        for (String organismType : organismTypes.keySet()) {
            comboBox.addItem(organismType);
        }
        this.add(new JLabel("Select organism"));
        ActionListener actionListener = e -> {
            JComboBox <String> box = (JComboBox)e.getSource();
            String item = (String)box.getSelectedItem();
            updateFields(item);
        };
        comboBox.addActionListener(actionListener);
        this.add(comboBox);
    }

    private void createTextFields()
    {
        this.textFields = new HashMap<>();
        textFields.put("maxQuantity", new JTextField(""));
        textFields.put("childQuantity", new JTextField(""));
        textFields.put("maxSpeed", new JTextField(""));
        textFields.put("maxAge", new JTextField(""));
        textFields.put("maxWeight", new JTextField(""));
        textFields.put("maxFood", new JTextField(""));
        for (String label: textFields.keySet()) {
            this.add(new JLabel(label));
            this.add(textFields.get(label));
        }
    }
    private void updateFields(String organismKind)
    {
        this.parameterLoader.updateParameters();
        for (String label: textFields.keySet()) {

            textFields.get(label)
                    .setText(parameterLoader
                            .getOrganismParameters()
                            .get(organismTypes
                                    .get(organismKind))
                            .get(label));

        }
        if (organismTypes.get(organismKind).getSuperclass() == Plant.class)
        {
            textFields.get("maxSpeed").setEditable(false);
            textFields.get("maxAge").setEditable(false);
            textFields.get("maxFood").setEditable(false);
        } else
        {
            textFields.get("maxSpeed").setEditable(true);
            textFields.get("maxAge").setEditable(true);
            textFields.get("maxFood").setEditable(true);
        }
    }

    private void addButtons(MainFrame mainFrame)
    {
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> updateConfig((String) comboBox.getSelectedItem()));
        JButton backButton = new JButton("Back to main");
        backButton.addActionListener(e -> mainFrame.loadMainPanel());
        this.add(saveButton);
        this.add(backButton);
    }

    //TODO implement verification for textfields input values
    private void updateConfig (String className)
    {
        for (String parameterName: textFields.keySet())
        {
            if (organismTypes.get(className).getSuperclass() == Plant.class)
            {
                if (parameterName.equals("maxSpeed") ||
                        parameterName.equals("maxAge") ||
                        parameterName.equals("maxFood"))
                {
                    continue;
                }
            }
            parameterLoader
                    .changeConfigXMLFile
                            (organismTypes.get(className),
                                    parameterName,
                                    textFields.get(parameterName).getText()
                                    );
        }

    }
}

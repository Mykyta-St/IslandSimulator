package org.project.ui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
    JButton orgParamButton, islandParamButton, simulateButton;

    public MainPanel(MainFrame mainFrame)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        orgParamButton = new JButton("Organism parameters");
        orgParamButton.setFont(new Font("Verdana", Font.PLAIN, 28));
        orgParamButton.setPreferredSize(new Dimension(500, 100));
        orgParamButton.setMaximumSize(new Dimension(500, 100));
        orgParamButton.setMinimumSize(new Dimension(500, 100));
        orgParamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        orgParamButton.addActionListener(e -> mainFrame.loadOrgParamPanel());


        islandParamButton = new JButton("Island parameters");
        islandParamButton.setFont(new Font("Verdana", Font.PLAIN, 28));
        islandParamButton.setPreferredSize(new Dimension(500, 100));
        islandParamButton.setMaximumSize(new Dimension(500, 100));
        islandParamButton.setMinimumSize(new Dimension(500, 100));
        islandParamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        islandParamButton.addActionListener(e -> mainFrame.loadIslandParamPanel());

        this.add(Box.createRigidArea(new Dimension(5,50)));


        simulateButton = new JButton("Start simulation");
        simulateButton.setFont(new Font("Verdana", Font.PLAIN, 28));
        simulateButton.setPreferredSize(new Dimension(500, 100));
        simulateButton.setMaximumSize(new Dimension(500, 100));
        simulateButton.setMinimumSize(new Dimension(500, 100));
        simulateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        simulateButton.addActionListener(e -> mainFrame.startInfoPanel());

        this.add(orgParamButton);
        this.add(Box.createRigidArea(new Dimension(5,50)));
        this.add(islandParamButton);
        this.add(Box.createRigidArea(new Dimension(5,50)));
        this.add(simulateButton);
    }
}

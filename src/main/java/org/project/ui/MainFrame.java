package org.project.ui;

import org.project.config.AppConfigurator;
import org.project.entity.map.Island;
import org.project.entity.map.IslandStatistics;
import org.project.runners.AnimalRunner;
import org.project.runners.InfoRunner;
import org.project.runners.PlantRunner;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    JPanel currentPanel;
    private static MainFrame instance;
    private MainFrame()
    {
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //this.contentPane=getContentPane();
        this.setLayout(new BorderLayout());
        JLabel mainLabel = new JLabel("Welcome to island simulation");
        mainLabel.setFont(new Font("Verdana", Font.PLAIN, 36));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(mainLabel, BorderLayout.NORTH);
        loadMainPanel();
        this.setVisible(true);

    }

    public static MainFrame getInstance()
    {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public void loadMainPanel()
    {
        if (currentPanel!=null)
        {
            this.remove(currentPanel);
        }
        this.currentPanel = new MainPanel(this);
        this.add(currentPanel, BorderLayout.CENTER);
        this.validate();
    }
    public void loadOrgParamPanel()
    {
        if (currentPanel!=null)
        {
            this.remove(currentPanel);
        }
        this.currentPanel = new OrgParamPanel(this);
        this.add(currentPanel, BorderLayout.CENTER);
        this.validate();
    }

    public void loadIslandParamPanel()
    {
        if (currentPanel!=null)
        {
            this.remove(currentPanel);
        }
        this.currentPanel = new IslandParamPanel(this);
        this.add(currentPanel, BorderLayout.CENTER);
        this.validate();
    }

    public void startInfoPanel()
    {
        //this.statPanel = new StatPanel();
        if (currentPanel!=null)
        {
            this.remove(currentPanel);
        }
        AppConfigurator.getInstance().init();
        this.currentPanel = new StatPanel();
        this.add(currentPanel, BorderLayout.CENTER);
        this.validate();
        loadSimulation();
    }
    public void loadSimulation()
    {
        final int GAME_SPEED = 3;
        Island island = AppConfigurator.getInstance().loadIsland();
        island.fulfillAreas();

        InfoRunner infoRunner = new InfoRunner(island,GAME_SPEED, this);
        AnimalRunner animalRunner = new AnimalRunner(island, GAME_SPEED);
        PlantRunner plantRunner = new PlantRunner(island, GAME_SPEED);

        infoRunner.start();
        animalRunner.start();
        plantRunner.start();
    }

    public void updateStat (IslandStatistics islandStatistics, int iteration)
    {
        ((StatPanel) this.currentPanel).updateInfoData(islandStatistics, iteration);
    }

}

package org.project.ui;

import org.project.entity.factories.OrganismFactory;
import org.project.entity.map.Statistics;
import org.project.entity.organism.Organism;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatPanel extends JPanel
{
    int iteration;
    Map <String, Map<Class <? extends Organism>, JLabel>> textFields;

    JLabel iterLabel;

    StatPanel ()
    {
        this.iteration = 0;
        this.textFields = new HashMap<>();
        createFields();
        placeLabels();
    }
    private void createFields()
    {
        //this.setLayout(new GridLayout());
        this.textFields.put("name", new HashMap<>());
        this.textFields.put("totalQuantity", new HashMap<>());
        this.textFields.put("born", new HashMap<>());
        this.textFields.put("died", new HashMap<>());
        this.textFields.put("hunted", new HashMap<>());
        for (Class <? extends Organism> type: OrganismFactory.getInstance().getOrganismSet())
        {
            this.textFields.get("name")
                    .put(type,new JLabel(type.getSimpleName()));
            this.textFields.get("name")
                    .get(type).setFont(new Font("Verdana", Font.PLAIN, 20));
            this.textFields.get("totalQuantity")
                    .put(type,new JLabel("0"));
            this.textFields.get("totalQuantity")
                    .get(type).setFont(new Font("Verdana", Font.PLAIN, 16));
            this.textFields.get("born")
                    .put(type,new JLabel("0"));
            this.textFields.get("born")
                    .get(type).setFont(new Font("Verdana", Font.PLAIN, 16));
            this.textFields.get("died")
                    .put(type,new JLabel("0"));
            this.textFields.get("died")
                    .get(type).setFont(new Font("Verdana", Font.PLAIN, 16));
            this.textFields
                    .get("hunted").put(type,new JLabel("0"));
            this.textFields.get("hunted")
                    .get(type).setFont(new Font("Verdana", Font.PLAIN, 16));
        }

    }

    private void placeLabels()
    {
        int kindsNumber = OrganismFactory.getInstance().getOrganismSet().size();
        this.setLayout(new GridLayout(kindsNumber+1,5));
        iterLabel = new JLabel("Year: 0");
        iterLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        this.add(iterLabel);

        JLabel total = new JLabel("Total");
        total.setFont(new Font("Verdana", Font.BOLD, 20));
        this.add(total);

        JLabel born = new JLabel("Born");
        born.setFont(new Font("Verdana", Font.BOLD, 20));
        this.add(born);

        JLabel died = new JLabel("Died");
        died.setFont(new Font("Verdana", Font.BOLD, 20));
        this.add(died);

        JLabel hunted = new JLabel("Hunted");
        hunted.setFont(new Font("Verdana", Font.BOLD, 20));
        this.add(hunted);

        for (Class <? extends Organism> type: OrganismFactory.getInstance().getOrganismSet())
        {
            this.add(this.textFields.get("name").get(type));
            this.add(this.textFields.get("totalQuantity").get(type));
            this.add(this.textFields.get("born").get(type));
            this.add(this.textFields.get("died").get(type));
            this.add(this.textFields.get("hunted").get(type));
        }
    }

    public void updateInfoData(Statistics islandStatistics, int iteration)
    {
        this.iterLabel.setText("Year: " + iteration);
        for (Class <? extends Organism> type: OrganismFactory.getInstance().getOrganismSet())
        {
            this.textFields.get("totalQuantity")
                    .get(type)
                    .setText(islandStatistics.getTotalQuantity(type).toString());
            this.textFields.get("born")
                    .get(type)
                    .setText(islandStatistics.getBornNumber(type).toString());
            this.textFields.get("died")
                    .get(type)
                    .setText(islandStatistics.getDiedNumber(type).toString());
            this.textFields.get("hunted")
                    .get(type)
                    .setText(islandStatistics.getHuntedNumber(type).toString());
        }
    }


}

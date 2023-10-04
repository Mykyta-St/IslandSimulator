package org.project.runners;

import org.project.entity.factories.OrganismFactory;
import org.project.entity.map.Area;
import org.project.entity.map.Island;
import org.project.entity.organism.Organism;
import org.project.entity.organism.plants.Plant;

import java.util.Set;
/**
 * This class is responsible for performing grow action for Plants.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Grow of plants</li>
 * </ul>
 */
public class PlantRunner extends Thread
{
    Island island;
    int speed;

    public PlantRunner(Island island, int speed)
    {
        this.island = island;
        this.speed = speed;

    }

    public void run()
    {
        while (!this.isInterrupted())
        {
            for (int i = 0; i < this.island.getHeight(); i++)
            {
                for (int j = 0; j < this.island.getWidth(); j++)
                {
                    Area currentArea = this.island.getArea(i, j);
                    synchronized (currentArea.getInhabitants())
                    {
                        areaPlantGrow (currentArea);
                    }

                }
            }

            try {
                Thread.sleep((long)(1000/(speed*0.5)));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void areaPlantGrow (Area area)
    {

        for (Class <? extends Organism> type: OrganismFactory.getInstance().getOrganismSet())
        {
            if (type.getSuperclass() == Plant.class)
            {
                int sizeIncrease = 0;
                if (!area.getOrganismTribe(type).isEmpty())
                {
                    sizeIncrease = plantIncreaseNumber (area.getOrganismTribe(type), type);
                }
                if (sizeIncrease>0)
                {
                    for (int i = 0; i < sizeIncrease; i++)
                    {
                        area.addNewOrganism(type);
                        area.getAreaStatistics().addBorn(type);
                    }
                }
            }
        }

    }

    private  int  plantIncreaseNumber  (Set <? extends Organism> plants, Class <? extends Organism> type)
    {
        int oldSize = plants.size();
        int childQuantity = OrganismFactory.getInstance().getPrototype(type).getChildQuantity();
        int maxQuantity = OrganismFactory.getInstance().getPrototype(type).getMaxQuantity();
        if (oldSize < maxQuantity)
        {
            return Math.min(maxQuantity - oldSize
                            , oldSize*childQuantity);
        }
        else return 0;
    }

}

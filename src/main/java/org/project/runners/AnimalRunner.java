package org.project.runners;

import org.project.entity.factories.OrganismFactory;
import org.project.entity.map.Area;
import org.project.entity.map.Island;
import org.project.entity.organism.Organism;
import org.project.entity.organism.plants.Plant;

import java.util.Iterator;
/**
 * This class is responsible for performing game actions for each Organism.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Performing of game actions for each Organism</li>
 * </ul>
 */
public class AnimalRunner extends Thread
{

    Island island;
    int speed;
    int iteration;
    public AnimalRunner(Island island, int speed)
    {
        this.island = island;
        this.speed = speed;
        this.iteration = 0;
    }

    public void run()
    {
        while (!this.isInterrupted())
        {
            this.iteration++;

            System.out.println(this.getName() + " Iteration " + this.iteration);
            for (int i = 0; i < this.island.getHeight(); i++)
            {
                for (int j = 0; j < this.island.getWidth(); j++)
                {
                        areaIteration(this.island.getArea(i, j));
                }
            }

            try {
                Thread.sleep((long)(1000/(speed*0.5)));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void  areaIteration  (Area area)
    {

            Organism organism;
            synchronized (area.getInhabitants())
            {
                for (Class<? extends Organism> organismKind : area.getInhabitants().keySet()) {
                    if (organismKind.getSuperclass() != Plant.class) {
                        Iterator<? extends Organism> iterator = area.getOrganismTribe(organismKind)
                                .iterator();
                        int pairsQuantity = 0;
                        while (iterator.hasNext()) {
                            organism = iterator.next();
                            if (!organism.isAliveAfterIteration()) {
                                area.getAreaStatistics().addDied(organismKind);
                                iterator.remove();
                            } else
                            {
                                if (!organism.isHungry()) pairsQuantity++;
                            }
                        }
                        organismReproduce(organismKind, pairsQuantity/2, area);

                    }
                }
            }

        //}
    }

    private void organismReproduce(Class<? extends Organism> organismKind, int pairsQuantity, Area currentArea)
    {
        //int pairsQuantity = currentArea.getTribeQuantity(organismKind)/2;
        int maxQuantity = OrganismFactory.getInstance().getPrototype(organismKind).getMaxQuantity();
        int childQuantity = Math.min(pairsQuantity, maxQuantity - currentArea.getTribeQuantity(organismKind));
        for (int i = 0; i < childQuantity; i++)
        {
            currentArea.addNewOrganism(organismKind);
            currentArea.getAreaStatistics().addBorn(organismKind);
        }
    }

}

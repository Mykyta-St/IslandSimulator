package org.project.entity.organism.animal.herbivore;

import org.project.entity.factories.OrganismFactory;
import org.project.entity.map.Area;
import org.project.entity.organism.Organism;
import org.project.entity.organism.animal.Animal;
import org.project.entity.organism.plants.Plant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Herbivore extends Animal
{
    Herbivore ()
    {
        super();
    }
    Herbivore (int maxQuantity,
               int childQuantity,
               int maxSpeed,
               int maxAge,
               double maxWeight,
               double maxFood,
               Area currentArea)
    {
        super(maxQuantity, childQuantity, maxSpeed, maxAge, maxWeight, maxFood, currentArea);
    }

    @Override
    public void eat()
    {
        if (this.isHungry())
            synchronized (this.getCurrentArea().getInhabitants())
            {
                eatPlant();
            }
    }

    private void eatPlant()
    {
                List <Class<? extends Organism>> potentialVictims = createPotentialVictimsList ();
                if (!potentialVictims.isEmpty())
                {
                    Class <? extends Organism> potentialVictim = potentialVictims
                            .get(new Random()
                                    .nextInt(potentialVictims.size()>1
                                            ? potentialVictims.size()-1
                                            : potentialVictims.size()));
                    Iterator<? extends Organism> iterator = this.getCurrentArea().getOrganismTribe(potentialVictim).iterator();
                    Organism victim = iterator.next();
                    this.getCurrentArea().getAreaStatistics().addHunted(potentialVictim);
                    iterator.remove();
                    this.feeding(victim.getMaxWeight());
                }
                //recursion
                if (this.isHungry() && (!createPotentialVictimsList().isEmpty())) eatPlant();
    }

    @Override
    public List<Class<? extends Organism>> createPotentialVictimsList ()
    {
        List <Class<? extends Organism>> potentialVictims = new ArrayList<>();
        for (Class <? extends Organism> organismType: OrganismFactory.getInstance().getOrganismSet())
        {
            if (organismType.getSuperclass() == Plant.class)
                if (!this.getCurrentArea().getOrganismTribe(organismType).isEmpty())
                    potentialVictims.add(organismType);
        }
        return potentialVictims;
    }
}

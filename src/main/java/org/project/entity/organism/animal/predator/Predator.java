package org.project.entity.organism.animal.predator;

import org.project.entity.factories.OrganismFactory;
import org.project.entity.map.Area;
import org.project.entity.organism.Organism;
import org.project.entity.organism.animal.Animal;

import java.util.*;

public abstract class Predator extends Animal
{
    private Map<Class<? extends Organism>, Integer> huntChances;

    Predator ()
    {
        super();
    }

    Predator (int maxQuantity,
              int childQuantity,
              int maxSpeed,
              int maxAge,
              double maxWeight,
              double maxFood,
              Area currentArea)
    {
        super(maxQuantity,
                childQuantity,
                maxSpeed,
                maxAge,
                maxWeight,
                maxFood,
                currentArea);
    }

    @Override
    public void eat()
    {

            hunt();

    }

    private void hunt()
    {
        List <Class<? extends Organism>> potentialVictims = createPotentialVictimsList ();
        if (!potentialVictims.isEmpty())
        {
            Class <? extends Organism> potentialVictim = potentialVictims
                    .get(new Random()
                            .nextInt(potentialVictims.size()>1
                                    ? potentialVictims.size()-1
                                    : potentialVictims.size()));
            if (huntSuccess(potentialVictim))
            {
                Iterator<? extends Organism> iterator = this.getCurrentArea().getOrganismTribe(potentialVictim).iterator();
                Organism victim = iterator.next();
                this.getCurrentArea().getAreaStatistics().addHunted(potentialVictim);
                iterator.remove();
                this.feeding(victim.getMaxWeight());
                if (isHungry()) hunt();
            }
        }
    }
    @Override
    public List<Class<? extends Organism>> createPotentialVictimsList ()
    {
        List <Class<? extends Organism>> potentialVictims = new ArrayList<>();
        for (Class <? extends Organism> organismType: OrganismFactory.getInstance().getOrganismSet())
        {
            if (this.getHuntChance(organismType) > 0)
                if (!this.getCurrentArea().getOrganismTribe(organismType).isEmpty())
                    potentialVictims.add(organismType);
        }
        return potentialVictims;
    }


    private boolean huntSuccess (Class <? extends Organism> victimKind)
    {
        return (new Random().nextInt(100) >= getHuntChance(victimKind));
    }

    public Integer getHuntChance (Class <? extends Organism> type)
    {
        return this.huntChances.get(type);
    }

    public void setHuntChances(Map<Class<? extends Organism>, Integer> huntChances)
    {
        this.huntChances = huntChances;
    }

}

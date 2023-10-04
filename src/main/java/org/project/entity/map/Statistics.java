package org.project.entity.map;

import org.project.entity.factories.OrganismFactory;
import org.project.entity.organism.Organism;

import java.util.HashMap;
import java.util.Map;
/**
 * This class is responsible for combining and giving the statistics.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Statistics getting and giving</li>
 * </ul>
 */
public class Statistics
{
    private final Area area;
    public Map< Class <? extends Organism>, Integer> totalQuantity = new HashMap<>();
    public Map<Class <? extends Organism>, Integer> died = new HashMap<>();
    public Map< Class <? extends Organism>, Integer> hunted = new HashMap<>();
    public Map<Class <? extends Organism>, Integer> born = new HashMap<>();

    public Statistics (Area area)
    {
        this.area = area;

        createEmptyStat();
    }

    public Statistics ()
    {
        this.area = null;

        createEmptyStat();
    }

    private void createEmptyStat ()
    {
        for (Class <? extends Organism> organism: OrganismFactory.getInstance().getOrganismSet())
        {
            this.totalQuantity.put(organism, 0);
            this.born.put(organism, 0);
            this.died.put(organism, 0);
            this.hunted.put(organism, 0);
        }
    }
    public void nullQuantity()
    {
        for (Class <? extends  Organism> organism: OrganismFactory.getInstance().getOrganismSet())
        {
            this.totalQuantity.replace(organism, 0);
            this.born.replace(organism, 0);
            this.died.replace(organism, 0);
            this.hunted.replace(organism, 0);
        }
    }

    public void updateStatistics ()
    {
        updateTotalQuantity();
    }

    private void updateTotalQuantity ()
    {
        for (Class <? extends Organism> organism: totalQuantity.keySet())
        {
            totalQuantity.replace(organism, this.area.getTribeQuantity(organism));
        }
    }

    public void addBorn (Class <? extends Organism> type)
    {
        Integer oldBornNumber = this.born.get(type);
        this.born.replace(type, oldBornNumber + 1);
    }

    public void addDied (Class <? extends Organism> type)
    {
        Integer oldDiedNumber = this.died.get(type);
        this.died.replace(type, oldDiedNumber + 1);
    }

    public void addHunted (Class <? extends Organism> type)
    {
        int oldHuntedNumber = this.hunted.get(type);
        this.hunted.replace(type, oldHuntedNumber + 1);
    }

    public Integer getTotalQuantity (Class <? extends Organism> type)
    {
        return this.totalQuantity.get(type);
    }
    public Integer getBornNumber (Class <? extends Organism> type)
    {
        Integer currentBornNumber = this.born.get(type);
        //System.out.println(type.getSimpleName() + " born number " + this.born.get(type));
        this.born.replace(type, 0);
        return currentBornNumber;
    }

    public Integer getDiedNumber (Class <? extends Organism> type)
    {
        Integer currentDiedNumber = this.died.get(type);
        this.died.replace(type, 0);
        return currentDiedNumber;
    }

    public Integer  getHuntedNumber (Class <? extends Organism> type)
    {
        Integer currentHuntedNumber = this.hunted.get(type);
        this.hunted.replace(type, 0);
        return currentHuntedNumber;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Class <? extends Organism> type: OrganismFactory.getInstance().getOrganismSet())
        {
            sb.append(type.getSimpleName())
                    .append("\t\t\t")
                    .append(this.totalQuantity.get(type))
                    .append("\t\t")
                    .append(this.getBornNumber(type))
                    .append("\t\t")
                    .append(this.getDiedNumber(type))
                    .append("\t\t")
                    .append(this.getHuntedNumber(type))
                    .append("\n");
        }

        return sb.toString();
    }
}

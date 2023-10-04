package org.project.entity.map;

import org.project.entity.factories.OrganismFactory;
import org.project.entity.organism.Organism;

/**
 * This class is responsible for combining and giving the island statistics.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Island statistics getting and giving</li>
 * </ul>
 */
public class IslandStatistics extends Statistics
{
    private final Island island;
    public IslandStatistics (Island island)
    {
        super ();
        this.island = island;
    }

    public void updateIslandStatistics ()
    {
        nullQuantity();
        //System.out.println(this);
        for (int i = 0; i < this.island.getHeight(); i++) {
            for (int j = 0; j < this.island.getWidth(); j++) {
                getStatisticsFromArea(this.island.getArea(i, j).getAreaStatistics());
            }

        }
    }

    private void getStatisticsFromArea (Statistics areaStatistics)
    {
        areaStatistics.updateStatistics();
        for (Class <? extends Organism> organism: OrganismFactory.getInstance().getOrganismSet())
        {
            this.totalQuantity.replace
            (
                    organism, addQuantity
                    (
                            this.totalQuantity.get(organism)
                            ,areaStatistics.getTotalQuantity(organism)
                    )
            );
            this.born.replace
            (
                    organism, addQuantity
                    (
                            this.born.get(organism)
                            ,areaStatistics.getBornNumber(organism)
                    )
            );
            this.died.replace
            (
                    organism, addQuantity
                    (
                            this.died.get(organism)
                            ,areaStatistics.getDiedNumber(organism)
                    )
            );
            this.hunted.replace
            (
                    organism, addQuantity
                    (
                            this.hunted.get(organism)
                            ,areaStatistics.getHuntedNumber(organism)
                    )
            );
        }
    }

    private int addQuantity(int current, int addition)
    {
        return current + addition;
    }

}

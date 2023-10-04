package org.project.entity.map;

import lombok.Getter;
import org.project.entity.factories.OrganismFactory;
import org.project.entity.organism.Organism;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible for creating Area.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Area creation and fulfilling with starting inhabitants</li>
 * </ul>
 */
@Getter
public class Area
{
    private static long serialUID = 1L;
    private final long UID = serialUID++;

    private final int x;
    private final int y;

    private Map< Class<? extends Organism>, Set<Organism>> inhabitants;

    private final Statistics areaStatistics;

    private List<Area> nearAreas;
    public void setNearAreas (List<Area> nearAreas)
    {
        this.nearAreas = nearAreas;
    }
    private Area(int x, int y)
    {
        this.x = x;
        this.y = y;
        areaStatistics = new Statistics(this);
    }

    public static Area createArea(int x, int y, Map< Class<? extends Organism>, Set<Organism>> inhabitants)
    {
        Area newArea = new Area (x, y);
        newArea.inhabitants = inhabitants;
        return newArea;
    }

    public void addOrganism (Organism organism)
    {
        this.inhabitants.get(organism.getClass()).add(organism);
    }

    public void addNewOrganism (Class<? extends Organism> type)
    {
        Organism newOrganism = OrganismFactory.getInstance().create(type);
        newOrganism.setCurrentArea(this);
        inhabitants.get(type).add(newOrganism);
    }

    public String getAreaCoords()
    {
        return " - " + this.x + " - " + this.y;
    }

    @Override
    public String toString() {
        return "Area" + this.getAreaCoords();
    }

    public Set <? extends Organism> getOrganismTribe (Class <? extends Organism> organismType)
    {
        return this.inhabitants.get(organismType);
    }
    public int getTribeQuantity (Class <? extends Organism> type)
    {
        return inhabitants.get(type).size();
    }

}

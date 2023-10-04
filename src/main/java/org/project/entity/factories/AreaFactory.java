package org.project.entity.factories;

import org.project.entity.map.Area;
import org.project.entity.organism.Organism;

import java.util.HashMap;
import java.util.HashSet;
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
public class AreaFactory
{
    private static AreaFactory instance;
    private AreaFactory () {}

    public static AreaFactory getInstance() {
        if (instance == null) {
            instance = new AreaFactory();
        }
        return instance;
    }
    public static Area createArea(int x, int y, int animalsQuantity)
    {
        Area newArea = Area.createArea(x, y, fulfillNewInhabitants(animalsQuantity));
        setInhabitantsCurrentArea(newArea);
        return newArea;
    }

    private static Map< Class<? extends Organism>, Set<Organism>> fulfillNewInhabitants(int animalsQuantity)
    {
        Map< Class<? extends Organism>, Set<Organism>> inhabitants = new HashMap<>();
        for (Class <? extends Organism> type: OrganismFactory.getInstance().getOrganismSet())
        {
            inhabitants.put(type, new HashSet<>());
        }

        for (int i = 0; i < animalsQuantity; i++)
        {
            Organism newOrganism = OrganismFactory.getInstance().createRandomOrganism();
           try
           {
               inhabitants.get(newOrganism.getClass()).add(newOrganism);
           }
            catch (NullPointerException exception)
            {
                System.out.println(newOrganism.getClass());
            }
        }
        return inhabitants;
    }

    private static void setInhabitantsCurrentArea(Area currentArea)
    {
        for (Class <? extends Organism> organismKind: currentArea.getInhabitants().keySet())
        {
            for (Organism organism: currentArea.getOrganismTribe(organismKind))
            {
                organism.setCurrentArea(currentArea);
            }
        }
    }
}

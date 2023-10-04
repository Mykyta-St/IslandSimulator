package org.project.entity.factories;

import lombok.Getter;
import org.project.entity.organism.Organism;

import java.util.*;

/**
 * This class is responsible for creating Organism.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>New Organism creation</li>
 * </ul>
 */
public class OrganismFactory
{
    @Getter
    private static final OrganismFactory instance = new OrganismFactory();
    private final Map<Class<? extends Organism>, Organism> prototypes = new HashMap<>();
    private OrganismFactory () {

    }
    public void registerPrototype(Organism prototype) {
        prototypes.put(prototype.getClass(), prototype);
    }

    public Organism create(Class<? extends Organism> type) {
        if (!prototypes.containsKey(type)) {
            throw new IllegalArgumentException();
        }

        return prototypes.get(type).reproduce();
    }

    public Organism getPrototype(Class<? extends Organism> type)
    {
        return  prototypes.get(type);
    }

    public Class < ? extends Organism> getRandomOrganismClass()
    {
        int size = prototypes.size();
        return new ArrayList<>(getOrganismSet()).get(new Random().nextInt(size));
    }

    public Organism createRandomOrganism()
    {
        return create(getRandomOrganismClass());
    }

    public Set<Class<? extends Organism>> getOrganismSet()
    {
        return prototypes.keySet();
    }
}

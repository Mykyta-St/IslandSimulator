package org.project.entity.organism.animal.predator;

import org.project.abstraction.annotations.Config;
import org.project.entity.map.Area;
import org.project.entity.organism.Organism;
import org.project.entity.organism.animal.herbivore.Goat;
import org.project.entity.organism.animal.herbivore.Rabbit;
import org.project.entity.organism.animal.herbivore.Sheep;
import org.project.entity.organism.animal.herbivore.Worm;
import org.project.entity.organism.plants.Cactus;
import org.project.entity.organism.plants.Grass;

import java.util.HashMap;
import java.util.Map;

@Config(fileName = "config/organism/animal/predator/Fox.xml")
//@GameObject
public class Fox extends Predator
{
    public Fox ()
    {
        super();
    }
    public Fox (int maxQuantity,
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

        setHuntChances(foxHuntChances());
    }

    @Override
    public Organism reproduce ()
    {
        return new Fox(this.getMaxQuantity(),
                this.getChildQuantity(),
                this.getMaxSpeed(),
                this.getMaxAge(),
                this.getMaxWeight(),
                this.getMaxFood(),
                this.getCurrentArea());
    }

    @Override
    public String toString()
    {
        return this.getClass()
                + " ID: " + this.getUID()
                + " maxQuantity: " + this.getMaxQuantity()
                + " maxChildQuantity: " + this.getChildQuantity()
                + " maxSpeed: " + this.getMaxSpeed()
                + " maxAge: " + this.getMaxAge()
                + " maxWeight: " + this.getMaxWeight()
                + " Age: " + this.getAge()
                + " maxFood: " + this.getMaxFood()
                + " foodInStomach: " + this.getFoodInStomach()
                + " Is alive: " + this.isAlive();
    }

    private static Map<Class<? extends Organism>, Integer> foxHuntChances()
    {
        Map<Class<? extends Organism>, Integer> huntChances = new HashMap<>();
        huntChances.put(Worm.class, 40);
        huntChances.put(Rabbit.class, 70);
        huntChances.put(Goat.class, 0);
        huntChances.put(Sheep.class, 0);
        huntChances.put(Mouse.class, 90);
        huntChances.put(Duck.class, 60);
        huntChances.put(Boar.class, 0);
        huntChances.put(Eagle.class, 0);
        huntChances.put(Wolf.class, 0);
        huntChances.put(Fox.class, 0);
        huntChances.put(Bear.class, 0);
        huntChances.put(Grass.class, 0);
        huntChances.put(Cactus.class, 0);
        return huntChances;
    }
}

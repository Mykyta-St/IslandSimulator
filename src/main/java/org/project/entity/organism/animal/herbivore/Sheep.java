package org.project.entity.organism.animal.herbivore;

import org.project.abstraction.annotations.Config;
import org.project.abstraction.annotations.GameObject;
import org.project.entity.map.Area;
import org.project.entity.organism.Organism;

@Config(fileName = "config/organism/animal/herbivore/Sheep.xml")
@GameObject
public class Sheep extends Herbivore
{
    Sheep ()
    {
        super();
    }
    Sheep (int maxQuantity,
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
    public Organism reproduce ()
    {
        return new Sheep(this.getMaxQuantity(),
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
}


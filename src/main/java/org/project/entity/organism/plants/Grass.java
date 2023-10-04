package org.project.entity.organism.plants;

import org.project.abstraction.annotations.Config;
import org.project.abstraction.annotations.GameObject;
import org.project.entity.organism.Organism;

@Config(fileName = "config/organism/plant/Grass.xml")
@GameObject
public class Grass extends Plant
{
    public Grass ()
    {
        super();
    }

    public Grass (int maxQuantity,
                 double maxWeight,
                 int childQuantity)
    {
        super(maxQuantity, maxWeight, childQuantity);
    }

    @Override
    public Organism reproduce ()
    {
        return new Grass(this.getMaxQuantity(),
                this.getMaxWeight(),
                this.getChildQuantity());
    }
}

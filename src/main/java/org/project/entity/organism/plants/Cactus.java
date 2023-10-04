package org.project.entity.organism.plants;

import org.project.abstraction.annotations.Config;
import org.project.abstraction.annotations.GameObject;
import org.project.entity.organism.Organism;

@Config(fileName = "config/organism/plant/Cactus.xml")
@GameObject
public class Cactus extends Plant
{
    public Cactus ()
    {
        super();
    }

    public Cactus (int maxQuantity,
                  double maxWeight,
                  int childQuantity)
    {
        super(maxQuantity, maxWeight, childQuantity);
    }

    @Override
    public Organism reproduce ()
    {
        return new Cactus(this.getMaxQuantity(),
                this.getMaxWeight(),
                this.getChildQuantity());
    }
}
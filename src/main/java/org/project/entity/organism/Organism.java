package org.project.entity.organism;

import org.project.abstraction.interfaces.Reproducable;
import org.project.entity.map.Area;

public abstract class Organism implements Reproducable
{
    public Organism() { }
    public Organism reproduce()
    {
        return null;
    }

    public void liveIteration(){ }
    public boolean isAliveAfterIteration () {
        this.liveIteration();
        return this.isAlive();
    }

    public void setCurrentArea(Area area)
    {

    }

    public boolean isAlive() { return true;}
    public boolean isHungry()
    {
        return false;
    }
    public void move() {}

    public int getMaxQuantity()
    {
        return 0;
    }
    public int getChildQuantity()
    {
        return 0;
    }
    public double getMaxWeight()
    {
        return 0;
    }

}

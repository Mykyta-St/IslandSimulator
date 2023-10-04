package org.project.entity.organism.plants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.project.entity.organism.Organism;

@Getter
public abstract class Plant extends Organism
{
    @JsonIgnore
    private static long serialUID = 1L;
    @JsonIgnore
    private final long UID = serialUID++;

    @JsonIgnore
    private boolean isAlive = true;

    @JsonProperty("maxQuantity")
    private int maxQuantity;
    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    @JsonProperty("maxWeight")
    private double maxWeight;
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @JsonProperty("childQuantity")
    private int childQuantity;
    //public void setGrowthSpeed(double growthSpeed) {
    //    this.growthSpeed = growthSpeed;
    //}
    protected Plant()
    {
        super();
    }

    protected Plant(int maxQuantity,
                    double maxWeight,
                    int childQuantity
                    )
    {
        super();
        this.maxQuantity = maxQuantity;
        this.maxWeight = maxWeight;
        this.childQuantity = childQuantity;
    }

    public boolean isAlive ()
    {
        return this.isAlive;
    }

    public void kill()
    {
        this.isAlive = false;
    }

}

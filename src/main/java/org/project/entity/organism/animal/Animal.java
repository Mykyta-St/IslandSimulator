package org.project.entity.organism.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.project.abstraction.interfaces.Movable;
import org.project.entity.map.Area;
import org.project.entity.organism.Organism;

import java.util.List;

@Getter
@JsonIgnoreProperties(value = {"age", "isAlive", "foodInStomach"})

public abstract class Animal extends Organism implements Movable
{
    @JsonIgnore
    private static long serialUID = 1L;
    @JsonIgnore
    private final long UID = serialUID++;

    @JsonProperty("maxQuantity")
    private int maxQuantity;
    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    @JsonProperty("childQuantity")
    private int childQuantity;
    public void setChildQuantity(int childQuantity) {
        this.childQuantity = childQuantity;
    }

    @JsonProperty("maxSpeed")
    private int maxSpeed;
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @JsonProperty("maxAge")
    private int maxAge;
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @JsonProperty("maxWeight")
    private double maxWeight;
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @JsonProperty("maxFood")
    private double maxFood;
    public void setMaxFood(double maxFood) {
        this.maxFood = maxFood;
    }

    @JsonIgnore
    private int age = 0;
    @JsonIgnore
    private boolean isAlive = true;
    @JsonIgnore
    private double foodInStomach;



    @JsonIgnore
    private Area currentArea;
    @Override
    public void setCurrentArea(Area newArea)
    {
        this.currentArea = newArea;
    }
    //private Map<String, Number> limits = new HashMap<>();

    public Animal ()
    {
        super();
        foodInStomach = maxFood;
    }


    protected Animal (int maxQuantity,
                      int childQuantity,
                      int maxSpeed,
                      int maxAge,
                      double maxWeight,
                      double maxFood,
                      Area currentArea)
    {
        super();

        this.maxAge = maxAge;
        this.childQuantity = childQuantity;
        this.maxWeight = maxWeight;
        this.maxFood = maxFood;
        this.maxQuantity = maxQuantity;
        this.maxSpeed = maxSpeed;
        this.foodInStomach = maxFood;
        this.currentArea = currentArea;
    }
    @Override
    public void liveIteration () {
        this.aging();
        if (this.isAlive())
        {
            this.eat();
            if (isHungry()) this.move();
        }
    }
    public void aging()
    {
        this.age++;
        if (this.age > getMaxAge())
        {
            this.kill();
        } else
        {
            this.starvation();
        }
    }
    @Override
    public boolean isAlive()
    {
        return this.isAlive;
    }
    public void starvation()
    {
        this.foodInStomach -= (getMaxFood() * 0.25);
        if (foodInStomach<=0)
        {
            this.kill();
        }
    }
    private void kill()
    {
        this.isAlive = false;
    }
    @Override
    public void move()
    {
        Area startArea = this.getCurrentArea();
        Area newArea = startArea;
        for (int i = 0; i < this.getMaxSpeed(); i++)
        {
            if (newArea.getNearAreas()!= null)
            {
                if(!createPotentialVictimsList().isEmpty()) break;
                else
                {
                    if (!newArea.getNearAreas().isEmpty())
                    {
                        newArea = this.getRandomNearArea();
                        this.setCurrentArea(newArea);
                    }
                }
            }
        }
        if (!newArea.getAreaCoords().equals(startArea.getAreaCoords()))
        {
            newArea.addOrganism(this);
        }

    }
    private Area getRandomNearArea()
    {
        double choise = Math.random()*currentArea.getNearAreas().size();
        if (currentArea.getNearAreas().isEmpty()) return currentArea;
        else return currentArea.getNearAreas().get((int)choise);
    }

    public void eat()
    {

    }
    @Override
    public boolean isHungry()
    {
        return (foodInStomach < this.getMaxFood());
    }

    public void feeding(double foodEaten)
    {
        this.foodInStomach += Math.min(foodEaten, this.foodToEat());
    }
    public double foodToEat()
    {
        return (this.getMaxFood() - this.foodInStomach);
    }

    public List<Class<? extends Organism>> createPotentialVictimsList ()
    {
        return null;
    }
}

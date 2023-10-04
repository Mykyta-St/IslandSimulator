package org.project.entity.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.project.abstraction.annotations.Config;
import org.project.entity.factories.AreaFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for creating and managing Island.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Island creation and management</li>
 * </ul>
 */

@Config(fileName = "config/island/Island.xml")

public class Island implements Serializable
{
    @JsonProperty("organismQuantity")
    private final int organismStartQuantity;
    @JsonProperty("height")
    private final int HEIGHT;
    @JsonProperty("width")
    private final int WIDTH;

    private Area [][] areas;

    @Getter
    private final IslandStatistics islandStatistics;

    public Island() {
        this.HEIGHT = 1;
        this.WIDTH = 1;
        this.organismStartQuantity = 1;
        this.islandStatistics = new IslandStatistics(this);
    }

    public int getHeight()
    {
        return this.HEIGHT;
    }
    public int getWidth()
    {
        return this.WIDTH;
    }
    public Area getArea (int x, int y)
    {
        return areas[x][y];
    }
    public void fulfillAreas()
    {
        this.areas = new Area[HEIGHT][WIDTH];
        for (int i = 0; i < this.HEIGHT; i++)
        {
            for (int j = 0; j < this.WIDTH; j++)
            {
                areas[i][j] = AreaFactory.createArea(i, j, organismStartQuantity);
                //System.out.println(areas[i][j]);
            }
        }
        fulfillNearAreaList();
        updateIslandStatistics();
    }

    private void fulfillNearAreaList ()
    {
        for (int i = 0; i < this.HEIGHT; i++)
        {
            for (int j = 0; j < this.WIDTH; j++)
            {
                areas[i][j].setNearAreas(getNearAreaList(i, j));
                //System.out.println(areas[i][j].toString());
            }
        }
    }

    private List<Area> getNearAreaList (int x, int y)
    {
        List<Area> areaList = new ArrayList<>();
        if ( x+1 < HEIGHT) areaList.add(getArea(x+1, y));
        if ( x-1 >= 0) areaList.add(getArea(x-1, y));
        if ( y+1 < WIDTH) areaList.add(getArea(x, y+1));
        if ( y-1 >= 0) areaList.add(getArea(x, y-1));
        return (!areaList.isEmpty()) ? areaList : null;
    }

    public void updateIslandStatistics()
    {
        this.islandStatistics.updateIslandStatistics();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Height: ")
                .append(this.HEIGHT)
                .append(" Width:  ")
                .append(this.WIDTH)
                .append(" start quantity: ")
                .append(this.organismStartQuantity);
        return sb.toString();

    }
}

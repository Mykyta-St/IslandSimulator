package org.project.runners;

import org.project.entity.map.Island;
import org.project.ui.MainFrame;
/**
 * This class is responsible for updating island statistics and every area statistics.
 * <p>
 * It is responsible for:
 * <ul>
 * <li>Updating statistics</li>
 * </ul>
 */
public class InfoRunner extends Thread
{
    Island island;
    int speed;

    MainFrame mainFrame;
    public InfoRunner(Island island, int speed, MainFrame mainFrame)
    {
        this.island = island;
        this.speed = speed;
        this.mainFrame = mainFrame;
    }

    public void run()
    {
        try {
            int iteration = 0;
            while (!this.isInterrupted())
            {
                iteration++;
                island.updateIslandStatistics();

                this.mainFrame.updateStat(island.getIslandStatistics(), iteration);

                Thread.sleep((long)(1000/(speed*0.5)));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
}
package com.redmancometh.arkspawn.tasks;
import org.bukkit.scheduler.BukkitRunnable;
import com.redmancometh.arkspawn.ArkSpawn;
public class TickTask extends BukkitRunnable
{
    @Override
    public void run()
    {
        ArkSpawn.getInstance().getSpawnerManager().tickSpawners();
    }

    public void tick(int group)
    {

    }

    /**
     * This argument should be at what segment in the second is from 1 to 20
     * @param secProgress
     * @return
     */
    public int getGroupToTick(int secProgress)
    {
        return Math.min(20, secProgress);
    }

    public void updateGroups()
    {

    }

    /**
     * This should be a maximum of 20, so that all spawner groups can be ticked in one second.
     */
    public int getGroupCalculation()
    {
        return Math.min(0, 0);
    }
}

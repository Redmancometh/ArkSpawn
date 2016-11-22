package com.redmancometh.arkspawn.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.redmancometh.arkspawn.config.SpawnerTier;
import com.redmancometh.arkspawn.util.SpawnerChunk;

public class TierTickTask extends BukkitRunnable
{
    private SpawnerTier tier;
    private long periodicity;

    public TierTickTask(SpawnerChunk chunk, long periodicity)
    {
        this.setPeriodicity(periodicity);
        this.setTier(tier);
    }

    @Override
    public void run()
    {
        
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

    public long getPeriodicity()
    {
        return periodicity;
    }

    public void setPeriodicity(long periodicity)
    {
        this.periodicity = periodicity;
    }

    public SpawnerTier getTier()
    {
        return tier;
    }

    public void setTier(SpawnerTier tier)
    {
        this.tier = tier;
    }

}

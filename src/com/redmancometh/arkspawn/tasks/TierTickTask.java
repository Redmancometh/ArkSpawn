package com.redmancometh.arkspawn.tasks;

import com.redmancometh.arkspawn.config.SpawnerTier;

public class TierTickTask
{
    private SpawnerTier tier;
    private long periodicity;

    public TierTickTask(SpawnerTier tier, long periodicity)
    {
        this.setPeriodicity(periodicity);
        this.setTier(tier);
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

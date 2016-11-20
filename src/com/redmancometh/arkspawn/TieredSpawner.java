package com.redmancometh.arkspawn;

import org.bukkit.Location;

import com.redmancometh.arkspawn.config.SpawnerTier;

public class TieredSpawner
{
    private Location location;
    private SpawnerTier tier;

    public TieredSpawner(Location location, SpawnerTier tier)
    {
        this.location = location;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
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

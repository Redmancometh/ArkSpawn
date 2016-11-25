package com.redmancometh.arkspawn;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.redmancometh.arkspawn.config.SpawnerTier;

public class TieredSpawner
{
    private Location location;
    private SpawnerTier tier;
    private EntityType type; //so that we don't need to call getBlock and fuck with spawner data.

    public TieredSpawner(Location location, SpawnerTier tier, EntityType type)
    {
        this.location = location;
        this.type = type;
        this.location = location;
    }

    public void tick()
    {
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

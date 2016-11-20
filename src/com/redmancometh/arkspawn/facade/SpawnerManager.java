package com.redmancometh.arkspawn.facade;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Chunk;
import org.bukkit.Location;

import com.redmancometh.arkspawn.TieredSpawner;
import com.redmancometh.arkspawn.config.SpawnerTier;
import com.redmancometh.arkspawn.util.SpawnerTierCollection;

public class SpawnerManager
{
    private Map<SpawnerTier, SpawnerTierCollection> spawners = new ConcurrentHashMap();

    /**
     * 
     * @param tier
     * @return
     */
    public SpawnerTierCollection getSpawnersByTier(SpawnerTier tier)
    {
        return spawners.get(tier);
    }

    /**
     * Last resort means for getting a spawner.
     * @param loc
     * @return
     */
    public TieredSpawner getFromLocation(Location loc)
    {
        for (SpawnerTierCollection coll : spawners.values())
        {
            if (coll.isAtLocation(loc))
            {
                return coll.getFromLocation(loc);
            }
        }
        return null;
    }

    /**
     * 
     * @param c
     * @return
     */
    public boolean doesChunkExist(Chunk c)
    {
        for (SpawnerTierCollection coll : spawners.values())
        {
            if (coll.hasChunkLoaded(c))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param c
     * @param tier
     * @return
     */
    public boolean doesChunkTierExist(Chunk c, SpawnerTier tier)
    {
        return spawners.get(tier).hasChunkLoaded(c);
    }

    public void insertIntoManager(TieredSpawner spawner)
    {
        spawners.get(spawner.getTier()).insertAtLocation(spawner);
    }

    /**
     * Use this method if possible for maximum performance. Checking isAtLocation for the key is significantly more expensive.
     * @param tier
     * @param loc
     * @return
     */
    public TieredSpawner getFromLocation(SpawnerTier tier, Location loc)
    {
        return spawners.get(tier).getFromLocation(loc);
    }

    /**
     * Properly performant at location check.
     * @param loc
     * @param tier
     * @return
     */
    public boolean hasAtLocation(Location loc, SpawnerTier tier)
    {
        return spawners.get(tier).isAtLocation(loc);
    }

    /**
     * Last resort method to check for a spawner at a location.
     * @param loc
     * @return
     */
    public boolean hasAtLocation(Location loc)
    {
        for (SpawnerTierCollection coll : spawners.values())
        {
            if (coll.isAtLocation(loc))
            {
                return true;
            }
        }
        return false;
    }
}

package com.redmancometh.arkspawn.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;

import com.redmancometh.arkspawn.TieredSpawner;

public class SpawnerTierCollection implements Iterable<SpawnerChunk>
{
    private Map<LongHash, SpawnerChunk> chunkMap = new HashMap();

    public boolean isAtLocation(Location loc)
    {
        Chunk c = loc.getChunk();
        Long hash = LongHash.toLong(c.getX(), c.getZ());
        if (!chunkMap.containsKey(hash))
        {
            return false;
        }
        return chunkMap.get(hash).isAtLocation(loc);
    }

    public boolean hasChunkLoaded(Chunk c)
    {
        return chunkMap.containsKey(LongHash.toLong(c.getX(), c.getZ()));
    }

    public void insertAtLocation(TieredSpawner spawner)
    {
        Chunk c = spawner.getLocation().getChunk();
        chunkMap.get(LongHash.toLong(c.getX(), c.getZ())).insertAtLocation(spawner);
    }

    /**
     * NOTE: This is an entirely unchecked operation!
     * Use isAtLocation if you aren't 1000% fucking certain
     * @param loc
     * @return
     */
    public TieredSpawner getFromLocation(Location loc)
    {
        Chunk c = loc.getChunk();
        return chunkMap.get(LongHash.toLong(c.getX(), c.getZ())).getFromLocation(loc);
    }

    @Override
    public Iterator<SpawnerChunk> iterator()
    {
        return chunkMap.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super SpawnerChunk> action)
    {
        chunkMap.values().forEach(action);
    }

}

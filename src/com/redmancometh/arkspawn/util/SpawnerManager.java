package com.redmancometh.arkspawn.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;

import com.redmancometh.arkspawn.TieredSpawner;

public class SpawnerManager implements Iterable<SpawnerChunk>
{
    private Map<LongHash, SpawnerChunk> chunkMap = new HashMap();

    {
    }

    /**
     * Is there a spawner at this location?
     * @param loc
     * @return
     */
    public boolean isAtLocation(Location loc)
    {
        Long hash = LongHash.toLong(loc.getChunk().getX(), loc.getChunk().getZ());
        if (!chunkMap.containsKey(hash))
        {
            return false;
        }
        return chunkMap.get(hash).isAtLocation(loc);
    }

    /**
     * This will save the chunk to the database, and then remove it's reference from the chunkMap.
     * @param c
     */
    public void purgeAndSave(SpawnerChunk c)
    {
        //Really hate the double-storage of the long hash, but seems unavoidable. Alternative 
        //route is iteration through the map and checking for the spawnerchunk.
        c.purgeAndSave().thenRun(() -> chunkMap.remove(c.getHash()));
    }

    /**
     * Check if an instance of the chunk exists in the SpawnerManager.
     * @param c
     * @return
     */
    public boolean hasChunkLoaded(Chunk c)
    {
        return chunkMap.containsKey(LongHash.toLong(c.getX(), c.getZ()));
    }

    /**
     * Check whether the chunk at x,z is loaded
     * @param x
     * @param z
     * @return
     */
    public boolean hasChunkLoaded(int x, int z)
    {
        return chunkMap.containsKey(LongHash.toLong(x, z));
    }

    /**
     * Check if a chunk with this hash exists.
     * @param hash
     * @return
     */
    public boolean hasChunkLoaded(long hash)
    {
        return chunkMap.containsKey(hash);
    }

    /**
     * push a spawner to the SpawnerManager map.
     * @param spawner
     */
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

    /**
     * So I can expose the iterator without exposing the map.
     */
    @Override
    public void forEach(Consumer<? super SpawnerChunk> action)
    {
        chunkMap.values().forEach(action);
    }

}

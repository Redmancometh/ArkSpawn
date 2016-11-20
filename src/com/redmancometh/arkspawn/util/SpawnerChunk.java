package com.redmancometh.arkspawn.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Location;

import com.redmancometh.arkspawn.TieredSpawner;

public class SpawnerChunk implements Iterable<TieredSpawner>
{
    private Map<Location, TieredSpawner> locMap = new HashMap();

    /**
     * ONLY use the block XYZ so this doesn't break.
     * @param loc
     * @return
     */
    public boolean isAtLocation(Location loc)
    {
        return locMap.containsKey(loc);
    }

    public void insertAtLocation(TieredSpawner spawner)
    {
        
    }

    public TieredSpawner getFromLocation(Location loc)
    {
        return locMap.get(loc);
    }

    @Override
    public Iterator<TieredSpawner> iterator()
    {
        return locMap.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super TieredSpawner> action)
    {
        locMap.values().forEach(action);
    }
}

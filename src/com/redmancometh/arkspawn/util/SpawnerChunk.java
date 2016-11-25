package com.redmancometh.arkspawn.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;

import com.redmancometh.arkspawn.ArkSpawn;
import com.redmancometh.arkspawn.TieredSpawner;

public class SpawnerChunk implements Iterable<TieredSpawner>
{
    private LinkedHashMap<Location, TieredSpawner> locMap = new LinkedHashMap();
    private long longHash;
    private int currentGroup = 0;

    public SpawnerChunk(long hash)
    {
        this.longHash = hash;
    }

    public SpawnerChunk(int x, int z)
    {
        this.longHash = LongHash.toLong(x, z);
    }

    public void tick()
    {
        if (currentGroup == 4)
        {
            currentGroup = 0;
        }
        else
        {
            currentGroup++;
        }
        if (locMap.values().size() < 4)
        {
            instantTick();
            return;
        }
        int sliceSize = Math.max(1, locMap.values().size()) / 4;
        int start = currentGroup * sliceSize; //I guess if this is 0 that's cool
        int end = (currentGroup == 4) ? locMap.values().size() : sliceSize * currentGroup;
        for (int x = start; x < end; x++)
        {
            getSpawnerByIndex(x).tick();
        }
    }

    public TieredSpawner getSpawnerByIndex(int index)
    {
        return locMap.get(locMap.keySet().toArray()[index]);
    }

    public void instantTick()
    {
        if (currentGroup == 4)
        {
            this.locMap.values().forEach((spawner) -> spawner.tick());
        }
    }

    /**
     * ONLY use the block XYZ so this doesn't break.
     * @param loc
     * @return
     */
    public boolean isAtLocation(Location loc)
    {
        return locMap.containsKey(loc);
    }

    public CompletableFuture<Void> purgeAndSave()
    {
        return ArkSpawn.getInstance().getSpawnerDatabase().insertSpawnerTierCollectionAsync(locMap.values()).thenRun(() -> locMap = null);
    }

    public void insertAtLocation(TieredSpawner spawner)
    {
        locMap.put(spawner.getLocation(), spawner);
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

    public long getHash()
    {
        return this.longHash;
    }

    @Override
    public void forEach(Consumer<? super TieredSpawner> action)
    {
        locMap.values().forEach(action);
    }
}

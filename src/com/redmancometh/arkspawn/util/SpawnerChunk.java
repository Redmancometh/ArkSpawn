package com.redmancometh.arkspawn.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;

import com.redmancometh.arkspawn.ArkSpawn;
import com.redmancometh.arkspawn.TieredSpawner;

public class SpawnerChunk implements Iterable<TieredSpawner>
{
    private Map<Location, TieredSpawner> locMap = new HashMap();
    private long longHash;

    public SpawnerChunk(long hash)
    {
        this.longHash = hash;
    }

    public SpawnerChunk(int x, int z)
    {
        this.longHash = LongHash.toLong(x, z);
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

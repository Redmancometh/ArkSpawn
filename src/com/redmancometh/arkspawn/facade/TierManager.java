package com.redmancometh.arkspawn.facade;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.redmancometh.arkspawn.config.SpawnerTier;

public class TierManager implements Iterable<SpawnerTier>
{
    private Map<String, SpawnerTier> tierMap = new ConcurrentHashMap();

    public SpawnerTier getTierByName(String name)
    {
        return tierMap.get(name);
    }

    public boolean containsTierName(String name)
    {
        return tierMap.containsKey(name);
    }

    public void addTier(SpawnerTier tier)
    {
        tierMap.put(tier.getName(), tier);
    }

    @Override
    public void forEach(Consumer<? super SpawnerTier> action)
    {
        tierMap.values().forEach(action);
    }

    @Override
    public Iterator<SpawnerTier> iterator()
    {
        return tierMap.values().iterator();
    }

}

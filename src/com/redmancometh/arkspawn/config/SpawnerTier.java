package com.redmancometh.arkspawn.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import com.redmancometh.arkspawn.ArkSpawn;

public class SpawnerTier
{
    private String name;
    private long tickRate;
    private int tickTaskId;
    private Map<EntityType, Integer> maxTierMap = new ConcurrentHashMap();
    private int cost;

    public SpawnerTier(String name, long tickRate, int cost)
    {
        this.name = name;
        this.tickRate = tickRate;
        this.setCost(cost);
        startTask();
    }

    public SpawnerTier(String name, long tickRate, int cost, Map<EntityType, Integer> tierMap)
    {
        this.name = name;
        this.tickRate = tickRate;
        this.setCost(cost);
        this.maxTierMap = tierMap;
        startTask();
    }

    public void addMaximum(EntityType type, int amount)
    {
        maxTierMap.put(type, amount);
    }

    private void startTask()
    {
        this.tickTaskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(ArkSpawn.getInstance(), () ->
        {

        }, tickRate, tickRate);
    }

    public void setLimit(EntityType type, int level)
    {
        maxTierMap.put(type, level);
    }

    public int getTickTaskId()
    {
        return tickTaskId;
    }

    public void setTickTaskId(int tickTaskId)
    {
        this.tickTaskId = tickTaskId;
    }

    public long getTickRate()
    {
        return tickRate;
    }

    public void setTickRate(long tickRate)
    {
        this.tickRate = tickRate;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}

package com.redmancometh.arkspawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.redmancometh.arkspawn.databasing.SpawnerDatabase;
import com.redmancometh.arkspawn.facade.TierManager;
import com.redmancometh.arkspawn.listeners.SpawnerListeners;
import com.redmancometh.arkspawn.util.SpawnerManager;

public class ArkSpawn extends JavaPlugin
{
    private static ArkSpawn instance;
    private TierManager tierManager;
    private SpawnerManager spawnerManager;
    private SpawnerDatabase spawnerDatabase;

    @Override
    public void onEnable()
    {
        instance = this;
        spawnerDatabase = new SpawnerDatabase();
        tierManager = new TierManager();
        spawnerManager = new SpawnerManager();
        Bukkit.getPluginManager().registerEvents(new SpawnerListeners(), this);
    }

    public SpawnerManager getSpawnerManager()
    {
        return spawnerManager;
    }

    public TierManager getTierManager()
    {
        return tierManager;
    }

    public static ArkSpawn getInstance()
    {
        return instance;
    }

    public SpawnerDatabase getSpawnerDatabase()
    {
        return spawnerDatabase;
    }

}

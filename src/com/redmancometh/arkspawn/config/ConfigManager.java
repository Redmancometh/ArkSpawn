package com.redmancometh.arkspawn.config;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import com.redmancometh.arkspawn.ArkSpawn;

public class ConfigManager
{

    private FileConfiguration config;

    public ConfigManager(JavaPlugin pl)
    {
        File configFile = new File(pl.getDataFolder(), "arkspawners.yml");
        if (!configFile.exists())
        {
            pl.saveResource("arkspawners.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void init()
    {
        config.getKeys(false).forEach((tierName) ->
        {
            SpawnerTier tier = new SpawnerTier(tierName, config.getInt(tierName + ".tickrate"), config.getInt(tierName + ".cost"), makeMobMap(tierName));
            ArkSpawn.getInstance().getTierManager().addTier(tier);
        });
    }

    public Map<EntityType, Integer> makeMobMap(String tierName)
    {
        Map<EntityType, Integer> mobMap = new ConcurrentHashMap();
        config.getConfigurationSection(tierName).getKeys(false).forEach((mobType) ->
        {
            String confString = tierName + "." + mobType + ".";
            mobMap.put(EntityType.valueOf(mobType.toUpperCase()), config.getInt(confString + "maximum"));
        });
        return mobMap;
    }

}

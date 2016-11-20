package com.redmancometh.arkspawn.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import com.redmancometh.arkspawn.ArkSpawn;

public class SpawnerListeners implements Listener
{
    @EventHandler
    public void onPlace(BlockPlaceEvent e)
    {
        
    }

    @EventHandler
    public void loadSpawnersOnLoad(ChunkLoadEvent e)
    {
        ArkSpawn.getInstance().getSpawnerDatabase().getSpawnersInChunk(e.getChunk().getX(), e.getChunk().getZ()).thenAccept((spawnerSet) -> spawnerSet.forEach((spawner) -> ArkSpawn.getInstance().getSpawnerManager().insertIntoManager(spawner)));
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e)
    {
        ArkSpawn.getInstance().getSpawnerDatabase().getSpawnersInChunk(e.getChunk().getX(), e.getChunk().getZ()).thenAccept((spawnerSet) -> spawnerSet.forEach((spawner) -> ArkSpawn.getInstance().getSpawnerManager().insertIntoManager(spawner)));
    }
}

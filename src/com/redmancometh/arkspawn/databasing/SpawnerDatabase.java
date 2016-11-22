package com.redmancometh.arkspawn.databasing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;

import com.redmancometh.arkshards.ArkShards;
import com.redmancometh.arkspawn.ArkSpawn;
import com.redmancometh.arkspawn.TieredSpawner;
import com.redmancometh.arkspawn.config.SpawnerTier;

public class SpawnerDatabase
{
    public Connection getConnection()
    {
        return ArkShards.getInstance().getConnection();
    }

    public void createTable()
    {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("create table if not exists spawners(tier varchar(16),x int,y int,z int,chunkhash bigint NOT NULL PRIMARY KEY, world varchar(32));"))
        {
            ps.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Void> insertSpawnerTierCollectionAsync(Collection<TieredSpawner> spawners)
    {
        return CompletableFuture.runAsync(() ->
        {
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("INSERT INTO spawners(tier,x,y,z,chunkhash,world) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY(UPDATE spawner SET tier=?,x=?,y=?,z=?,chunkhash=?,world=?)"))
            {
                spawners.forEach((spawner) -> addBatch(spawner, ps));
                ps.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, ArkShards.getInstance().getPool());
    }

    //Pretty sure I'm gonna use this at some point
    public void insertSpawnerTierCollectionSync(Collection<TieredSpawner> spawners)
    {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("INSERT INTO spawners(tier,x,y,z,chunkhash,world) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY(UPDATE spawner SET tier=?,x=?,y=?,z=?,chunkhash=?,world=?)"))
        {
            spawners.forEach((spawner) -> addBatch(spawner, ps));
            ps.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void addBatch(TieredSpawner spawner, PreparedStatement ps)
    {
        try
        {
            Location loc = spawner.getLocation();
            ps.setString(1, spawner.getTier().getName());
            ps.setInt(2, loc.getBlockX());
            ps.setInt(3, loc.getBlockY());
            ps.setInt(4, loc.getBlockZ());
            ps.setLong(5, LongHash.toLong(loc.getChunk().getX(), loc.getChunk().getZ()));
            ps.setString(6, loc.getWorld().getName());
            ps.addBatch();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addSpawner(SpawnerTier tier, Location loc)
    {
        CompletableFuture.runAsync(() ->
        {
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("INSERT INTO spawners(tier,x,y,z,cx,cz,world) VALUES (?,?,?,?,?,?,?)"))
            {
                ps.setString(1, tier.getName());
                ps.setInt(2, loc.getBlockX());
                ps.setInt(3, loc.getBlockY());
                ps.setInt(4, loc.getBlockZ());
                ps.setInt(5, loc.getChunk().getX());
                ps.setInt(6, loc.getChunk().getZ());
                ps.setString(7, loc.getWorld().getName());
                ps.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, ArkShards.getInstance().getPool());
    }

    public CompletableFuture<Set<TieredSpawner>> getSpawnersInChunk(int cx, int cz)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            Set<TieredSpawner> spawnerSet = new HashSet();
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("SELECT tier,x,y,z,world where cx=? AND cz=?"))
            {
                ps.setInt(1, cx);
                ps.setInt(2, cz);
                try (ResultSet rs = ps.executeQuery())
                {

                    while (rs.next())
                    {
                        int x = rs.getInt("x");
                        int y = rs.getInt("y");
                        int z = rs.getInt("z");
                        String tier = rs.getString("tier");
                        String world = rs.getString("world");
                        spawnerSet.add(new TieredSpawner(new Location(Bukkit.getWorld(world), x, y, z), ArkSpawn.getInstance().getTierManager().getTierByName(tier)));
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }, ArkShards.getInstance().getPool());

    }

    public CompletableFuture<Void> createPlayerInDB(UUID uuid)
    {
        return CompletableFuture.runAsync(() ->
        {
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("INSERT INTO shards(uuid, count) VALUES (?,?)"))
            {
                ps.setString(1, uuid.toString());
                ps.setInt(2, 0);
                ps.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }, ArkShards.getInstance().getPool());
    }

    public CompletableFuture<AtomicInteger> getShards(UUID uuid)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("SELECT count from shards where uuid=? "))
            {
                ps.setString(1, uuid.toString());
                try (ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        return new AtomicInteger(rs.getInt("count"));
                    }
                    createPlayerInDB(uuid);
                    return new AtomicInteger(0);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }, ArkShards.getInstance().getPool());
    }
}

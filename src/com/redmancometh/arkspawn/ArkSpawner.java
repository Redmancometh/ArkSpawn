package com.redmancometh.arkspawn;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

public class ArkSpawner
{
    private Block block;
    private EntityType type;

    public ArkSpawner(EntityType type, Block block)
    {
        this.setType(type);
        this.setBlock(block);
    }

    public void tick()
    {
        //dologic
    }

    public Block getBlock()
    {
        return block;
    }

    public void setBlock(Block block)
    {
        this.block = block;
    }

    public EntityType getType()
    {
        return type;
    }

    public void setType(EntityType type)
    {
        this.type = type;
    }
}

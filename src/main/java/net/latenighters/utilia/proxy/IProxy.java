package net.latenighters.utilia.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {
    PlayerEntity getPlayer();
    World getWorld();
}

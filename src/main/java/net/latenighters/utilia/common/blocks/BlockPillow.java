package net.latenighters.utilia.common.blocks;

import net.latenighters.utilia.common.blocks.suppressor.TileSuppressor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPillow extends Block {
    public BlockPillow() {
        super(Properties.of(Material.WOOL)
                .sound(SoundType.WOOL)
        );
    }

    @Override
    public void fallOn(World world, BlockPos blockPos, Entity entity, float v) {
        super.fallOn(world, blockPos, entity, 0.0f);
    }
}

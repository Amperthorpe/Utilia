package net.latenighters.utilia.common.blocks.suppressor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockSuppressor extends Block {
    public BlockSuppressor() {
        super(AbstractBlock.Properties.of(Material.METAL)
                .sound(SoundType.METAL)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileSuppressor();
    }
}

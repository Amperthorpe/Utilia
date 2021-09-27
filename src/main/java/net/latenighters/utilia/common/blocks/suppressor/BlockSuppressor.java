package net.latenighters.utilia.common.blocks.suppressor;

import net.latenighters.utilia.Utilities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSuppressor extends Block {
    public BlockSuppressor() {
        super(AbstractBlock.Properties.of(Material.METAL)
                .sound(SoundType.METAL)
        );
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader blockReader, List<ITextComponent> textComponents, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockReader, textComponents, tooltipFlag);
        textComponents.add(Utilities.tooltipStyle("block.utilia.suppressor.tooltip"));

        textComponents.add(Utilities.addWIPText()); //TODO remove once working
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileSuppressor();
    }
}

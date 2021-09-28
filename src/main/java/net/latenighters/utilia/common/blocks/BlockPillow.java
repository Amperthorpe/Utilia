package net.latenighters.utilia.common.blocks;

import net.latenighters.utilia.Utilities;
import net.latenighters.utilia.common.blocks.suppressor.TileSuppressor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockPillow extends Block {
    public BlockPillow() {
        super(Properties.of(Material.WOOL)
                .sound(SoundType.WOOL)
        );
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader blockReader, List<ITextComponent> textComponents, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockReader, textComponents, tooltipFlag);
        textComponents.add(Utilities.tooltipStyle("block.utilia.pillow.tooltip"));
    }


    @Override
    public void fallOn(World world, BlockPos blockPos, Entity entity, float v) {
        super.fallOn(world, blockPos, entity, 0.0f);
    }
}

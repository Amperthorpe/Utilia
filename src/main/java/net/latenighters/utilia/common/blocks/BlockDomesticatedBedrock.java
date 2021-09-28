package net.latenighters.utilia.common.blocks;

import net.latenighters.utilia.Utilities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockDomesticatedBedrock extends Block {
    public BlockDomesticatedBedrock() {
        super(AbstractBlock.Properties.of(Material.STONE)
                .strength(-1.0F, 3600000.0F)
                .sound(SoundType.ANCIENT_DEBRIS)
        );
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader blockReader, List<ITextComponent> textComponents, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockReader, textComponents, tooltipFlag);
        textComponents.add(Utilities.tooltipStyle("block.utilia.domesticated_bedrock.tooltip"));
        textComponents.add(Utilities.loreStyle("block.utilia.domesticated_bedrock.lore"));
    }


    @Override
    public void attack(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        if (playerEntity.isSteppingCarefully()) {
            world.addFreshEntity(new ItemEntity(world, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), new ItemStack(this.asItem())));
            world.destroyBlock(blockPos, true, playerEntity);
        }

        super.attack(blockState, world, blockPos, playerEntity);
    }
}

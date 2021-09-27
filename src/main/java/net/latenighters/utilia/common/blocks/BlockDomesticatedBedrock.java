package net.latenighters.utilia.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDomesticatedBedrock extends Block {
    public BlockDomesticatedBedrock() {
        super(AbstractBlock.Properties.of(Material.STONE)
                .strength(-1.0F, 3600000.0F)
                .sound(SoundType.ANCIENT_DEBRIS)
        );
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

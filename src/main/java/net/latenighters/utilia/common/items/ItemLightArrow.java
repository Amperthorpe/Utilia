package net.latenighters.utilia.common.items;

import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.common.entities.LightArrowEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLightArrow extends ArrowItem {
    public ItemLightArrow() {
        super(new Properties()
                .stacksTo(1)
                .tab(Utilia.ITEM_GROUP)
        );
    }

    public AbstractArrowEntity createArrow(World world, ItemStack itemStack, LivingEntity livingEntity) {
        return new LightArrowEntity(world, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltipFlag) {
        text.add(new TranslationTextComponent("item.utilia.light_arrow.tooltip")
                .withStyle(TextFormatting.DARK_PURPLE).withStyle(TextFormatting.ITALIC));
        super.appendHoverText(itemStack, world, text, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if (!world.isClientSide) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (player.inventory.countItem(itemStack.getItem()) > 1) {
                    player.drop(itemStack, true);
                    itemStack.setCount(0);
                }
            } else {
                entity.hurt(DamageSource.MAGIC, 1f);
            }
            super.inventoryTick(itemStack, world, entity, p_77663_4_, p_77663_5_);
        }
    }
}

package net.latenighters.utilia.common.items;

import net.latenighters.utilia.Registration;
import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.Utilities;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBottledBedrock extends Item {
    public ItemBottledBedrock() {
        super(new Properties()
            .tab(Utilia.ITEM_GROUP)
        );
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltipFlag) {
        text.add(Utilities.tooltipStyle("item.utilia.bottled_bedrock.tooltip"));
        text.add(Utilities.loreStyle("item.utilia.bottled_bedrock.lore"));
    }


    public static void onPlayerRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isClientSide) {
            ItemStack stack = event.getItemStack();
            if (event.getItemStack().getItem() == Items.GLASS_BOTTLE) {
                BlockPos pos = event.getPos();
                ItemEntity itemEntity = new ItemEntity(event.getWorld(), pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(Registration.BOTTLED_BEDROCK.get().getItem()));

                stack.shrink(1);
                event.getWorld().addFreshEntity(itemEntity);
            }
        }
    }
}

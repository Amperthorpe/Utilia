package net.latenighters.utilia.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventSubscriber { // Style: Keep logic in item/block classes when possible.

    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event) {
//        AxisAlignedBB box = new AxisAlignedBB()

        Entity ent = event.getEntity();
        if (ent instanceof ItemEntity) {
            if (((ItemEntity) ent).getItem().getItem() == Items.DIAMOND) {
                event.setCanceled(true);
            }
        }
    }
}

package net.latenighters.utilia.common;

import net.latenighters.utilia.common.blocks.suppressor.TileSuppressor;
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
        TileSuppressor.onEntityJoinWorld(event);
    }
}

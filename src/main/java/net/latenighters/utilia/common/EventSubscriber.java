package net.latenighters.utilia.common;

import net.latenighters.utilia.common.blocks.suppressor.TileSuppressor;
import net.latenighters.utilia.common.items.ItemBottledBedrock;
import net.latenighters.utilia.common.items.SpikeBoots;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventSubscriber { // Style: Keep logic in item/block classes when possible.

    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event) {
//        TileSuppressor.onEntityJoinWorld(event);
    }

    @SubscribeEvent
    public static void onEvent(LivingFallEvent event) {
        SpikeBoots.handleLivingFallEvent(event);
    }

    @SubscribeEvent
    public static void onEvent(PlayerInteractEvent.RightClickBlock event) {
        ItemBottledBedrock.onPlayerRightClickBlockEvent(event);
    }

    @SubscribeEvent
    public static void onEvent(ItemTossEvent event) {

    }
}

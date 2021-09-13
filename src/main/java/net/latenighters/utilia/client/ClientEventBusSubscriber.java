package net.latenighters.utilia.client;

import net.latenighters.utilia.Registration;
import net.latenighters.utilia.Utilia;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Utilia.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onStaticClientSetup(FMLClientSetupEvent event) {
        event.setPhase(EventPriority.HIGH);
        RenderingRegistry.registerEntityRenderingHandler(Registration.LIGHT_ARROW_ENTITY.get(), LightArrowRenderer::new);
    }
}

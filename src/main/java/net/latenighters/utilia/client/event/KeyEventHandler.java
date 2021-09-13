package net.latenighters.utilia.client.event;

import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.client.KeyBindings;
import net.latenighters.utilia.client.gui.ScreenChalk;
import net.latenighters.utilia.common.items.ChalkItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import static net.latenighters.utilia.Utilia.MODID;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyEventHandler {

    @SubscribeEvent
    public static void onKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        if(KeyBindings.CHALK.isDown() && Minecraft.getInstance().screen == null
            && ((Utilia.proxy.getPlayer().getItemInHand(Hand.MAIN_HAND)!=null && Utilia.proxy.getPlayer().getItemInHand(Hand.MAIN_HAND).getItem() instanceof ChalkItem)
            || (Utilia.proxy.getPlayer().getItemInHand(Hand.OFF_HAND)!=null   && Utilia.proxy.getPlayer().getItemInHand(Hand.OFF_HAND).getItem()  instanceof ChalkItem)))
        {
            //force set screen?
            Minecraft.getInstance().setScreen(new ScreenChalk(new TranslationTextComponent(MODID+"gui.chalk_title")));
        }

    }

    public static void registerKeyBindings()
    {
        ArrayList<KeyBinding> bindings = new ArrayList<KeyBinding>();

        bindings.add(KeyBindings.CHALK);

        for(KeyBinding binding : bindings)
        {
            ClientRegistry.registerKeyBinding(binding);
        }
    }

}

package net.latenighters.utilia.common.symbols;


import net.latenighters.utilia.common.symbols.backend.Symbol;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

import static net.latenighters.utilia.Utilia.MODID;


@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SymbolTextures {

    public static final ResourceLocation DEBUG = new ResourceLocation(MODID , "symbols/symbol_x");
    public static final ResourceLocation EXPEL = new ResourceLocation(MODID , "symbols/symbol_expel");
    public static final ResourceLocation INSERT = new ResourceLocation(MODID , "symbols/symbol_insert");
    public static final ResourceLocation REDSTONE = new ResourceLocation(MODID , "symbols/symbol_redstone");
    public static final ResourceLocation BOOLEAN_LOGIC = new ResourceLocation(MODID , "symbols/symbol_boolean_logic");
    public static final ResourceLocation DETECT= new ResourceLocation(MODID , "symbols/symbol_detect");



    @SubscribeEvent
    public static void onStitchEvent(TextureStitchEvent.Pre event)
    {
        ResourceLocation stitching = event.getMap().location();
        if(!stitching.equals(AtlasTexture.LOCATION_BLOCKS))
        {
            return;
        }
        for (ResourceLocation tex:textureList)
        {
            event.addSprite(tex);
        }
    }

    public static void consolidateTextures(final RegistryEvent.Register<Symbol> evt)
    {

        for(Symbol symbol : evt.getRegistry().getValues())
        {
            if(!textureList.contains(symbol.getTexture()))
            {
                textureList.add(symbol.getTexture());
            }
        }
    }

    private static ArrayList<ResourceLocation> textureList = new ArrayList<ResourceLocation>();

}

package net.latenighters.utilia;

import net.latenighters.utilia.client.event.KeyEventHandler;
import net.latenighters.utilia.common.event.CommonEventHandler;
import net.latenighters.utilia.common.symbols.backend.Symbol;
import net.latenighters.utilia.common.symbols.backend.SymbolRegistration;
import net.latenighters.utilia.common.symbols.backend.SymbolRegistryHandler;
import net.latenighters.utilia.common.symbols.backend.capability.ISymbolHandler;
import net.latenighters.utilia.common.symbols.backend.capability.SymbolHandler;
import net.latenighters.utilia.common.symbols.backend.capability.SymbolHandlerStorage;
import net.latenighters.utilia.common.symbols.backend.capability.SymbolSyncer;
import net.latenighters.utilia.common.symbols.categories.SymbolCategory;
import net.latenighters.utilia.network.ClickableHandler;
import net.latenighters.utilia.network.NetworkSync;
import net.latenighters.utilia.proxy.ClientProxy;
import net.latenighters.utilia.proxy.IProxy;
import net.latenighters.utilia.proxy.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("utilia")
public class Utilia
{
    public static final String MODID = "utilia";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    //Capability Registration
    @CapabilityInject(ISymbolHandler.class)
    public static Capability<ISymbolHandler> SYMBOL_CAP = null;

    public static IProxy proxy;

    public Utilia() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        //register the modloading functions
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doServerStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onSetupComplete);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SymbolRegistryHandler::onCreateRegistryEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Symbol.class, SymbolRegistration::registerSymbols);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
        MinecraftForge.EVENT_BUS.register(new SymbolSyncer());
        SymbolSyncer.registerPackets();
        NetworkSync.registerPackets();
        ClickableHandler.registerPackets();
        Registration.init();
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STICK);
        }
    };

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        SymbolHandlerStorage storage = new SymbolHandlerStorage();
        SymbolHandler.SymbolHandlerFactory factory = new SymbolHandler.SymbolHandlerFactory();
        CapabilityManager.INSTANCE.register(ISymbolHandler.class, storage, factory);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        proxy = new ClientProxy();
        KeyEventHandler.registerKeyBindings();
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    }

    private void doServerStuff(final FMLDedicatedServerSetupEvent event) {
        proxy = new ServerProxy();
    }
    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(MODID, "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    private void onSetupComplete(final FMLLoadCompleteEvent event)
    {
        SymbolCategory.generateCategories();  //TODO remove this when static initialization is working
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts

        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}

package net.latenighters.utilia;

import net.latenighters.utilia.common.blocks.BlockDomesticatedBedrock;
import net.latenighters.utilia.common.blocks.BlockPillow;
import net.latenighters.utilia.common.blocks.suppressor.BlockSuppressor;
import net.latenighters.utilia.common.blocks.suppressor.TileSuppressor;
import net.latenighters.utilia.common.entities.LightArrowEntity;
import net.latenighters.utilia.common.items.ChalkItem;
import net.latenighters.utilia.common.items.ItemLightArrow;
import net.latenighters.utilia.common.items.LongFallBoots;
import net.latenighters.utilia.common.items.SpikeBoots;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.latenighters.utilia.Utilia.MODID;

@SuppressWarnings("unused")
public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
    private static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        WORLD_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Item Registration
    //public static final RegistryObject<ItemPuncher> PUNCHER = ITEMS.register("puncher", ItemPuncher::new);
    public static final RegistryObject<ChalkItem> CHALK = ITEMS.register("chalk", ChalkItem::new);
    public static final RegistryObject<ItemLightArrow> LIGHT_ARROW = ITEMS.register("light_arrow", ItemLightArrow::new);
    public static final RegistryObject<LongFallBoots> LONG_FALL_BOOTS = ITEMS.register("long_fall_boots", LongFallBoots::new);
    public static final RegistryObject<SpikeBoots> SPIKE_BOOTS = ITEMS.register("spike_boots", SpikeBoots::new);

    // Block Registration
    public static final RegistryObject<BlockSuppressor> SUPPRESSOR_BLOCK = BLOCKS.register("suppressor", BlockSuppressor::new);
    public static final RegistryObject<Item> SUPPRESSOR_BLOCK_ITEM = ITEMS.register("suppressor", () -> new BlockItem(SUPPRESSOR_BLOCK.get(), new Item.Properties().tab(Utilia.ITEM_GROUP)));
    public static final RegistryObject<BlockPillow> PILLOW_BLOCK = BLOCKS.register("pillow", BlockPillow::new);
    public static final RegistryObject<Item> PILLOW_BLOCK_ITEM = ITEMS.register("pillow", () -> new BlockItem(PILLOW_BLOCK.get(), new Item.Properties().tab(Utilia.ITEM_GROUP)));
    public static final RegistryObject<BlockDomesticatedBedrock> DOMESTICATED_BEDROCK_BLOCK = BLOCKS.register("domesticated_bedrock", BlockDomesticatedBedrock::new);
    public static final RegistryObject<Item> DOMESTICATED_BEDROCK_ITEM = ITEMS.register("domesticated_bedrock", () -> new BlockItem(DOMESTICATED_BEDROCK_BLOCK.get(), new Item.Properties().tab(Utilia.ITEM_GROUP)));


    // Tile Entity Registration
    public static final RegistryObject<TileEntityType<TileSuppressor>> SUPPRESSOR_TILE = TILES.register("suppressor_tile", () -> TileEntityType.Builder.of(TileSuppressor::new, SUPPRESSOR_BLOCK.get()).build(null));

    // Entity Registration
    public static final RegistryObject<EntityType<LightArrowEntity>> LIGHT_ARROW_ENTITY =
            ENTITIES.register("light_arrow_entity", () -> EntityType.Builder.<LightArrowEntity>of(LightArrowEntity::new,
                EntityClassification.MISC).sized(0.5f, 0.5f)
                .build(new ResourceLocation(MODID, "textures/entity/arrows").toString()));


}

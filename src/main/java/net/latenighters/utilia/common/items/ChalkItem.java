package net.latenighters.utilia.common.items;

import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.client.gui.OverlayPopup;
import net.latenighters.utilia.common.symbols.Symbols;
import net.latenighters.utilia.common.symbols.backend.*;
import net.latenighters.utilia.common.symbols.backend.capability.ISymbolHandler;
import net.latenighters.utilia.common.symbols.backend.capability.SymbolSyncer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class ChalkItem extends Item {

    public static AtomicReference<HashableTuple<String, DataType>> selectedFunction;

    public ChalkItem() {
        super(new Properties().stacksTo(1).tab(Utilia.ITEM_GROUP));
    }

    private static final int DIRTY_RANGE = 20;


    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        LazyOptional<ISymbolHandler> symbolOp = context.getLevel().getChunkAt(context.getClickedPos()).getCapability(Utilia.SYMBOL_CAP);
        symbolOp.ifPresent(symbols -> {

            if (!context.getLevel().isClientSide)
            {
                //TODO this will not work if shift+right clicking on a non-symbol functional object
                Chunk chunk = context.getLevel().getChunkAt(context.getClickedPos());
                Symbol symbolToDraw = null;
                symbolToDraw = RegistryManager.ACTIVE.getRegistry(Symbol.class)
                        .getValue(new ResourceLocation(context.getItemInHand().getOrCreateTag().contains("selected_symbol")
                        ? context.getItemInHand().getOrCreateTag().getString("selected_symbol") : Symbols.DEBUG.getRegistryName().toString()));

                symbols.addSymbol(new DrawnSymbol(symbolToDraw, context.getClickedPos(), context.getClickedFace(),chunk), chunk);

                for(PlayerEntity player : context.getLevel().players())
                {
                    if(player.xChunk > chunk.getPos().x - DIRTY_RANGE && player.xChunk < chunk.getPos().x + DIRTY_RANGE &&
                       player.zChunk > chunk.getPos().z - DIRTY_RANGE && player.zChunk < chunk.getPos().z + DIRTY_RANGE)
                    {
                        SymbolSyncer.SymbolDirtyMessage msg = new SymbolSyncer.SymbolDirtyMessage(chunk.getPos());
                        SymbolSyncer.INSTANCE.sendTo( msg, ((ServerPlayerEntity)(player)).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
                    }
                }
            }
            else if(context.getLevel().isClientSide && context.getPlayer().isSteppingCarefully())
            {
                IFunctionalObject symbol = SymbolUtil.getLookedFunctionalObject();
                if(symbol !=null) {

                    ItemStack chalk = context.getItemInHand();
                    Chunk chunk = context.getLevel().getChunkAt(context.getClickedPos());

                    if (!chalk.getOrCreateTag().contains("linking_from") && selectedFunction!=null && selectedFunction.get()!=null) {
                        CompoundNBT nbt = symbol.basicSerializeNBT();
                        nbt.putString("func",selectedFunction.get().getA());
                        nbt.putString("type",selectedFunction.get().getB().name);
                        chalk.getTag().put("linking_from",nbt);
                    }else
                    {
                        sendNBTToServer(symbol,chalk,chunk);
                    }
                }


            }
        });

        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void sendNBTToServer(IFunctionalObject symbol, ItemStack chalk, Chunk chunk)
    {
        if(OverlayPopup.selectedFunction.get()==null) return;
        IFunctionalObject object = null;
        try {
            object = symbol.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(object==null)return;
        if(!chalk.getOrCreateTag().contains("linking_from"))return;
        if (chalk.getTag() != null) {
            object.deserializeNBT(chalk.getTag().getCompound("linking_from"));
        }
        object = object.findReal(chunk);
        if(object==null)return;

        SymbolSyncer.INSTANCE.sendToServer(new SymbolSyncer.SymbolLinkMessage(object,symbol,
                new HashableTuple<>(chalk.getTag().getCompound("linking_from").getString("func"),
                DataType.getDataType(chalk.getTag().getCompound("linking_from").getString("type"))),
                OverlayPopup.selectedFunction.get().getA(),OverlayPopup.selectedFunction.get().getB().name));

        chalk.getTag().remove("linking_from");
        OverlayPopup.funcObject.set(null);
        OverlayPopup.selectedFunction.set(null);
        OverlayPopup.updateRenderList(chalk,null);
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putString("selected_symbol",Symbols.DEBUG.getRegistryName().toString());
        return super.createEntity(world, location, stack);
    }


    public static class ChalkSyncMessage
    {
        public Symbol selectedSymbol;
        public Hand hand;

        public ChalkSyncMessage(Symbol selectedSymbol, Hand hand)
        {
            this.selectedSymbol = selectedSymbol;
            this.hand = hand;
        }

        public static void encode(final ChalkSyncMessage msg, final PacketBuffer buf)
        {
            buf.writeUtf(msg.selectedSymbol.getRegistryName().toString());
            buf.writeBoolean(msg.hand.equals(Hand.MAIN_HAND));
        }

        public static ChalkSyncMessage decode(final PacketBuffer buf)
        {
            Symbol symbol = RegistryManager.ACTIVE.getRegistry(Symbol.class).getValue(new ResourceLocation(buf.readUtf(128)));
            Hand hand = buf.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;

            return new ChalkSyncMessage(symbol, hand);
        }

        public static void handle(final ChalkSyncMessage msg, final Supplier<NetworkEvent.Context> contextSupplier)
        {
            final NetworkEvent.Context context = contextSupplier.get();
            if (context.getDirection().equals(NetworkDirection.PLAY_TO_CLIENT))
            {

                context.setPacketHandled(true);
            }
            else if (context.getDirection().equals(NetworkDirection.PLAY_TO_SERVER))
            {
                if(context.getSender()==null)return;
                ItemStack chalk = context.getSender().getItemInHand(msg.hand);
                chalk.getOrCreateTag().putString("selected_symbol", msg.selectedSymbol.getRegistryName().toString());

                context.setPacketHandled(true);
            }
        }

    }





}

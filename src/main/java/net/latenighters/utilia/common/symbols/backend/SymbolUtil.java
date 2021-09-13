package net.latenighters.utilia.common.symbols.backend;

import net.latenighters.utilia.Utilia;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SymbolUtil {

    //client-side only
    public static IFunctionalObject getLookedFunctionalObject(){
        if(Utilia.proxy.getWorld()==null)return null;
        AtomicReference<IFunctionalObject> symbol = new AtomicReference<>();
        symbol.set(null);

        //first check for a symbol that the player is looking at
        PlayerEntity player = Utilia.proxy.getPlayer();
        if (Minecraft.getInstance().hitResult != null && Minecraft.getInstance().hitResult.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockRayTrace = (BlockRayTraceResult)Minecraft.getInstance().hitResult;
            IChunk chunk = Utilia.proxy.getWorld().getChunk(blockRayTrace.getBlockPos());

            if(chunk instanceof Chunk)
                ((Chunk)chunk).getCapability(Utilia.SYMBOL_CAP).ifPresent(symbolHandler ->{
                    symbol.set(symbolHandler.getSymbolAt(blockRayTrace.getBlockPos(), blockRayTrace.getDirection()));
                });
        }
        return symbol.get();
    }

    public static void parseArguments(List<HashableTuple<String, Object>> args, Map<String, AtomicReference<Object>> objectsToFill)
    {
        args.forEach(arg ->{
            if(objectsToFill.containsKey(arg.getA()) && arg.getB().getClass().isAssignableFrom(objectsToFill.get(arg.getA()).getClass()))
                objectsToFill.get(arg.getA()).set(arg.getB());
        });
    }
}

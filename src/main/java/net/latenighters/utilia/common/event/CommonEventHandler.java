package net.latenighters.utilia.common.event;

import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.common.symbols.backend.capability.SymbolHandler;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEventHandler {

    @SubscribeEvent
    public void onChunkAttachCapabilitiesEvent(AttachCapabilitiesEvent<Chunk> evt)
    {
        if (!evt.getObject().getCapability(Utilia.SYMBOL_CAP).isPresent())
            evt.addCapability(SymbolHandler.NAME, new SymbolHandler());
    }

    @SubscribeEvent
    public void onChunkTickEvent(TickEvent.WorldTickEvent evt)
    {
        if(evt.side.isClient())
            return;

        Iterable<ChunkHolder> loadedChunks = ServerChunks.getLoadedChunks((ServerWorld) evt.world);
        if(loadedChunks==null) return;

        int chunksTicked = 0;

        //give each symbol handler a tick
        for(ChunkHolder chunkHolder : loadedChunks)
        {
            //Chunk chunk = chunkHolder.getChunkIfComplete();
            Chunk chunk = chunkHolder.getTickingChunk();
            if(chunk==null)continue;

            chunksTicked++;
            chunk.getCapability(Utilia.SYMBOL_CAP).ifPresent(symbolHandler ->{
                symbolHandler.tick(evt.world, chunk);
            });
        }
        chunksTicked++;
    }
}

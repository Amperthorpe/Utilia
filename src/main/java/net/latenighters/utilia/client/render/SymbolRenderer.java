package net.latenighters.utilia.client.render;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.common.symbols.backend.DrawnSymbol;
import net.latenighters.utilia.common.symbols.backend.capability.ISymbolHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SymbolRenderer {

    //range to render symbols in chunks
    private static int symbol_render_range = 1;

    public static void renderSymbols(RenderWorldLastEvent evt)
    {

        PlayerEntity player = Utilia.proxy.getPlayer();
        if(player == null) return;
        Chunk homeChunk = player.level.getChunkAt(player.blockPosition());

        ArrayList<Chunk> renderChunks = new ArrayList<>();
        int range = 3;
        for(int i=-range; i<range; i++){
            for (int j=-range; j<range; j++){
                renderChunks.add(player.level.getChunkAt(player.blockPosition().south(i*16).east(j*16)));
            }
        }

//        Long2ObjectMap<Chunk> renderChunks;
//
//        try {
//            Field chunkMapping = ClientChunkProvider.class.getDeclaredField("chunkMapping");
//            chunkMapping.setAccessible(true);
//            renderChunks = (Long2ObjectMap<Chunk>) chunkMapping.get(player.level.getChunkSource());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }


        renderChunks.forEach(chunk -> {
            LazyOptional<ISymbolHandler> symbolOp = chunk.getCapability(Utilia.SYMBOL_CAP);
            symbolOp.ifPresent(symbols -> {

                Vector3f projectedView = new Vector3f(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition());
                ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getMainCamera();

                MatrixStack matrix = evt.getMatrixStack();

        //        matrix.translate(-projectedView.x, -projectedView.y, -projectedView.z);
        //        Matrix4f matrix4f = matrix.getLast().getMatrix();
        //        RenderSystem.multMatrix(matrix4f);

                GlStateManager._pushMatrix();
                //Minecraft.getInstance().getRenderManager().textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
                GlStateManager._bindTexture(Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).getId());


        //        GlStateManager.translated(-projectedView.x, -projectedView.y, -projectedView.z);
                GlStateManager._rotatef(renderInfo.getXRot(), 1, 0, 0); // Fixes camera rotation.
                GlStateManager._rotatef(renderInfo.getYRot() + 180, 0, 1, 0); // Fixes camera rotation.
                GlStateManager._translated(-projectedView.x(), -projectedView.y(), -projectedView.z());

                GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager._enableAlphaTest();
                GlStateManager._enableBlend();
        //        GlStateManager.disableTexture();
    //            GlStateManager._enableLighting();
                GlStateManager._disableLighting();

                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //
        //        RenderSystem.enableDepthTest();
        //        RenderSystem.enableAlphaTest();
        //        RenderSystem.enableLighting();

                //TODO: move this to a chunk tick or something
                //symbols.clientSync(homeChunk.getPos());

                if(symbols.getSymbols().size()>0)
                {
                    for(DrawnSymbol sym : symbols.getSymbols())
                    {
                        GlStateManager._pushMatrix();
                        renderSymbol(sym, matrix);
                        GlStateManager._popMatrix();
                    }
                }

                //        matrix.pop();
                GlStateManager._popMatrix();

            });

        });

    }

    public static void add(IVertexBuilder renderer, MatrixStack matrix, float x, float y, float z, float u, float v)
    {
        renderer.vertex(matrix.last().pose(),x,y,z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .uv(u,v)
                .uv2(0,240)
                .normal(0, 1, 0)
                .endVertex();
    }

    public static void renderSymbol(DrawnSymbol symbol, MatrixStack matrix)
    {
        assert Utilia.proxy.getWorld() != null;


//        IRenderTypeBuffer buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
//        IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucent());
//        ResourceLocation textureToGrab = symbol.getTexture();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(symbol.getTexture());

//
//        BlockPos pos = symbol.getDrawnOn();
//
//        switch (symbol.getBlockFace())
//        {
//            case UP:
//                add(builder,matrix,pos.getX(), pos.getY()+1.05F, pos.getZ(), sprite.getMinU(),sprite.getMinV());
//                add(builder,matrix,pos.getX(), pos.getY()+1.05F, pos.getZ()+1.05F, sprite.getMinU(),sprite.getMaxV());
//                add(builder,matrix,pos.getX()+1.05F, pos.getY()+1.05F, pos.getZ()+1.05F, sprite.getMaxU(),sprite.getMaxV());
//                add(builder,matrix,pos.getX()+1.05F, pos.getY()+1.05F, pos.getZ(), sprite.getMaxU(),sprite.getMinV());
//                break;
//            default:
//
//        }

//        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//        ItemStack stack = new ItemStack(Items.DIAMOND);
//        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntity.getWorld(), null);
//        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrix, buffer, combinedLight, combinedOverlay, ibakedmodel);


        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
//        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(symbol.getSymbol().getTexture());
        BlockPos pos = symbol.getDrawnOn();
        GlStateManager._translated(pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f);

        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        switch (symbol.getBlockFace())
        {
            case DOWN:
                GlStateManager._rotatef(180, 1, 0, 0);
                break;
            case EAST:
                GlStateManager._rotatef(-90, 0, 0, 1);
                break;
            case WEST:
                GlStateManager._rotatef(90, 0, 0, 1);
                break;
            case NORTH:
                GlStateManager._rotatef(-90, 1, 0, 0);
                break;
            case SOUTH:
                GlStateManager._rotatef(90, 1, 0, 0);
                break;
        }
        GlStateManager._rotatef(symbol.getWork()/10.0f,0,1,0);

        bufferBuilder.vertex(-0.5, 0.51, -0.5).uv(sprite.getU0(),sprite.getV0()).normal(0, 1, 0).endVertex();
        bufferBuilder.vertex(-0.5, 0.51,  0.5).uv(sprite.getU0(),sprite.getV1()).normal(0, 1, 0).endVertex();
        bufferBuilder.vertex( 0.5, 0.51,  0.5).uv(sprite.getU1(),sprite.getV1()).normal(0, 1, 0).endVertex();
        bufferBuilder.vertex( 0.5, 0.51, -0.5).uv(sprite.getU1(),sprite.getV0()).normal(0, 1, 0).endVertex();

        bufferBuilder.vertex(-0.5, 0.49, -0.5).uv(sprite.getU0(),sprite.getV0()).normal(0, 1, 0).endVertex();
        bufferBuilder.vertex( 0.5, 0.49, -0.5).uv(sprite.getU0(),sprite.getV1()).normal(0, 1, 0).endVertex();
        bufferBuilder.vertex( 0.5, 0.49,  0.5).uv(sprite.getU1(),sprite.getV1()).normal(0, 1, 0).endVertex();
        bufferBuilder.vertex(-0.5, 0.49,  0.5).uv(sprite.getU1(),sprite.getV0()).normal(0, 1, 0).endVertex();

        tessellator.end();

//        GL11.glBegin(GL11.GL_QUADS);
//
//        BlockPos pos = symbol.getDrawnOn();
//
//        switch (symbol.getBlockFace())
//        {
//            case UP:
//
//                GL11.glVertex3d(pos.getX(), pos.getY() + 1, pos.getZ());
//                GL11.glVertex3d(pos.getX() + 1, pos.getY() + 1, pos.getZ());
//                GL11.glVertex3d(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
//                GL11.glVertex3d(pos.getX(), pos.getY() + 1, pos.getZ() + 1);
//                GL11.glNormal3d(0,-1,0);
//                GL11.glNormal3d(0,-1,0);
//                GL11.glNormal3d(0,-1,0);
//                GL11.glNormal3d(0,-1,0);
//
//                break;
//            case DOWN:
//
//                break;
//        }
//
//        GL11.glEnd();


    }
}

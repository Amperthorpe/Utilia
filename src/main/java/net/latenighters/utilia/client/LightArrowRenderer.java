package net.latenighters.utilia.client;

import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.common.entities.LightArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class LightArrowRenderer extends ArrowRenderer<LightArrowEntity> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Utilia.MODID, "textures/entity/arrows/poison_arrow.png");

    public LightArrowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(LightArrowEntity entity) {
        return TEXTURE;
    }


}

package net.latenighters.utilia.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.common.items.ChalkItem;
import net.latenighters.utilia.common.symbols.Symbols;
import net.latenighters.utilia.common.symbols.backend.Symbol;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.RegistryManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

public class SymbolButton extends Button {

    private final Symbol symbol;
    private final boolean is_category;

    public Symbol getSymbol() {
        return symbol;
    }

    public SymbolButton(Symbol symbol, int xIn, int yIn, int widthIn, int heightIn, IPressable onPress) {
        this(symbol, xIn, yIn, widthIn, heightIn, onPress, false);
    }

    public SymbolButton(Symbol symbol, int xIn, int yIn, int widthIn, int heightIn, IPressable onPress, boolean is_category ) {
        super(xIn, yIn, widthIn, heightIn, symbol.getTextComponent(), onPress);
        this.symbol = symbol;
        this.is_category = is_category;
    }

    @Override
    public void onPress() {
        super.onPress();
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {

        if(this.visible)
        {
            GlStateManager._bindTexture(Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).getId());
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(symbol.getTexture());

            GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager._enableBlend();
            //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            PlayerEntity playerEntity = Utilia.proxy.getPlayer();
            ItemStack chalk;
            if(playerEntity.getItemInHand(Hand.MAIN_HAND)!=null&&playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ChalkItem) {
                chalk = playerEntity.getItemInHand(Hand.MAIN_HAND);
            }
            else if(playerEntity.getItemInHand(Hand.MAIN_HAND)!=null&&playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ChalkItem) {
                chalk = playerEntity.getItemInHand(Hand.MAIN_HAND);
            }
            else
            {
                Minecraft.getInstance().setScreen(null);
                return;
            }

            Symbol chalkSymbol = RegistryManager.ACTIVE.getRegistry(Symbol.class)
                    .getValue(new ResourceLocation(chalk.getOrCreateTag().contains("selected_symbol")
                            ? chalk.getOrCreateTag().getString("selected_symbol") : Symbols.DEBUG.getRegistryName().toString()));

            if(this.is_category)
            {
                if (symbol == chalkSymbol.getCategory().getDisplaySymbol())
                {
                    GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
                }else
                {
                    GlStateManager._color4f(1.0F, 1.0F, 1.0F, 0.5F);
                }
            }
            else {
                if (symbol == chalkSymbol) {
                    GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
                }else
                {
                    GlStateManager._color4f(1.0F, 1.0F, 1.0F, 0.5F);
                }
            }

            GlStateManager._pushMatrix();
            blit(stack, this.x, this.y, 0, this.width, this.height, sprite);
            GlStateManager._popMatrix();
        }




        //super.renderButton(mouseX, mouseY, partialTicks);
    }

}

package net.latenighters.utilia.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.common.items.ChalkItem;
import net.latenighters.utilia.common.symbols.Symbols;
import net.latenighters.utilia.common.symbols.backend.Symbol;
import net.latenighters.utilia.common.symbols.backend.capability.SymbolSyncer;
import net.latenighters.utilia.common.symbols.categories.SymbolCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.registries.RegistryManager;

import java.util.ArrayList;

public class ScreenChalk extends Screen {

    private boolean shifting = false;
    private int categorySelected = 0;
    private final int guiHeight = 140;
    private final int guiWidth  = 200;

    public ScreenChalk(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().textureManager.bind(GuiTextures.CHALK_BACKGROUND);
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(GuiTextures.CHALK_BACKGROUND);

        int xStart = (width-guiWidth)/30;
        int yStart = (int)((height-guiHeight)*0.95);
        //drawTexturedModalRect(xStart,yStart,0,0,guiWidth,guiHeight);
        this.blit(stack,xStart,yStart,0,0,guiWidth,guiHeight);

        this.buttons.forEach(buton ->{
            if(buton.isHovered() && buton instanceof SymbolButton){
                SymbolButton symbolButton = (SymbolButton)buton;
                this.renderTooltip(stack, new StringTextComponent(symbolButton.getSymbol().getName()), mouseX, mouseY);
            }
        });

        super.render(stack,mouseX, mouseY, partialTicks);
    }

    @Override
    public void init(){
        buttons.clear();
        PlayerEntity playerEntity = Utilia.proxy.getPlayer();
        if(playerEntity.getItemInHand(Hand.MAIN_HAND)!=null&&playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ChalkItem)
        {
            ItemStack chalk = playerEntity.getItemInHand(Hand.MAIN_HAND);
            if(!chalk.getOrCreateTag().contains("selected_symbol"))
                chalk.getTag().putString("selected_symbol", Symbols.DEBUG.getRegistryName().toString());

        }
        else if(playerEntity.getItemInHand(Hand.OFF_HAND)!=null&&playerEntity.getItemInHand(Hand.OFF_HAND).getItem() instanceof ChalkItem)
        {
            ItemStack chalk = playerEntity.getItemInHand(Hand.OFF_HAND);
            if(!chalk.getOrCreateTag().contains("selected_symbol"))
                chalk.getTag().putString("selected_symbol", Symbols.DEBUG.getRegistryName().toString());
        }
        addButtons();
        super.init();
    }

    @Override
    public void tick() {
        super.tick();
        //if(Minecraft.getInstance().gameSettings.key)
//        if(!KeyBindings.CHALK.isKeyDown())
//            Minecraft.getInstance().displayGuiScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }



    public void addButtons()
    {
        buttons.clear();

        int buttonWidth = (guiWidth/8);
        int buttonHeight = buttonWidth;
        int xStart  = (width-guiWidth)/30;
        int yStart  = (int)((height-guiHeight)*0.95);
        int xEnd    = (xStart+guiWidth)-buttonWidth;
        int yEnd    = (yStart+guiHeight);
        int xMargin = (guiWidth/20);
        int yMargin = (guiHeight/48);
        int xSpacing = (guiWidth/30);
        int ySpacing = (guiHeight/30);


        int x = xStart+xMargin;
        int y = yStart+yMargin;


        //find all categories
        ArrayList<SymbolCategory> categories = new ArrayList<SymbolCategory>();
        for(Symbol symbol : RegistryManager.ACTIVE.getRegistry(Symbol.class).getValues()) {
            if(!categories.contains(symbol.getCategory())) {
                categories.add(symbol.getCategory());
            }
        }

        for(int i=0; i<categories.size(); i++)
        {
            SymbolCategory cat = categories.get(i);
            SymbolButton newButt = new SymbolButton(cat.getDisplaySymbol(),x,y,buttonWidth,buttonHeight, new CategoryButtonPress(cat,this,i),true);
            //buttons.add(newButt);
            this.addButton(newButt);
            x = x + xSpacing + buttonWidth;
        }

        x = xStart+xMargin;
        y = y+ySpacing+buttonHeight+yMargin;

        //fill the category with the buttons in it
        for(Symbol symbol : RegistryManager.ACTIVE.getRegistry(Symbol.class).getValues())
        {
            if(symbol.getCategory()!=categories.get(categorySelected))
                continue;

            SymbolButton newButt = new SymbolButton(symbol,x,y,buttonWidth,buttonHeight, new SymbolButtonPress(symbol));
            //buttons.add(newButt);
            this.addButton(newButt);
            x = x + xSpacing + buttonWidth;
            if(x>xEnd)
            {
                x = xStart+xMargin;
                y = y+ySpacing+buttonHeight;
            }
        }

    }

    public static class SymbolButtonPress implements Button.IPressable {

        Symbol symbol;

        public SymbolButtonPress(Symbol symbol) {
            this.symbol = symbol;
        }

        @Override
        public void onPress(Button p_onPress_1_) {
            PlayerEntity playerEntity = Utilia.proxy.getPlayer();
            ItemStack chalk;
            if(playerEntity.getItemInHand(Hand.MAIN_HAND)!=null&&playerEntity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof ChalkItem) {
                chalk = playerEntity.getItemInHand(Hand.MAIN_HAND);
                SymbolSyncer.INSTANCE.sendToServer(new ChalkItem.ChalkSyncMessage(symbol, Hand.MAIN_HAND));
            }
            else if(playerEntity.getItemInHand(Hand.OFF_HAND)!=null&&playerEntity.getItemInHand(Hand.OFF_HAND).getItem() instanceof ChalkItem) {
                chalk = playerEntity.getItemInHand(Hand.OFF_HAND);
                SymbolSyncer.INSTANCE.sendToServer(new ChalkItem.ChalkSyncMessage(symbol, Hand.OFF_HAND));
            }
            else {
                Minecraft.getInstance().setScreen(null);
                return;
            }

            chalk.getTag().putString("selected_symbol",symbol.getRegistryName().toString());



        }
    }

    public static class CategoryButtonPress implements Button.IPressable {

        final SymbolCategory category;
        final ScreenChalk screenChalk;
        int catID;

        public CategoryButtonPress(SymbolCategory category, ScreenChalk screenChalk, int catID) {
            this.category = category;
            this.screenChalk = screenChalk;
            this.catID = catID;
        }

        @Override
        public void onPress(Button p_onPress_1_) {
            screenChalk.categorySelected = catID;
        }
    }

}

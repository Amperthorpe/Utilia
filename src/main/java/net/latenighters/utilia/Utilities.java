package net.latenighters.utilia;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class Utilities {
    public static ITextComponent loreStyle(ITextComponent textComponent){
        return textComponent.plainCopy().withStyle(TextFormatting.DARK_PURPLE).withStyle(TextFormatting.ITALIC);
    }

    public static ITextComponent loreStyle(String key, Object...args){
        return loreStyle(new TranslationTextComponent(key, args));
    }

    public static ITextComponent tooltipStyle(ITextComponent textComponent){
        return textComponent.plainCopy().withStyle(TextFormatting.GRAY);
    }

    public static ITextComponent tooltipStyle(String key, Object... args){
        return tooltipStyle(new TranslationTextComponent(key, args));
    }

    public static boolean checkHeadspace(World world, BlockPos pos){
        return world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above());
    }

}

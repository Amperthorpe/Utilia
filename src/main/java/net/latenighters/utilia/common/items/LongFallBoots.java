package net.latenighters.utilia.common.items;

import net.latenighters.utilia.Utilia;
import net.latenighters.utilia.Utilities;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LongFallBoots extends ArmorItem {

    private static final IArmorMaterial longFallMat =
            new IArmorMaterial() {
                @Override
                public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) { return -1; }

                @Override
                public int getDefenseForSlot(EquipmentSlotType p_200902_1_) { return 0; }

                @Override
                public int getEnchantmentValue() { return 0; }

                @Override
                public SoundEvent getEquipSound() { return null; }

                @Override
                public Ingredient getRepairIngredient() { return null; }

                @Override
                public String getName() { return "utilia:longfallmat"; }

                @Override
                public float getToughness() { return 0; }

                @Override
                public float getKnockbackResistance() { return 0; }
            };
    public LongFallBoots() {
        super(
                longFallMat,
                EquipmentSlotType.FEET,
                new Properties()
                    .durability(-1)
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)
                    .tab(Utilia.ITEM_GROUP)
        );
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltipFlag) {
        text.add(Utilities.tooltipStyle("item.utilia.long_fall_boots.tooltip"));
        text.add(Utilities.loreStyle("item.utilia.long_fall_boots.lore"));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.fallDistance = 0.0f;
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) { return false; }

    @Override
    public boolean isDamageable(ItemStack stack) { return false; }
}

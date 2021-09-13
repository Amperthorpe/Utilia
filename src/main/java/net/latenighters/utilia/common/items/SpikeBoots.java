package net.latenighters.utilia.common.items;

import net.latenighters.utilia.Utilia;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Predicate;

public class SpikeBoots extends ArmorItem {

    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = LivingEntity::attackable;
    private static final double range = 1.0;
    private static final EntityPredicate TARGETING_CONDITIONS = (new EntityPredicate()).range(range).selector(LIVING_ENTITY_SELECTOR);

    public static void handleLivingFallEvent(LivingFallEvent event) {
        World world = event.getEntity().level;
        if (!world.isClientSide && event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            List<LivingEntity> nearbyEntities = world.getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, player, event.getEntity().getBoundingBox());
            for (LivingEntity ent : nearbyEntities) {
                if (ent.getType() == EntityType.RABBIT || ent.getType() == EntityType.SILVERFISH || ent.getType() == EntityType.ENDERMITE) {
                    ent.kill();
                }
                ent.hurt(DamageSource.CRAMMING, event.getDamageMultiplier() * event.getDistance());
            }
        }
    }

    private static final IArmorMaterial spikeBootMat =
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

    public SpikeBoots() {
        super(
                spikeBootMat,
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
    public boolean isEnchantable(ItemStack p_77616_1_) { return false; }

    @Override
    public boolean isDamageable(ItemStack stack) { return false; }
}

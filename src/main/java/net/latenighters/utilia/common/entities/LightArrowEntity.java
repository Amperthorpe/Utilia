package net.latenighters.utilia.common.entities;

import net.latenighters.utilia.Registration;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LightArrowEntity extends ArrowEntity {
    public LightArrowEntity(World world, LivingEntity livingEntity) {
        super(world, livingEntity);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Registration.LIGHT_ARROW.get());
    }

    @Override
    public double getBaseDamage() {
        return 10f;
    }
}

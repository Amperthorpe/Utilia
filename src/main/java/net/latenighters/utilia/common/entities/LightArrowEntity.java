package net.latenighters.utilia.common.entities;

import net.latenighters.utilia.Registration;
import net.latenighters.utilia.common.items.ItemLightArrow;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightArrowEntity extends AbstractArrowEntity {


    public LightArrowEntity(EntityType<? extends LightArrowEntity> type, World world) {
        super(type, world);
    }

    public LightArrowEntity(World worldIn, LivingEntity shooter) {
        super(Registration.LIGHT_ARROW_ENTITY.get(), shooter, worldIn);
    }

    public LightArrowEntity(World worldIn, double x, double y, double z) {
        super(Registration.LIGHT_ARROW_ENTITY.get(), x, y, z, worldIn);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();

        if (!level.isClientSide() && !this.inGround || !level.isClientSide() && !this.isInWaterOrRain() || !level.isClientSide() && !this.isInWater()) {
            this.level.addParticle(ParticleTypes.COMPOSTER, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
            this.level.addParticle(ParticleTypes.COMPOSTER, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        living.hurt(DamageSource.MAGIC, ItemLightArrow.arrowDamage);
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

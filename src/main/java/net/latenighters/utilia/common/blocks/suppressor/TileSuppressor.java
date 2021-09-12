package net.latenighters.utilia.common.blocks.suppressor;

import net.latenighters.utilia.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.annotation.Nonnull;

public class TileSuppressor extends TileEntity {
    protected static float range = 5.0f;
    public static TileEntityType<TileSuppressor> tileSuppressorType;

    public TileSuppressor() {
        super(tileSuppressorType);
    }

    public static void onEntityJoinWorld (EntityJoinWorldEvent event) {
//        if (false && !event.getWorld().isClientSide && (event.getEntity() instanceof ItemEntity || event.getEntity() instanceof ExperienceOrbEntity)) { //works, but hangs on world load
//            Vector3d pos = event.getEntity().position();
//            AxisAlignedBB aabb = AxisAlignedBB.unitCubeFromLowerCorner(pos);
//            aabb = aabb.inflate(range);
//            boolean blockInAABB = BlockPos.betweenClosedStream(aabb).anyMatch(blockPos -> {
//                return event.getWorld().getBlockState(blockPos).getBlock() instanceof BlockSuppressor;
//            });
//
//            if (blockInAABB) {
//                event.setCanceled(true);
//            }
//        }
    }

    @Override
    public TileEntity getTileEntity() {
        return this;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }

    @Override
    public void deserializeNBT(BlockState state, CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {

    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getBlockPos()).inflate(1.0);
    }

    @Override
    public void requestModelDataUpdate() {

    }

//    @Nonnull
//    @Override
//    public IModelData getModelData() {
//        return null;
//    }

//
//    @Override
//    public void tick() {
////        if (!this.hasLevel()) return;
////        World world = this.getLevel();
////        if (world.isClientSide) return;
////        ServerWorld serverWorld = (ServerWorld) world;
////        world.setBlockAndUpdate(this.worldPosition, Blocks.DIAMOND_BLOCK.defaultBlockState());
//    }


}

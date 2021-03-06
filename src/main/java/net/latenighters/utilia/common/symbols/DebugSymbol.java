package net.latenighters.utilia.common.symbols;

import com.google.common.collect.Lists;
import net.latenighters.utilia.common.symbols.backend.*;
import net.latenighters.utilia.common.symbols.categories.SymbolCategory;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class DebugSymbol extends Symbol {
    public DebugSymbol() {
        super("symbol_debug", SymbolTextures.DEBUG, SymbolCategory.DEFAULT);
    }

    @Override
    protected void registerFunctions() {

        this.functions.add(new IFunctional() {

            @Override
            public String getName() {
                return "Debug_Function";
            }

            @Override
            public List<HashableTuple<String,DataType>> getRequiredInputs() {
                return new ArrayList<HashableTuple<String,DataType>>();
            }

            @Override
            public Object executeInWorld(IFunctionalObject object, Chunk chunk, List<HashableTuple<String, Object>> args) {
                DrawnSymbol symbol = (DrawnSymbol)object;

                symbol.applyServerTorque(1,chunk);
                if(symbol.getTicksAlive()%20==0)
                {
                    World world = chunk.getLevel();

                    ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
                    CompoundNBT tag = new CompoundNBT();
                    CompoundNBT tag2 = new CompoundNBT();
                    List<Integer> list = Lists.<Integer>newArrayList();

                    list.add(DyeColor.GREEN.getFireworkColor());
                    list.add(DyeColor.BLUE.getFireworkColor());
                    int[] aint1 = new int[list.size()];

                    for (int l2 = 0; l2 < aint1.length; ++l2)
                    {
                        aint1[l2] = (Integer) list.get(l2);
                    }

                    tag2.putIntArray("Colors",aint1);
                    tag2.putBoolean("Flicker",true);
                    tag.put("Explosions",tag2);

                    firework.setTag(tag);
                    FireworkRocketEntity rocket = new FireworkRocketEntity(world, symbol.getDrawnOn().getX(),
                            symbol.getDrawnOn().getY(), symbol.getDrawnOn().getZ(), firework);
                    world.addFreshEntity(rocket);
                }

                return null;
            }

            @Override
            public DataType getOutputType() {
                return null;
            }

            @Override
            public List<HashableTuple<String, DataType>> getTriggers() {
                return new ArrayList<HashableTuple<String,DataType>>();
            }
        });
    }
}

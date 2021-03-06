package net.latenighters.utilia.common.symbols;


import net.latenighters.utilia.common.symbols.backend.*;
import net.latenighters.utilia.common.symbols.categories.SymbolCategory;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class RedstoneSymbol extends Symbol {

    public RedstoneSymbol() {
        super("symbol_redstone", SymbolTextures.REDSTONE, SymbolCategory.DEFAULT);
    }

    @Override
    protected void registerFunctions() {

        HashableTuple<String,DataType> Input = new HashableTuple<>("Set High", DataType.BOOLEAN);
        List<HashableTuple<String, DataType>> requiredInputs = new ArrayList<>();
        requiredInputs.add(Input);

        functions.add(new IFunctional() {

            public final BooleanProperty POWER = BlockStateProperties.POWERED;


            @Override
            public String getName() {
                return "redstone_tick";
            }

            @Override
            public List<HashableTuple<String, DataType>> getRequiredInputs() {
                return requiredInputs;
            }

            @Override
            public Object executeInWorld(IFunctionalObject object, Chunk chunk, List<HashableTuple<String, Object>> args) {

                if(chunk.getLevel().isClientSide()) return null;

                Boolean Enabled = null;

                //TODO: implement an args helper or something
                for(HashableTuple<String, Object> arg : args)
                {
                    switch(arg.getA()) {
                        case "Set High":
                            Enabled = (Boolean) arg.getB();
                            break;
                        default:
                    }
                }

                DrawnSymbol symbol = (DrawnSymbol)object;

                if(symbol.getTicksAlive()%20!=0) return null;
                int redstoneLevel =  chunk.getLevel().getSignal(symbol.getDrawnOn(), symbol.getBlockFace());
                symbol.applyServerTorque(8*redstoneLevel, chunk);

                return null;
            }

            @Override
            public DataType getOutputType() {
                return null;
            }

            @Override
            public List<HashableTuple<String, DataType>> getTriggers() {
                return new ArrayList<>();
            }
        });

        outputs.add(new IFunctional() {
            @Override
            public String getName() {
                return "Redstone On";
            }

            @Override
            public List<HashableTuple<String, DataType>> getRequiredInputs() {
                return new ArrayList<>();
            }

            @Override
            public Object executeInWorld(IFunctionalObject object, Chunk chunk, List<HashableTuple<String, Object>> args) {

                DrawnSymbol symbol = (DrawnSymbol)object;
                int redstoneLevel =  chunk.getLevel().getSignal(symbol.getDrawnOn(), symbol.getBlockFace());
                Boolean retval = redstoneLevel > 0;
                return retval;
            }

            @Override
            public DataType getOutputType() {
                return DataType.BOOLEAN;
            }

            @Override
            public List<HashableTuple<String, DataType>> getTriggers() {
                return new ArrayList<>();
            }
        });

    }
}

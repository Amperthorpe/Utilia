package net.latenighters.utilia.common.symbols.categories;

import net.latenighters.utilia.common.symbols.Symbols;
import net.latenighters.utilia.common.symbols.backend.Symbol;

import static net.latenighters.utilia.Utilia.MODID;

public class SymbolCategory {

    public static SymbolCategory DEFAULT = new SymbolCategory(Symbols.DEBUG, MODID+".symbol.category.default");

    private Symbol displaySymbol;
    private final String unlocalizedName;

    public static void generateCategories()
    {
        //TODO figure out why static initialization isn't working
        DEFAULT.setDisplaySymbol(Symbols.EXPULSION);
    }

    public SymbolCategory(Symbol displaySymbol, String unlocalizedName) {
        this.displaySymbol = displaySymbol;
        this.unlocalizedName = unlocalizedName;
    }

    public Symbol getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(Symbol displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }
}

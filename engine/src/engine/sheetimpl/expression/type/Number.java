package engine.sheetimpl.expression.type;

import engine.api.EffectiveValue;
import engine.api.Expression;
import engine.api.SheetReadActions;
import engine.sheetimpl.cellimpl.EffectiveValueImpl;
import engine.sheetimpl.utils.CellType;

public class Number implements Expression {
    private final double num;

    public Number(double num) {
        this.num = num;
    }

    @Override
    public EffectiveValue evaluate(SheetReadActions spreadsheet) {

        return new EffectiveValueImpl(CellType.NUMERIC, num);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}
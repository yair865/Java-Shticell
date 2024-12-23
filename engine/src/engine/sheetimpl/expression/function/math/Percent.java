package engine.sheetimpl.expression.function.math;

import dto.dtoPackage.effectivevalue.EffectiveValue;
import engine.sheetimpl.expression.Expression;
import engine.sheetimpl.api.SheetReadActions;
import dto.dtoPackage.effectivevalue.EffectiveValueImpl;
import engine.sheetimpl.expression.type.BinaryExpression;
import dto.dtoPackage.CellType;

public class Percent extends BinaryExpression {

    public Percent(Expression part, Expression whole) {
        super(part, whole);
    }

    @Override
    public EffectiveValue evaluate(Expression left, Expression right, SheetReadActions spreadsheet) {
        EffectiveValue partEffectiveValue = left.evaluate(spreadsheet);
        EffectiveValue wholeEffectiveValue = right.evaluate(spreadsheet);

        Double partValue = partEffectiveValue.extractValueWithExpectation(Double.class);
        Double wholeValue = wholeEffectiveValue.extractValueWithExpectation(Double.class);

        if (partValue == null || wholeValue == null) {
            return new EffectiveValueImpl(CellType.ERROR, Double.NaN);
        }

        double percentValue = (partValue * wholeValue) / 100.0;

        return new EffectiveValueImpl(CellType.NUMERIC, percentValue);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}

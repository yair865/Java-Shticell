package engine.sheetimpl.expression.function.string;

import dto.dtoPackage.effectivevalue.EffectiveValue;
import engine.sheetimpl.expression.Expression;
import engine.sheetimpl.api.SheetReadActions;
import dto.dtoPackage.effectivevalue.EffectiveValueImpl;
import engine.sheetimpl.expression.type.BinaryExpression;
import dto.dtoPackage.CellType;

public class Concat extends BinaryExpression {

    public Concat(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public EffectiveValue evaluate(Expression left, Expression right, SheetReadActions spreadsheet) {
        EffectiveValue leftEffectiveValue = left.evaluate(spreadsheet);
        EffectiveValue rightEffectiveValue = right.evaluate(spreadsheet);

        String leftString = leftEffectiveValue.extractValueWithExpectation(String.class);
        String rightString = rightEffectiveValue.extractValueWithExpectation(String.class);

        if (leftString == null || rightString == null) {
            return new EffectiveValueImpl(CellType.ERROR, "!UNDEFINED!");
        }

        String result = leftString + rightString;

        return new EffectiveValueImpl(CellType.STRING, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }
}

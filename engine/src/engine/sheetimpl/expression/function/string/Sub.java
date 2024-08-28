package engine.sheetimpl.expression.function.string;

import engine.api.EffectiveValue;
import engine.api.Expression;
import engine.api.SheetReadActions;
import engine.sheetimpl.cellimpl.EffectiveValueImpl;
import engine.sheetimpl.expression.type.TrinaryExpression;
import engine.sheetimpl.utils.CellType;

public class Sub extends TrinaryExpression {

    public Sub(Expression sourceString, Expression startIndex, Expression endIndex) {
        super(sourceString, startIndex, endIndex);
    }

    @Override
    public EffectiveValue evaluate(Expression sourceString, Expression startIndex, Expression endIndex, SheetReadActions spreadsheet) {
        // Evaluate expressions to get EffectiveValues
        EffectiveValue sourceStringValue = sourceString.evaluate(spreadsheet);
        EffectiveValue startIndexValue = startIndex.evaluate(spreadsheet);
        EffectiveValue endIndexValue = endIndex.evaluate(spreadsheet);

        // Extract the expected types
        String source = sourceStringValue.extractValueWithExpectation(String.class);
        Double start = startIndexValue.extractValueWithExpectation(Double.class);
        Double end = endIndexValue.extractValueWithExpectation(Double.class);

        // Validate extracted values
        if (source == null || start == null || end == null) {
            return new EffectiveValueImpl(CellType.ERROR, "!UNDEFINED!");
        }

        // Ensure start and end are actually integers
        if (start % 1 != 0 || end % 1 != 0) {
            throw new IllegalArgumentException("Start and end indices must be integers. "
                    + "Received values: startIndex = " + start + ", endIndex = " + end + ".");
        }

        int startIndexInt = start.intValue();
        int endIndexInt = end.intValue();

        // Additional validation for index bounds
        if (startIndexInt < 0 || endIndexInt > source.length() || startIndexInt > endIndexInt) {
            return new EffectiveValueImpl(CellType.ERROR, "!UNDEFINED!");
        }

        // Perform the substring operation
        String result = source.substring(startIndexInt, endIndexInt);

        return new EffectiveValueImpl(CellType.STRING, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }
}


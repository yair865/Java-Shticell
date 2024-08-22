package sheetimpl.expression.type;

import api.EffectiveValue;
import api.Expression;
import dtoPackage.SpreadsheetDTO;


public abstract class BinaryExpression implements Expression {

    private final Expression expression1;
    private final Expression expression2;

    public BinaryExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public EffectiveValue evaluate(SpreadsheetDTO spreadsheetDTO) {
        return evaluate(expression1, expression2, spreadsheetDTO);
    }

    public abstract EffectiveValue evaluate(Expression left, Expression right, SpreadsheetDTO spreadsheetDTO);
}



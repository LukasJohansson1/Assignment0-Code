package prop.assignment0;

// ExpressionNode is a node in the parse tree for an expression

public class ExpressionNode implements INode {
	private TermNode term;
	private Lexeme operator;       // null when there is no tail.
	private ExpressionNode expr;   // null when there is no tail.
 
	public ExpressionNode(TermNode term, Lexeme operator, ExpressionNode expr) {
		this.term = term;
		this.operator = operator;
		this.expr = expr;
	}
 
	@Override
	public Object evaluate(Object[] args) throws Exception {
		double left = (Double) term.evaluate(args);
		if (operator == null)
			return left;
		double right = (Double) expr.evaluate(args);
		if (operator.token() == Token.ADD_OP)
			return left + right;
		return left - right;
	}
 
	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Helper.line(builder, tabs, "ExpressionNode");
		term.buildString(builder, tabs + 1);
		if (operator != null) {
			Helper.line(builder, tabs + 1, operator.toString());
			expr.buildString(builder, tabs + 1);
		}
	}
}
 
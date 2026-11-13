package prop.assignment0;

// FactorNode is a node in the parse tree for a factor

public class FactorNode implements INode {
	private Lexeme intLit;          // Set when the factor is an integer.
	private Lexeme leftParen;       // Set when the factor is '(' expr ')'.
	private ExpressionNode expr;
	private Lexeme rightParen;
 
	public FactorNode(Lexeme intLit) {
		this.intLit = intLit;
	}
 
	public FactorNode(Lexeme leftParen, ExpressionNode expr, Lexeme rightParen) {
		this.leftParen = leftParen;
		this.expr = expr;
		this.rightParen = rightParen;
	}
 
	@Override
	public Object evaluate(Object[] args) throws Exception {
		if (intLit != null)
			return intLit.value();
		return expr.evaluate(args);
	}
 
	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Helper.line(builder, tabs, "FactorNode");
		if (intLit != null) {
			Helper.line(builder, tabs + 1, intLit.toString());
		}
		else {
			Helper.line(builder, tabs + 1, leftParen.toString());
			expr.buildString(builder, tabs + 1);
			Helper.line(builder, tabs + 1, rightParen.toString());
		}
	}
}
 
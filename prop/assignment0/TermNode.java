package prop.assignment0;

// TermNode is a node in the parse tree for a term, which is a factor optionally followed by a multiplication or division operator and another term.

public class TermNode implements INode {
	private FactorNode factor;
	private Lexeme operator; // null when there is no tail.
	private TermNode term;   // null when there is no tail.
 
	public TermNode(FactorNode factor, Lexeme operator, TermNode term) {
		this.factor = factor;
		this.operator = operator;
		this.term = term;
	}
 
	@Override
	public Object evaluate(Object[] args) throws Exception {
		double left = (Double) factor.evaluate(args);
		if (operator == null)
			return left;
		double right = (Double) term.evaluate(args);
		if (operator.token() == Token.MULT_OP)
			return left * right;
		return left / right;
	}
 
	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Helper.line(builder, tabs, "TermNode");
		factor.buildString(builder, tabs + 1);
		if (operator != null) {
			Helper.line(builder, tabs + 1, operator.toString());
			term.buildString(builder, tabs + 1);
		}
	}
}
 
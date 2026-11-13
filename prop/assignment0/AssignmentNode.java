package prop.assignment0;

// AssignmentNode is a node in the parse tree for an assignment statement 

public class AssignmentNode implements INode {
	private Lexeme id;
	private Lexeme assignOp;
	private ExpressionNode expr;
	private Lexeme semicolon;
 
	public AssignmentNode(Lexeme id, Lexeme assignOp, ExpressionNode expr, Lexeme semicolon) {
		this.id = id;
		this.assignOp = assignOp;
		this.expr = expr;
		this.semicolon = semicolon;
	}
 
	@Override
	public Object evaluate(Object[] args) throws Exception {
		Object value = expr.evaluate(args);
		return id.value() + " = " + value + "\n";
	}
 
	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Helper.line(builder, tabs, "AssignmentNode");
		Helper.line(builder, tabs + 1, id.toString());
		Helper.line(builder, tabs + 1, assignOp.toString());
		expr.buildString(builder, tabs + 1);
		Helper.line(builder, tabs + 1, semicolon.toString());
	}
}

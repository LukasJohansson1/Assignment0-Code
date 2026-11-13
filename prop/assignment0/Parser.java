package prop.assignment0;


import java.io.IOException;
 
// Parser reads a stream of tokens and produces a parse tree.

// Implements IParser and has one method per grammar rule so it mirrors the EBNF directly
// Builds required nodes Assign, Expression, Term, Factor, all implement INode and stores Lexeme objects
// Reports errors with ParserException,  and check that nothing is left after semicolon
// parse tree output for program1.txt is identical to parsetree1.txt

//evaluator
// Is distrubuted over then nodes in an object oriented way, gives the correct a = 1.8

public class Parser implements IParser {
	private ITokenizer tokenizer = null;
 
	public void open(String fileName) throws IOException, TokenizerException {
		tokenizer = new Tokenizer();
		tokenizer.open(fileName);
	}
 
	public INode parse() throws IOException, TokenizerException, ParserException {
		if (tokenizer == null)
			throw new IOException("No open file.");
		AssignmentNode root = parseAssignment();
		if (tokenizer.current().token() != Token.EOF)
			throw new ParserException("Unexpected token after assignment: " + tokenizer.current());
		return root;
	}
 
	public void close() throws IOException {
		if (tokenizer != null)
			tokenizer.close();
	}
 
	// assign = id , '=' , expr , ';' ;
	private AssignmentNode parseAssignment() throws IOException, TokenizerException, ParserException {
		Lexeme id = expect(Token.IDENT);
		Lexeme assignOp = expect(Token.ASSIGN_OP);
		ExpressionNode expr = parseExpression();
		Lexeme semicolon = expect(Token.SEMICOLON);
		return new AssignmentNode(id, assignOp, expr, semicolon);
	}
 
	// expr = term , [ ( '+' | '-' ) , expr ] ;
	private ExpressionNode parseExpression() throws IOException, TokenizerException, ParserException {
		TermNode term = parseTerm();
		Token t = tokenizer.current().token();
		if (t == Token.ADD_OP || t == Token.SUB_OP) {
			Lexeme operator = tokenizer.current();
			tokenizer.moveNext();
			return new ExpressionNode(term, operator, parseExpression());
		}
		return new ExpressionNode(term, null, null);
	}
 
	// term = factor , [ ( '*' | '/' ) , term ] ;
	private TermNode parseTerm() throws IOException, TokenizerException, ParserException {
		FactorNode factor = parseFactor();
		Token t = tokenizer.current().token();
		if (t == Token.MULT_OP || t == Token.DIV_OP) {
			Lexeme operator = tokenizer.current();
			tokenizer.moveNext();
			return new TermNode(factor, operator, parseTerm());
		}
		return new TermNode(factor, null, null);
	}
 
	// factor = int | '(' , expr , ')' ;
	private FactorNode parseFactor() throws IOException, TokenizerException, ParserException {
		Token t = tokenizer.current().token();
		if (t == Token.INT_LIT) {
			return new FactorNode(expect(Token.INT_LIT));
		}
		if (t == Token.LEFT_PAREN) {
			Lexeme left = expect(Token.LEFT_PAREN);
			ExpressionNode expr = parseExpression();
			Lexeme right = expect(Token.RIGHT_PAREN);
			return new FactorNode(left, expr, right);
		}
		throw new ParserException("Expected integer or '(' but found " + tokenizer.current());
	}
 
	// Returns the current lexeme if it has the expected token and advances; otherwise throws.
	private Lexeme expect(Token expected) throws IOException, TokenizerException, ParserException {
		Lexeme lexeme = tokenizer.current();
		if (lexeme.token() != expected)
			throw new ParserException("Expected " + expected + " but found " + lexeme);
		tokenizer.moveNext();
		return lexeme;
	}
}
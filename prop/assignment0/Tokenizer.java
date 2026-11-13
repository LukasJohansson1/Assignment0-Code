package prop.assignment0;

import java.io.IOException;

// Tokenizer reads a stream of characters and produces a stream of tokens. 

// It implements ITokenizer and gets its characters from Scanner.
// It recognizes everything grammar 1 needs, integers (0..9)+, identifiers (a..z)+ and the operators, paranetheses, = and ; 
// Skips whitespace, signals end of input with EOF token, throws TokenizerException for illegal characters
// Small methods, works, well structured

public class Tokenizer implements ITokenizer {
	private IScanner scanner = null;
	private Lexeme current = null;
 
	public void open(String fileName) throws IOException, TokenizerException {
		scanner = new Scanner();
		scanner.open(fileName);
		scanner.moveNext(); // Load the first character.
		moveNext();         // Load the first token.
	}
 
	public Lexeme current() {
		return current;
	}
 
	public void moveNext() throws IOException, TokenizerException {
		if (scanner == null)
			throw new IOException("No open file.");
 
		// Skip white space.
		while (scanner.current() != Scanner.EOF && Character.isWhitespace(scanner.current()))
			scanner.moveNext();
 
		char c = scanner.current();
 
		if (c == Scanner.EOF) {
			current = new Lexeme("EOF", Token.EOF);
		}
		else if (c >= '0' && c <= '9') {
			current = readInteger();
		}
		else if (c >= 'a' && c <= 'z') {
			current = readIdentifier();
		}
		else {
			current = readSymbol(c);
			scanner.moveNext(); // A symbol is exactly one character.
		}
	}
 
	public void close() throws IOException {
		if (scanner != null)
			scanner.close();
	}
 
	// Reads (0..9)+ and stores the value as a Double.
	private Lexeme readInteger() throws IOException {
		StringBuilder text = new StringBuilder();
		while (scanner.current() >= '0' && scanner.current() <= '9') {
			text.append(scanner.current());
			scanner.moveNext();
		}
		return new Lexeme(Double.valueOf(text.toString()), Token.INT_LIT);
	}
 
	// Reads (a..z)+.
	private Lexeme readIdentifier() throws IOException {
		StringBuilder text = new StringBuilder();
		while (scanner.current() >= 'a' && scanner.current() <= 'z') {
			text.append(scanner.current());
			scanner.moveNext();
		}
		return new Lexeme(text.toString(), Token.IDENT);
	}
 
	// Maps a single-character symbol to its token.
	private Lexeme readSymbol(char c) throws TokenizerException {
		String s = String.valueOf(c);
		switch (c) {
			case '+': return new Lexeme(s, Token.ADD_OP);
			case '-': return new Lexeme(s, Token.SUB_OP);
			case '*': return new Lexeme(s, Token.MULT_OP);
			case '/': return new Lexeme(s, Token.DIV_OP);
			case '=': return new Lexeme(s, Token.ASSIGN_OP);
			case '(': return new Lexeme(s, Token.LEFT_PAREN);
			case ')': return new Lexeme(s, Token.RIGHT_PAREN);
			case ';': return new Lexeme(s, Token.SEMICOLON);
			case '{': return new Lexeme(s, Token.LEFT_CURLY);
			case '}': return new Lexeme(s, Token.RIGHT_CURLY);
			default:
				throw new TokenizerException("Unknown character '" + c + "'.");
		}
	}
}

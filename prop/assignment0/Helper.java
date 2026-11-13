package prop.assignment0;

// Helper provides a utility method for building a string representation of a parse tree.

final class Helper {
	private Helper() {
	}
 
	// Appends one line indented with the given number of tabs.
	static void line(StringBuilder builder, int tabs, String text) {
		for (int i = 0; i < tabs; i++)
			builder.append('\t');
		builder.append(text).append('\n');
	}
}
 
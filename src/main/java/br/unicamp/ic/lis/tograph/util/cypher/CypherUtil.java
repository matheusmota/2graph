package br.unicamp.ic.lis.tograph.util.cypher;

public class CypherUtil {

	public CypherUtil() {
	}

	public static String escapeStringForCypher(String string) {

		String scapedString = string;
		scapedString = scapedString.replace("'", "\\'");
		scapedString = scapedString.replace("\"", "\\\"");

		return scapedString;
	}

}

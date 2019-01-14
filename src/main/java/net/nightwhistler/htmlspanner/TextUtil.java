package net.nightwhistler.htmlspanner;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

	private static Pattern SPECIAL_CHAR_WHITESPACE = Pattern
			.compile("(&[a-z]*;|&#x?([a-f]|[A-F]|[0-9])*;|[\\s\n]+)");

	private static Pattern SPECIAL_CHAR_NO_WHITESPACE = Pattern
			.compile("(&[a-z]*;|&#x?([a-f]|[A-F]|[0-9])*;)");

	private static Map<String, String> REPLACEMENTS = new HashMap<String, String>();

	static {

		REPLACEMENTS.put("&nbsp;", "\u00A0");
		REPLACEMENTS.put("&amp;", "&");
		REPLACEMENTS.put("&quot;", "\"");
		REPLACEMENTS.put("&cent;", "¢");
		REPLACEMENTS.put("&lt;", "<");
		REPLACEMENTS.put("&gt;", ">");
		REPLACEMENTS.put("&sect;", "§");

        REPLACEMENTS.put("&ldquo;", "“");
        REPLACEMENTS.put("&rdquo;", "”");
        REPLACEMENTS.put("&lsquo;", "‘");
        REPLACEMENTS.put("&rsquo;", "’");

		REPLACEMENTS.put("&ndash;", "\u2013");
		REPLACEMENTS.put("&mdash;", "\u2014");
		REPLACEMENTS.put("&horbar;", "\u2015");

		REPLACEMENTS.put("&apos;", "'");
		REPLACEMENTS.put("&iexcl;", "¡");
		REPLACEMENTS.put("&pound;", "£");
		REPLACEMENTS.put("&curren;", "¤");
		REPLACEMENTS.put("&yen;", "¥");
		REPLACEMENTS.put("&brvbar;", "¦");
		REPLACEMENTS.put("&uml;", "¨");
		REPLACEMENTS.put("&copy;", "©");
		REPLACEMENTS.put("&ordf;", "ª");
		REPLACEMENTS.put("&laquo;", "«");
		REPLACEMENTS.put("&not;", "¬");
		REPLACEMENTS.put("&reg;", "®");
		REPLACEMENTS.put("&macr;", "¯");
		REPLACEMENTS.put("&deg;", "°");
		REPLACEMENTS.put("&plusmn;", "±");
		REPLACEMENTS.put("&sup2;", "²");
		REPLACEMENTS.put("&sup3;", "³");
		REPLACEMENTS.put("&acute;", "´");
		REPLACEMENTS.put("&micro;", "µ");
		REPLACEMENTS.put("&para;", "¶");
		REPLACEMENTS.put("&middot;", "·");
		REPLACEMENTS.put("&cedil;", "¸");
		REPLACEMENTS.put("&sup1;", "¹");
		REPLACEMENTS.put("&ordm;", "º");
		REPLACEMENTS.put("&raquo;", "»");
		REPLACEMENTS.put("&frac14;", "¼");
		REPLACEMENTS.put("&frac12;", "½");
		REPLACEMENTS.put("&frac34;", "¾");
		REPLACEMENTS.put("&iquest;", "¿");
		REPLACEMENTS.put("&times;", "×");
		REPLACEMENTS.put("&divide;", "÷");
		REPLACEMENTS.put("&Agrave;", "À");
		REPLACEMENTS.put("&Aacute;", "Á");
		REPLACEMENTS.put("&Acirc;", "Â");
		REPLACEMENTS.put("&Atilde;", "Ã");
		REPLACEMENTS.put("&Auml;", "Ä");
		REPLACEMENTS.put("&Aring;", "Å");
		REPLACEMENTS.put("&AElig;", "Æ");
		REPLACEMENTS.put("&Ccedil;", "Ç");
		REPLACEMENTS.put("&Egrave;", "È");
		REPLACEMENTS.put("&Eacute;", "É");
		REPLACEMENTS.put("&Ecirc;", "Ê");
		REPLACEMENTS.put("&Euml;", "Ë");
		REPLACEMENTS.put("&Igrave;", "Ì");
		REPLACEMENTS.put("&Iacute;", "Í");
		REPLACEMENTS.put("&Icirc;", "Î");
		REPLACEMENTS.put("&Iuml;", "Ï");
		REPLACEMENTS.put("&ETH;", "Ð");
		REPLACEMENTS.put("&Ntilde;", "Ñ");
		REPLACEMENTS.put("&Ograve;", "Ò");
		REPLACEMENTS.put("&Oacute;", "Ó");
		REPLACEMENTS.put("&Ocirc;", "Ô");
		REPLACEMENTS.put("&Otilde;", "Õ");
		REPLACEMENTS.put("&Ouml;", "Ö");
		REPLACEMENTS.put("&Oslash;", "Ø");
		REPLACEMENTS.put("&Ugrave;", "Ù");
		REPLACEMENTS.put("&Uacute;", "Ú");
		REPLACEMENTS.put("&Ucirc;", "Û");
		REPLACEMENTS.put("&Uuml;", "Ü");
		REPLACEMENTS.put("&Yacute;", "Ý");
		REPLACEMENTS.put("&THORN;", "Þ");
		REPLACEMENTS.put("&szlig;", "ß");
		REPLACEMENTS.put("&agrave;", "à");
		REPLACEMENTS.put("&aacute;", "á");
		REPLACEMENTS.put("&acirc;", "â");
		REPLACEMENTS.put("&atilde;", "ã");
		REPLACEMENTS.put("&auml;", "ä");
		REPLACEMENTS.put("&aring;", "å");
		REPLACEMENTS.put("&aelig;", "æ");
		REPLACEMENTS.put("&ccedil;", "ç");
		REPLACEMENTS.put("&egrave;", "è");
		REPLACEMENTS.put("&eacute;", "é");
		REPLACEMENTS.put("&ecirc;", "ê");
		REPLACEMENTS.put("&euml;", "ë");
		REPLACEMENTS.put("&igrave;", "ì");
		REPLACEMENTS.put("&iacute;", "í");
		REPLACEMENTS.put("&icirc;", "î");
		REPLACEMENTS.put("&iuml;", "ï");
		REPLACEMENTS.put("&eth;", "ð");
		REPLACEMENTS.put("&ntilde;", "ñ");
		REPLACEMENTS.put("&ograve;", "ò");
		REPLACEMENTS.put("&oacute;", "ó");
		REPLACEMENTS.put("&ocirc;", "ô");
		REPLACEMENTS.put("&otilde;", "õ");
		REPLACEMENTS.put("&ouml;", "ö");
		REPLACEMENTS.put("&oslash;", "ø");
		REPLACEMENTS.put("&ugrave;", "ù");
		REPLACEMENTS.put("&uacute;", "ú");
		REPLACEMENTS.put("&ucirc;", "û");
		REPLACEMENTS.put("&uuml;", "ü");
		REPLACEMENTS.put("&yacute;", "ý");
		REPLACEMENTS.put("&thorn;", "þ");
		REPLACEMENTS.put("&yuml;", "ÿ");
	}

	/**
	 * Replaces all HTML entities ( &lt;, &amp; ), with their Unicode
	 * characters.
	 * 
	 * @param aText
	 *            text to replace entities in
	 * @return the text with entities replaced.
	 */
	public static String replaceHtmlEntities(String aText,
			boolean preserveFormatting) {
		StringBuffer result = new StringBuffer();

		Map<String, String> replacements = new HashMap<String, String>(
				REPLACEMENTS);
		Matcher matcher;

		if (preserveFormatting) {
			matcher = SPECIAL_CHAR_NO_WHITESPACE.matcher(aText);
		} else {
			matcher = SPECIAL_CHAR_WHITESPACE.matcher(aText);
			replacements.put("", " ");
			replacements.put("\n", " ");
		}

		while (matcher.find()) {
            try {
			    matcher.appendReplacement(result,
					getReplacement(matcher, replacements));
            } catch ( ArrayIndexOutOfBoundsException i ) {
                //Ignore, seems to be a matcher bug
            }
		}
		matcher.appendTail(result);
		return result.toString();
	}

	private static String getReplacement(Matcher aMatcher,
			Map<String, String> replacements) {

		String match = aMatcher.group(0).trim();
		String result = replacements.get(match);

		if (result != null) {
			return result;
		} else if ( match.startsWith("&#")) {
			
			Integer code;
			
			// Translate to unicode character.
			try {
				
				//Check if it's hex or normal
				if ( match.startsWith("&#x") ) {
					code = Integer.decode( "0x" + match.substring(3, match.length() -1));
				} else {				
					code = Integer.parseInt(match.substring(2,
						match.length() - 1));
				}
				
				return "" + (char) code.intValue();
			} catch (NumberFormatException nfe) {
				return "";
			}
		} else {
			return "";
		}
	}

}

package net.nightwhistler.htmlspanner.handlers;

import org.htmlcleaner.TagNode;

import android.text.SpannableStringBuilder;
import net.nightwhistler.htmlspanner.TagNodeHandler;

/**
 * Adds a specified number of newlines.
 * 
 * Used to implement p and br tags.
 * 
 * @author Alex Kuiper
 *
 */
public class NewLineHandler extends TagNodeHandler {

	int numberOfNewLines;
	
	/**
	 * Creates this handler for a specified number of newlines.
	 * 
	 * @param howMany
	 */
	public NewLineHandler(int howMany) {
		this.numberOfNewLines = howMany;
	}
	
	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {
		for ( int i=0; i < numberOfNewLines; i++ ) {
			appendNewLine(builder);
		}
	}
}

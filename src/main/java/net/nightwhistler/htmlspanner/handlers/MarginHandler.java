package net.nightwhistler.htmlspanner.handlers;

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.LeadingMarginSpan;
import net.nightwhistler.htmlspanner.TagNodeHandler;

/**
 * Applies margin-formatting, indenting text to the right.
 * 
 * @author Alex Kuiper
 *
 */
public class MarginHandler extends TagNodeHandler {

	private static int MARGIN_INDENT = 30;
	
	@Override
	public void beforeChildren(TagNode node,
			SpannableStringBuilder builder) {

		if (builder.length() > 0
				&& builder.charAt(builder.length() - 1) != '\n') {
			appendNewLine(builder);
		}
	}

	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {

		builder.setSpan(new LeadingMarginSpan.Standard(MARGIN_INDENT),
				start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		appendNewLine(builder);
		appendNewLine(builder);
	}
}

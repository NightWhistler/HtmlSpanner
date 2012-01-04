package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

/**
 * Handles Headers, by assigning a relative text-size.
 * 
 * Note that which header is handled (h1, h2, etc) is determined
 * by the tag this handler is registered for.
 * 
 * Example: 
 * 
 * spanner.registerHandler("h1", new HeaderHandler(1.5f));
 * spanner.registerHandler("h2", new HeaderHandler(1.4f));
 * 
 * @author Alex Kuiper
 *
 */
public class HeaderHandler extends TagNodeHandler {

	private float size;

	/**
	 * Creates a HeaderHandler which gives
	 * @param size
	 */
	public HeaderHandler(float size) {
		this.size = size;
	}

	@Override
	public void beforeChildren(TagNode node, SpannableStringBuilder builder) {
		if (builder.length() > 0
				&& builder.charAt(builder.length() - 1) != '\n') {
			builder.append("\n");
		}
	}

	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {

		builder.setSpan(new RelativeSizeSpan(size), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new StyleSpan(Typeface.BOLD), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		appendNewLine(builder);
		appendNewLine(builder);
	}
}


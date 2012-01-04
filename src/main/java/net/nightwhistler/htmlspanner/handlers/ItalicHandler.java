package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

/**
 * Applies italic formatting.
 * 
 * @author Alex Kuiper
 *
 */
public class ItalicHandler extends TagNodeHandler {

	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {
		builder.setSpan(new StyleSpan(Typeface.ITALIC), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
}

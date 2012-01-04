package net.nightwhistler.htmlspanner.handlers;

import org.htmlcleaner.TagNode;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import net.nightwhistler.htmlspanner.TagNodeHandler;

/**
 * Applies bold formatting.
 * 
 * @author Alex Kuiper
 *
 */
public class BoldHandler extends TagNodeHandler {

	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {
		builder.setSpan(new StyleSpan(Typeface.BOLD), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
}

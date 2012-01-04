package net.nightwhistler.htmlspanner.handlers;

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import net.nightwhistler.htmlspanner.TagNodeHandler;

/**
 * Sets monotype font.
 * 
 * @author Alex Kuiper
 *
 */
public class MonoSpaceHandler extends TagNodeHandler {

	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {

		builder.setSpan(new TypefaceSpan("monospace"), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
}

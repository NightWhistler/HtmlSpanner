package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SubscriptSpan;

/**
 * Applies subscript style.
 * 
 * @author Alex Kuiper
 *
 */
public class SubScriptHandler extends TagNodeHandler {

	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {

		builder.setSpan(new SubscriptSpan(), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
}

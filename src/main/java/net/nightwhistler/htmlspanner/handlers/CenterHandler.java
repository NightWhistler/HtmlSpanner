package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Layout.Alignment;
import android.text.style.AlignmentSpan;

/**
 * Applies centered formatting.
 * 
 * @author Alex Kuiper
 *
 */
public class CenterHandler extends TagNodeHandler {

	@Override
	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {
		builder.setSpan(new AlignmentSpan() {
			@Override
			public Alignment getAlignment() {
				return Alignment.ALIGN_CENTER;
			}
		}, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
}

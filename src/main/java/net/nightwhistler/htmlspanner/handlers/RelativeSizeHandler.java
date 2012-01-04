package net.nightwhistler.htmlspanner.handlers;

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import net.nightwhistler.htmlspanner.TagNodeHandler;

/**
 * Changes the text size for big, small, etc tags.
 * 
 * @author Alex Kuiper
 *
 */
public class RelativeSizeHandler extends TagNodeHandler {

	private float size;
	
	public RelativeSizeHandler(float size) {
		this.size = size;
	}
	
	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {

		builder.setSpan(new RelativeSizeSpan(size), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
}

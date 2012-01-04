package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;

/**
 * Applies superscript.
 * 
 * @author Alex Kuiper
 *
 */
public class SuperScriptHandler extends TagNodeHandler {
	
	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {

		builder.setSpan(new SuperscriptSpan(), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

}

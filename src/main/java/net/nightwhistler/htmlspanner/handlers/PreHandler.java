package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.ContentNode;
import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;

/**
 * Handles pre tags, setting the style to monospace
 * and preserving the formatting.
 * 
 * @author Alex Kuiper
 *
 */
public class PreHandler extends TagNodeHandler {
	
	private void getPlainText(StringBuffer buffer, Object node) {
		if (node instanceof ContentNode) {
			ContentNode contentNode = (ContentNode) node;
			buffer.append(contentNode.getContent());
		} else {
			TagNode tagNode = (TagNode) node;
			for (Object child : tagNode.getChildren()) {
				getPlainText(buffer, child);
			}
		}
	}

	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {

		StringBuffer buffer = new StringBuffer();
		getPlainText(buffer, node);

		builder.append(buffer.toString());

		builder.setSpan(new TypefaceSpan("monospace"), start,
				builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		appendNewLine(builder);
		appendNewLine(builder);
	}

	@Override
	public boolean rendersContent() {
		return true;
	}

}
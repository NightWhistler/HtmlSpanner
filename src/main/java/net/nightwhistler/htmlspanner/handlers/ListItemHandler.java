package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

import android.text.SpannableStringBuilder;

/**
 * Handles items in both numbered and unordered lists.
 * 
 * @author Alex Kuiper
 *
 */
public class ListItemHandler extends TagNodeHandler {

	private int getMyIndex(TagNode node) {

		if (node.getParent() == null) {
			return -1;
		}

		int i = 1;

		for (Object child : node.getParent().getChildren()) {
			if (child == node) {
				return i;
			}

			if (child instanceof TagNode) {
				TagNode childNode = (TagNode) child;
				if ("li".equals(childNode.getName())) {
					i++;
				}
			}
		}

		return -1;
	}

	private String getParentName(TagNode node) {
		if (node.getParent() == null) {
			return null;
		}

		return node.getParent().getName();
	}

	@Override
	public void beforeChildren(TagNode node, SpannableStringBuilder builder) {
		if ("ol".equals(getParentName(node))) {
			builder.append("" + getMyIndex(node) + ". ");
		} else if ("ul".equals(getParentName(node))) {
			// Unicode bullet character.
			builder.append("\u2022  ");
		}
	}

	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {

		if (builder.length() > 0
				&& builder.charAt(builder.length() - 1) != '\n') {
			builder.append("\n");
		}

	}
}
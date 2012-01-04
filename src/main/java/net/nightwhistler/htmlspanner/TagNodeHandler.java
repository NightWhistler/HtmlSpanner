/*
 * Copyright (C) 2011 Alex Kuiper
 * 
 * This file is part of PageTurner
 *
 * PageTurner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PageTurner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PageTurner.  If not, see <http://www.gnu.org/licenses/>.*
 */
package net.nightwhistler.htmlspanner;

import org.htmlcleaner.TagNode;

import android.text.SpannableStringBuilder;

/**
 * A TagNodeHandler handles a specific type of tag (a, img, p, etc),
 * and adds the correct spans to a SpannableStringBuilder.
 * 
 * For example: the TagNodeHandler for i (italic) tags
 * would do
 * 
 * <tt>
 * public void handleTagNode( TagNode node, SpannableStringBuilder builder, 
 * 		int start, int end ) {
 * 		builder.setSpan(new StyleSpan(Typeface.ITALIC), 
 * 			start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 * }
 * </tt>
 * 
 * @author Alex Kuiper
 *
 */
public abstract class TagNodeHandler {
	
	private HtmlSpanner spanner;
	
	/**
	 * Called by HtmlSpanner when this TagNodeHandler is registered.
	 * @param spanner
	 */
	public void setSpanner(HtmlSpanner spanner) {
		this.spanner = spanner;
	}
	
	/**
	 * Returns a reference to the HtmlSpanner.
	 * 
	 * @return the HtmlSpanner;
	 */
	protected HtmlSpanner getSpanner() {
		return spanner;
	}
	
	/**
	 * Called before the children of this node are handled, allowing for
	 * text to be inserted before the childrens' text.
	 * 
	 * Default implementation is a no-op.
	 * 
	 * @param node
	 * @param builder
	 */
	public void beforeChildren( TagNode node, SpannableStringBuilder builder ) {
		
	}
	
	/**
	 * If this TagNodeHandler takes care of rendering the content.
	 * 
	 * If true, the parser will not add the content itself.
	 * @return
	 */
	public boolean rendersContent() {
		return false;
	}

	/**
	 * Handle the given node and add spans if needed.
	 * 
	 * @param node the node to handle
	 * @param builder the current stringbuilder
	 * @param start start position of inner text of this node
	 * @param end end position of inner text of this node.
	 */
	public abstract void handleTagNode( TagNode node, SpannableStringBuilder builder, int start, int end );
	
	/**
	 * Utility method to append newlines while making sure that there
	 * are never more than 2 consecutive newlines in the text (if
	 * whitespace stripping was enabled).
	 * 
	 * @param builder
	 */
	protected void appendNewLine(SpannableStringBuilder builder) {

		int len = builder.length();

		if (this.spanner.isStripExtraWhiteSpace()) {
			// Should never have more than 2 \n characters in a row.
			if (len > 2 && builder.charAt(len - 1) == '\n'
					&& builder.charAt(len - 2) == '\n') {
				return;
			}
		}

		builder.append("\n");
	}
}

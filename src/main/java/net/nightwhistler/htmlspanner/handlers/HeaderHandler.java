/*
 * Copyright (C) 2011 Alex Kuiper <http://www.nightwhistler.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nightwhistler.htmlspanner.handlers;

import net.nightwhistler.htmlspanner.FontFamily;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.FontFamilySpan;

import org.htmlcleaner.TagNode;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

/**
 * Handles Headers, by assigning a relative text-size.
 * 
 * Note that which header is handled (h1, h2, etc) is determined
 * by the tag this handler is registered for.
 * 
 * Example: 
 * 
 * spanner.registerHandler("h1", new HeaderHandler(1.5f));
 * spanner.registerHandler("h2", new HeaderHandler(1.4f));
 * 
 * @author Alex Kuiper
 *
 */
public class HeaderHandler extends TagNodeHandler {

	private float size;
	
	/**
	 * Creates a HeaderHandler which gives
	 * @param size
	 */
	public HeaderHandler(float size) {
		this.size = size;		
	}

	@Override
	public void beforeChildren(TagNode node, SpannableStringBuilder builder) {
		if (builder.length() > 0
				&& builder.charAt(builder.length() - 1) != '\n') {
			builder.append("\n");
		}
	}

	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {

		builder.setSpan(new RelativeSizeSpan(size), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		FontFamilySpan boldSpan = new FontFamilySpan(getSpanner().getFontFamily());
		boldSpan.setBold(true);
		
		builder.setSpan(boldSpan, start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		appendNewLine(builder);
		appendNewLine(builder);
	}
}


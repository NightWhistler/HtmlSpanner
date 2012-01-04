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

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.LeadingMarginSpan;
import net.nightwhistler.htmlspanner.TagNodeHandler;

/**
 * Applies margin-formatting, indenting text to the right.
 * 
 * @author Alex Kuiper
 *
 */
public class MarginHandler extends TagNodeHandler {

	private static int MARGIN_INDENT = 30;
	
	@Override
	public void beforeChildren(TagNode node,
			SpannableStringBuilder builder) {

		if (builder.length() > 0
				&& builder.charAt(builder.length() - 1) != '\n') {
			appendNewLine(builder);
		}
	}

	public void handleTagNode(TagNode node,
			SpannableStringBuilder builder, int start, int end) {

		builder.setSpan(new LeadingMarginSpan.Standard(MARGIN_INDENT),
				start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		appendNewLine(builder);
		appendNewLine(builder);
	}
}

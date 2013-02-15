/*
 * Copyright (C) 2013 Alex Kuiper <http://www.nightwhistler.net>
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

import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.FontFamilySpan;

import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;

public class FontHandler extends TagNodeHandler {
	
	private static final String SERIF = "serif";
	private static final String SANS_SERIF = "sans-serif";

	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {
		
		String face = node.getAttributeByName("face");
		String size = node.getAttributeByName("size");
		
		FontFamilySpan originalSpan = getFontFamilySpan(builder, start, end);		
		FontFamilySpan fontSpan;		
		
		if ( SERIF.equalsIgnoreCase(face) ) {
			fontSpan = new FontFamilySpan(getSpanner().getSerifFont());
		} else if ( SANS_SERIF.equalsIgnoreCase(face)) {
			fontSpan = new FontFamilySpan(getSpanner().getSansSerifFont() );
		} else if ( originalSpan != null ) {
			fontSpan = new FontFamilySpan(originalSpan.getFontFamily());
		} else {
			fontSpan = new FontFamilySpan(getSpanner().getDefaultFont());
		}
		
		if ( originalSpan != null ) {
			fontSpan.setBold(originalSpan.isBold());
			fontSpan.setItalic(originalSpan.isItalic());
		}		
		
		builder.setSpan(fontSpan, start, end,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		if ( size != null ) {
			try {			
				float relSize = Float.parseFloat(size);
				builder.setSpan(new RelativeSizeSpan(relSize), start, end,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			} catch (NumberFormatException e) {
				//Ignore
			}
		}
		
	}
	
}

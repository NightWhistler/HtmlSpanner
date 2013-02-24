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

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

public class FontHandler extends TagNodeHandler {
	
	private static final String SERIF = "serif";
	private static final String SANS_SERIF = "sans-serif";

	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {
		
		String face = node.getAttributeByName("face");
		String size = node.getAttributeByName("size");
		String color = node.getAttributeByName("color");
		
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
				int fontSize = Integer.parseInt(size);
				builder.setSpan(new RelativeSizeSpan(translateFontSize(fontSize)), start, end,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			} catch (NumberFormatException e) {
				//Ignore
			}
		}
		
		if ( color != null ) {
			int fontColor = Color.parseColor(color);
			builder.setSpan(new ForegroundColorSpan(fontColor), start, end,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}
	
	private static float translateFontSize( int fontSize ) {
		
		switch (fontSize ) {
		case 1:
			return 0.6f;
		case 2:
			return 0.8f;
		case 3:
			return 1.0f;
		case 4:
			return 1.2f;
		case 5:
			return 1.4f;
		case 6:
			return 1.6f;
		case 7:
			return 1.8f;
		}
		
		return 1.0f;
	}
	
}

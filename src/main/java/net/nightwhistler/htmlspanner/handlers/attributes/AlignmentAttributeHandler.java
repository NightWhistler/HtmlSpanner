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
package net.nightwhistler.htmlspanner.handlers.attributes;

import net.nightwhistler.htmlspanner.handlers.WrappingHandler;
import net.nightwhistler.htmlspanner.style.Style;
import net.nightwhistler.htmlspanner.style.StyleHandler;
import org.htmlcleaner.TagNode;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.AlignNormalSpan;
import net.nightwhistler.htmlspanner.spans.AlignOppositeSpan;
import net.nightwhistler.htmlspanner.spans.CenterSpan;

/**
 * Handler for align='left|right|center' attributes.
 * 
 * @author Alex Kuiper
 *
 */
public class AlignmentAttributeHandler extends WrappingStyleHandler {
	

	public AlignmentAttributeHandler(StyleHandler wrapHandler) {
		super(wrapHandler);
	}


	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end, Style style) {
		
		String align = node.getAttributeByName("align");

		if ( "right".equalsIgnoreCase(align) ) {
		    style = style.setTextAlignment(Style.TextAlignment.RIGHT);
		} else if ( "center".equalsIgnoreCase(align) ) {
            style =  style.setTextAlignment(Style.TextAlignment.CENTER);
		} else if ( "left".equalsIgnoreCase(align) ) {
            style =  style.setTextAlignment(Style.TextAlignment.LEFT);
		}
		
		if ( getWrappedHandler() != null ) {
			getWrappedHandler().handleTagNode(node, builder, start, end, style);
		}
		
	}
	
}

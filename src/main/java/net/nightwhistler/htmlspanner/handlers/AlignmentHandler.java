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
public class AlignmentHandler extends TagNodeHandler {
	
	private TagNodeHandler wrappedHandler;
	
	public AlignmentHandler() {}
	
	public AlignmentHandler(TagNodeHandler wrapHandler) {
		this.wrappedHandler = wrapHandler;
	}
	
	@Override
	public void setSpanner(HtmlSpanner spanner) {		
		super.setSpanner(spanner);
		
		if ( this.wrappedHandler != null ) {
			this.wrappedHandler.setSpanner(spanner);
		}
	}
	

	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {
		
		String align = node.getAttributeByName("align");
		
		AlignmentSpan span = null;
		
		if ( "right".equalsIgnoreCase(align) ) {
			span = new AlignOppositeSpan();
		} else if ( "center".equalsIgnoreCase(align) ) {
			span = new CenterSpan();
		} else if ( "left".equalsIgnoreCase(align) ) {
			span = new AlignNormalSpan();
		}
		
		if ( span != null ) {
			builder.setSpan(span, start, end,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		if ( wrappedHandler != null ) {
			wrappedHandler.handleTagNode(node, builder, start, end);
		}
		
	}
	
}

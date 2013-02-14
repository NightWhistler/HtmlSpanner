package net.nightwhistler.htmlspanner.handlers;

import org.htmlcleaner.TagNode;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import net.nightwhistler.htmlspanner.FontFamily;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.FontFamilySpan;

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

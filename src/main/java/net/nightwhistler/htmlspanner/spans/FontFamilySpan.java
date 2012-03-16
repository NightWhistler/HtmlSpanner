package net.nightwhistler.htmlspanner.spans;

import net.nightwhistler.htmlspanner.FontFamily;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class FontFamilySpan extends TypefaceSpan {
	
	private final FontFamily family;
	
	private boolean bold;
	private boolean italic;	

	public FontFamilySpan(FontFamily type) {
		super(type.getName());
		this.family = type;
	}
	
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	
	public void setItalic(boolean italic) {
		this.italic = italic;
	}	

	@Override
	public void updateDrawState(TextPaint ds) {
		applyCustomTypeFace(ds, this.family);
	}

	@Override
	public void updateMeasureState(TextPaint paint) {
		applyCustomTypeFace(paint, this.family);
	}

	private void applyCustomTypeFace(Paint paint, FontFamily tf) {
		
		paint.setAntiAlias(true);
		
		if (bold) {
			if ( tf.isFakeBold() ) {
				paint.setFakeBoldText(true);
			} else {
				paint.setTypeface(tf.getBoldTypeface());
			}
		} 
		
		if ( italic ) {
			if ( tf.isFakeItalic() ) {
				paint.setTextSkewX(-0.25f);
			} else {
				paint.setTypeface(tf.getItalicTypeface());
			}
		}
		
		if ( bold && italic && tf.getBoldItalicTypeface() != null ) {
			paint.setTypeface(tf.getBoldItalicTypeface());
		}		
	}
}


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

package net.nightwhistler.htmlspanner.spans;

import net.nightwhistler.htmlspanner.FontFamily;
import android.graphics.Paint;
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
			if (tf.isFakeBold()) {
				paint.setFakeBoldText(true);
			} else {
				paint.setTypeface(tf.getBoldTypeface());
			}
		}

		if (italic) {
			if (tf.isFakeItalic()) {
				paint.setTextSkewX(-0.25f);
			} else {
				paint.setTypeface(tf.getItalicTypeface());
			}
		}

		if (bold && italic && tf.getBoldItalicTypeface() != null) {
			paint.setTypeface(tf.getBoldItalicTypeface());
		}
	}
}

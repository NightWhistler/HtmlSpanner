package net.nightwhistler.htmlspanner.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

import net.nightwhistler.htmlspanner.ViewUtil;


public class HrSpan extends ReplacementSpan {

    @Override
    public int getSize(final Paint paint, final CharSequence text, final int start, final int end, final Paint.FontMetricsInt fm) {
        return 0;
    }

    @Override
    public void draw(final Canvas canvas, final CharSequence text, final int start, final int end, final float x, final int top, final int y, final int bottom, final Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ViewUtil.dpToPixel(2));

        int middle = (top + bottom) / 2;
        // Draw a line across the middle of the canvas
        canvas.drawLine(0, middle, canvas.getWidth(), middle, paint);
    }
}

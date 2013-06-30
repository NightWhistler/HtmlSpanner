package net.nightwhistler.htmlspanner.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BorderSpan implements LineBackgroundSpan {

    private int start;
    private int end;

    public BorderSpan( int start, int end ) {
        this.start = start;
        this.end = end;

    }


    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right,
                               int top, int baseline, int bottom,
                               CharSequence text, int start, int end,
                               int lnum) {

        int originalColor = p.getColor();

        p.setStyle(Paint.Style.STROKE);

        if ( start <= this.start ) {
            Log.d("BorderSpan", "Drawing first line");
            c.drawLine(left, top, right, top, p);
        } else if ( end >= this.end ) {
            Log.d("BorderSpan", "Drawing last line");
            c.drawLine(left, bottom, right, bottom, p);
        }

        c.drawLine(left,top,left,bottom, p);
        c.drawLine(right,top,right,bottom, p);


        p.setColor(originalColor);
    }


}


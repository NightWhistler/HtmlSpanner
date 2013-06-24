package net.nightwhistler.htmlspanner.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.style.DynamicDrawableSpan;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BorderSpan extends DynamicDrawableSpan {

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        Rect rect = new Rect();

        //Get the text bounds into the Rect
        paint.getTextBounds(text.toString(), start, end, rect);

        //TODO: Use the rect to draw a border
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);    //To change body of overridden methods use File | Settings | File Templates.

        canvas.drawText(text, start,end,x,y,paint);
    }

    @Override
    public Drawable getDrawable() {
        Drawable drawable = new ShapeDrawable();
        drawable.setBounds(0, 0, 20, 20 );

        return drawable;
    }
}


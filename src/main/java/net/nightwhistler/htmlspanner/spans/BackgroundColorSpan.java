package net.nightwhistler.htmlspanner.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackgroundColorSpan implements LineBackgroundSpan {


    private int color;


    public BackgroundColorSpan(int color) {
        this.color = color;
    }


    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right,
                               int top, int baseline, int bottom,
                               CharSequence text, int start, int end,
                               int lnum) {

        int originalColor = p.getColor();

        p.setColor(this.color);
        p.setStyle(Paint.Style.FILL);

        c.drawRect(left,top,right,bottom,p);

        p.setColor(originalColor);
    }


}


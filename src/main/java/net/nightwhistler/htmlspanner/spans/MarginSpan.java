package net.nightwhistler.htmlspanner.spans;

import android.graphics.Paint;
import android.text.style.LineHeightSpan;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/26/13
 * Time: 7:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class MarginSpan implements LineHeightSpan {

    private final float factor;

    public MarginSpan(float margin) {
        //A margin of 1em means a factor of 2
        this.factor = margin;
    }

    @Override
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v,
                             Paint.FontMetricsInt fm) {

        Log.d("MarginSpan", "Got metrics: " + fm.top + "," + fm.ascent + ", " + fm.bottom + ", " + fm.descent);

        int height = Math.abs( fm.descent - fm.ascent );

        height = (int) (height * factor);

        fm.descent += height;

         /*
        fm.top -= height;
        fm.ascent -= height;
        */
    }
}


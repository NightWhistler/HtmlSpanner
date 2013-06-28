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
        this.factor = margin;
    }

    @Override
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v,
                             Paint.FontMetricsInt fm) {

        int height = Math.abs( fm.descent - fm.ascent );
       // Log.d("MarginSpan", "Current height: " + height + " for text " + text.subSequence(start, end));

       height = (int) (height * factor);

       fm.descent = fm.ascent + height;
    //   fm.bottom += height;

      //  Log.d("MarginSpan", "Extra height: " + height + ", descent is " + fm.descent + " for text " + text.subSequence(start, end));
    }
}


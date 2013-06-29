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

    private final Float factor;
    private final Integer absolute;

    public MarginSpan(Float margin) {
        this.factor = margin;
        this.absolute = null;
    }

    public MarginSpan(Integer value ) {
        this.absolute = value;
        this.factor = null;
    }

    @Override
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v,
                             Paint.FontMetricsInt fm) {

        int height = Math.abs( fm.descent - fm.ascent );

        if ( factor != null ) {
            height = (int) (height * factor);
        } else if ( absolute != null ) {
            height = absolute;
        }

        fm.descent = fm.ascent + height;

    }
}


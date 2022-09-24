package net.nightwhistler.htmlspanner;

import android.content.res.Resources;

public class ViewUtil {

    public static int dpToPixel(final float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pixelToDp(final float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}

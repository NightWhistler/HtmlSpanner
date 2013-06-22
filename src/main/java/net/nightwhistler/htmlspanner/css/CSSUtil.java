package net.nightwhistler.htmlspanner.css;

import android.graphics.Color;
import android.util.Log;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.style.Style;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/22/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class CSSUtil {


    public static Style mapToStyle(HtmlSpanner spanner, Style style, String key, String value) {

        if ( "color".equals(key)) {
            try {
                return style.setColor(Color.parseColor(value));
            } catch ( IllegalArgumentException ia ) {
                Log.e("CSSUtil", "Can't parse colour definition: " + value);
                return style;
            }
        }

        if ( "align".equals(key) || "text-align".equals(key)) {
            try {
                return style.setTextAlignment(Style.TextAlignment.valueOf(value.toUpperCase()));
            } catch ( IllegalArgumentException i ) {
                Log.e("CSSUtil", "Can't parse alignment: " + value);
                return style;
            }
        }

        if ( "font-weight".equals(key)) {
            try {
                return style.setFontWeight(Style.FontWeight.valueOf(value.toUpperCase()));
            } catch ( IllegalArgumentException i ) {
                Log.e("CSSUtil", "Can't parse font-weight: " + value);
                return style;
            }
        }

        if ( "font-style".equals(key)) {
            try {
                return style.setFontStyle(Style.FontStyle.valueOf(value.toUpperCase()));
            }
            catch ( IllegalArgumentException i ) {
                Log.e("CSSUtil", "Can't parse font-style: " + value);
                return style;
            }
        }

        if ( "font-family".equals(key)) {
            return style.setFontFamily( spanner.getFont(value) );
        }

        if ( "font-size".equals(key)) {
            try {
                float fontSize = HtmlSpanner.translateFontSize(value);
                Log.d("CSSUtil", "Setting font-size: " + fontSize );
                //return style.setFontSize(fontSize);
                Style newStyle = style.setFontSize(fontSize);
                return newStyle;
            } catch ( NumberFormatException nfe ) {
                Log.e("CSSUtil", "Can't parse font-size: " + value );
                return style;
            }
        }

        Log.d("CSSUtil", "Don't understand CSS property '" + key + "'. Ignoring it.");

        return  style;

    }
}

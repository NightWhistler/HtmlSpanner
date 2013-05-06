package net.nightwhistler.htmlspanner.handlers.attributes;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.util.Log;
import com.osbcp.cssparser.CSSParser;
import com.osbcp.cssparser.Rule;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.handlers.WrappingHandler;
import net.nightwhistler.htmlspanner.style.Style;
import net.nightwhistler.htmlspanner.style.StyleHandler;
import org.htmlcleaner.TagNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handler which parses style attributes and modifies the style accordingly.
 */
public class StyleAttributeHandler extends WrappingStyleHandler  {

    public StyleAttributeHandler(StyleHandler wrapHandler) {
        super(wrapHandler);
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, Style useStyle,
                              SpanStack spanStack) {

        String styleAttr = node.getAttributeByName("style");

        if ( styleAttr != null ) {
            super.handleTagNode(node, builder, start, end,
                    parseStyleFromAttribute(useStyle, styleAttr),
                    spanStack);
        } else {
            super.handleTagNode(node, builder, start, end, useStyle, spanStack);
        }

    }

    private Style parseStyleFromAttribute(Style baseStyle, String attribute) {
        Style style = baseStyle;

        String[] pairs = attribute.split(";");
        for ( String pair: pairs ) {

            String[] keyVal = pair.split(":");

            if ( keyVal.length != 2) {
                Log.e("StyleAttributeHandler", "Could not parse attribute: " + attribute );
                return baseStyle;
            }

            String key =  keyVal[0].toLowerCase().trim();
            String value = keyVal[1].toLowerCase().trim();

            try {
                style = handleKeyValuePair(style, key, value );
            } catch (IllegalArgumentException ie) {
                Log.e("StyleAttributeHandler", "Unsupported value " + value + " for property " + key  );
            }
        }

        return style;
    }

    private Style handleKeyValuePair(Style style, String key, String value) {

        if ( "color".equals(key)) {
            return style.setColor(Color.parseColor(value));
        }
        if ( "align".equals(key) || "text-align".equals(key)) {
            return style.setTextAlignment(Style.TextAlignment.valueOf(value.toUpperCase()));
        }

        if ( "font-weight".equals(key)) {
            return style.setFontWeight(Style.FontWeight.valueOf(value.toUpperCase()));
        }

        if ( "font-style".equals(key)) {
            return style.setFontStyle(Style.FontStyle.valueOf(value.toUpperCase()));
        }

        if ( "font-family".equals(key)) {
            return style.setFontFamily( getSpanner().getFont(value));
        }

        if ( "font-size".equals(key)) {
            return style.setFontSize(HtmlSpanner.translateFontSize(value));
        }


        return  style;

    }



}

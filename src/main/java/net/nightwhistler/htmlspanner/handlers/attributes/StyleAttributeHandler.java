package net.nightwhistler.htmlspanner.handlers.attributes;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import com.osbcp.cssparser.CSSParser;
import com.osbcp.cssparser.Rule;
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

    private static Style parseStyleFromAttribute(Style baseStyle, String attribute) {
        Style style = baseStyle;

        Map<String, String> mapping = toMap(attribute);

        if ( mapping.containsKey("color") ) {
            style = style.setColor(Color.parseColor(mapping.get("color")));
        }

        return  style;
    }


    private static Map<String, String> toMap( String attribute ) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = attribute.split(";");
        for ( String pair: pairs ) {

            String[] keyVal = pair.split(":");

            if ( keyVal.length != 2) {
                throw new IllegalArgumentException("Could not parse attribute: " + attribute );
            }

            result.put( keyVal[0].toLowerCase(), keyVal[1].toLowerCase() );
        }

        return result;
    }
}

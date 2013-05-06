package net.nightwhistler.htmlspanner.handlers.attributes;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import com.osbcp.cssparser.CSSParser;
import com.osbcp.cssparser.Rule;
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
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, Style useStyle) {

        Style style = useStyle;

        String styleAttr = node.getAttributeByName("style");

        if ( styleAttr != null ) {
            Map<String, String> mapping = toMap(styleAttr);

            if ( mapping.containsKey("color") ) {
                style = style.setColor(Color.parseColor(mapping.get("color")));
            }
        }

        getWrappedHandler().handleTagNode(node, builder, start, end, style);
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

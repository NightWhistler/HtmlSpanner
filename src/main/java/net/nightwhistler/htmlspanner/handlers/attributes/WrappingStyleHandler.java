package net.nightwhistler.htmlspanner.handlers.attributes;

import android.text.SpannableStringBuilder;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.style.Style;
import net.nightwhistler.htmlspanner.style.StyleHandler;
import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 5/6/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class WrappingStyleHandler extends StyleHandler {

    private StyleHandler wrappedHandler;

    public WrappingStyleHandler(StyleHandler wrappedHandler) {
        super(new Style());
        this.wrappedHandler = wrappedHandler;
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, Style useStyle,
        SpanStack spanStack ) {
        if ( wrappedHandler != null ) {
            wrappedHandler.handleTagNode(node, builder, start, end, useStyle, spanStack);
        }
    }

    public StyleHandler getWrappedHandler() {
        return this.wrappedHandler;
    }

    @Override
    public void setSpanner(HtmlSpanner spanner) {
        super.setSpanner(spanner);

        if ( this.getWrappedHandler() != null ) {
            this.getWrappedHandler().setSpanner(spanner);
        }
    }

}

package net.nightwhistler.htmlspanner.style;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.AlignNormalSpan;
import net.nightwhistler.htmlspanner.spans.AlignOppositeSpan;
import net.nightwhistler.htmlspanner.spans.CenterSpan;
import net.nightwhistler.htmlspanner.spans.FontFamilySpan;
import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 5/6/13
 * Time: 8:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class StyleHandler extends TagNodeHandler {

    private Style style;

    public StyleHandler() {
        this.style = new Style();
    }

    public StyleHandler(Style style) {
        this.style = style;
    }

    public final void handleTagNode(TagNode node, SpannableStringBuilder builder,
                              int start, int end, SpanStack spanStack) {
          handleTagNode(node, builder, start, end, this.style, spanStack);
    }

    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, Style useStyle, SpanStack stack ) {
        stack.pushSpan(new StyleCallback(getSpanner().getDefaultFont(), useStyle, start, end ));
    }

}

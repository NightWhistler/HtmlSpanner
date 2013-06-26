package net.nightwhistler.htmlspanner.handlers;

import android.text.SpannableStringBuilder;
import android.util.Log;
import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.BorderSpan;
import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BorderHandler extends TagNodeHandler {

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, SpanStack spanStack) {
        Log.d("BorderHandler", "Adding BorderSpan");
        spanStack.pushSpan(new BorderSpan(), start, end);
    }


}

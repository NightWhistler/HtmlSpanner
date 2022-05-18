package net.nightwhistler.htmlspanner.handlers;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.HrSpan;

import org.htmlcleaner.TagNode;

public class HorizontalRuleHandler extends TagNodeHandler {

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, SpanStack spanStack) {
        appendNewLine(builder);
        int hrStart = builder.length();
        builder.append("\uFFFC"); // gets spanned with the horizontal rule
        int hrEnd = builder.length();
        appendNewLine(builder);

        builder.setSpan(new HrSpan(), hrStart, hrEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}

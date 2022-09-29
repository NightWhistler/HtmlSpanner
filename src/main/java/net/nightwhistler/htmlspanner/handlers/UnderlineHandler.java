package net.nightwhistler.htmlspanner.handlers;

import android.text.SpannableStringBuilder;
import android.text.style.UnderlineSpan;

import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

public class UnderlineHandler extends TagNodeHandler {

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, SpanStack spanStack) {
        spanStack.pushSpan(new UnderlineSpan(), start, end);
    }
}

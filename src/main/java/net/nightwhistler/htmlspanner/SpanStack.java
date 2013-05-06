package net.nightwhistler.htmlspanner;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 5/6/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpanStack {

   private Stack<SpanCallback> spanItemStack = new Stack<SpanCallback>();

    public void pushSpan( final Object span, final int start, final int end ) {

        SpanCallback callback = new SpanCallback() {
            @Override
            public void applySpan(SpannableStringBuilder builder) {
                builder.setSpan(span, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        };

        spanItemStack.push(callback);
    }

    public void pushSpan( SpanCallback callback ) {
        spanItemStack.push(callback);
    }

    public void applySpans( SpannableStringBuilder builder ) {
        while ( ! spanItemStack.isEmpty() ) {
            spanItemStack.pop().applySpan(builder);
        }
    }



}

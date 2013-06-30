package net.nightwhistler.htmlspanner.style;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.*;
import android.util.Log;
import net.nightwhistler.htmlspanner.FontFamily;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.SpanCallback;
import net.nightwhistler.htmlspanner.spans.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 5/6/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class StyleCallback implements SpanCallback {

    private int start;
    private int end;

    private FontFamily defaultFont;
    private Style useStyle;

    public StyleCallback( FontFamily defaultFont, Style style, int start, int end ) {
        this.defaultFont = defaultFont;
        this.useStyle = style;
        this.start = start;
        this.end = end;
    }

    @Override
    public void applySpan(SpannableStringBuilder builder) {

        if ( useStyle.getFontFamily() != null || useStyle.getFontStyle() != null || useStyle.getFontWeight() != null ) {

            FontFamilySpan originalSpan = getFontFamilySpan(builder, start, end);
            FontFamilySpan newSpan;

            if ( useStyle.getFontFamily() == null && originalSpan == null ) {
                newSpan = new FontFamilySpan(this.defaultFont);
            } else if ( useStyle.getFontFamily() != null ) {
                newSpan = new FontFamilySpan(  useStyle.getFontFamily() );
            } else {
                newSpan = new FontFamilySpan(originalSpan.getFontFamily());
            }

            if ( useStyle.getFontWeight() != null ) {
                newSpan.setBold( useStyle.getFontWeight() == Style.FontWeight.BOLD );
            } else if ( originalSpan != null ) {
                newSpan.setBold( originalSpan.isBold() );
            }

            if ( useStyle.getFontStyle() != null ) {
                newSpan.setItalic( useStyle.getFontStyle() == Style.FontStyle.ITALIC );
            } else if ( originalSpan != null ) {
                newSpan.setItalic( originalSpan.isItalic() );
            }

            //Log.d("StyleCallback", "Applying FontFamilySpan from " + start + " to " + end + " on text " + builder.subSequence(start, end));
            //Log.d("StyleCallback", "FontFamilySpan: " + newSpan );

            builder.setSpan(newSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if ( useStyle.getBackgroundColor() != null ) {
            //Log.d("StyleCallback", "Applying BackgroundColorSpan with color " + useStyle.getBackgroundColor() + " from " + start + " to " + end + " on text " + builder.subSequence(start, end));
            builder.setSpan(new BackgroundColorSpan(useStyle.getBackgroundColor()), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if ( useStyle.getFontSize() != null ) {

            StyleValue styleValue = useStyle.getFontSize();

            if ( styleValue.getUnit() == StyleValue.Unit.PX ) {
                // Log.d("StyleCallback", "Applying AbsoluteSizeSpan with size " + useStyle.getAbsoluteFontSize() + " from " + start + " to " + end + " on text " + builder.subSequence(start, end));
                builder.setSpan(new AbsoluteSizeSpan(styleValue.getIntValue()), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                //Log.d("StyleCallback", "Applying RelativeSizeSpan with size " + useStyle.getRelativeFontSize() + " from " + start + " to " + end + " on text " + builder.subSequence(start, end));
                builder.setSpan(new RelativeSizeSpan(styleValue.getFloatValue()), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        if ( useStyle.getColor() != null ) {
            //Log.d("StyleCallback", "Applying ForegroundColorSpan from " + start + " to " + end + " on text " + builder.subSequence(start, end) );
            builder.setSpan(new ForegroundColorSpan(useStyle.getColor()), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if ( useStyle.getTextAlignment() != null ) {

            AlignmentSpan alignSpan = null;

            switch ( useStyle.getTextAlignment()  ) {
                case LEFT:
                    alignSpan = new AlignNormalSpan();
                    break;
                case CENTER:
                    alignSpan = new CenterSpan();
                    break;
                case RIGHT:
                    alignSpan = new AlignOppositeSpan();
                    break;
            }

            //Log.d("StyleCallback", "Applying AlignmentSpan from " + start + " to " + end + " on text " + builder.subSequence(start, end) );
            builder.setSpan(alignSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }


        final int baseIndent = 10; //FIXME: this should be a dynamic value


        if ( useStyle.getTextIndent() != null ) {

            StyleValue styleValue = useStyle.getTextIndent();

            if ( styleValue.getUnit() == StyleValue.Unit.PX ) {
                builder.setSpan(new LeadingMarginSpan.Standard(styleValue.getIntValue(), 0), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else {
                builder.setSpan(new LeadingMarginSpan.Standard( (int) (baseIndent * styleValue.getFloatValue()), 0), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }

        /* We ignore negative horizontal margins, since that would cause the text to be rendered off-screen */
        if ( useStyle.getMarginLeft() != null ) {
            StyleValue styleValue = useStyle.getMarginLeft();

            if ( styleValue.getUnit() == StyleValue.Unit.PX ) {
                if ( styleValue.getIntValue() > 0 ) {
                    builder.setSpan(new LeadingMarginSpan.Standard(styleValue.getIntValue() ), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            } else if ( styleValue.getFloatValue() > 0f ) {
                builder.setSpan(new LeadingMarginSpan.Standard( (int) (baseIndent * styleValue.getFloatValue())), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

    }

    /**
     * Returns the current FontFamilySpan in use on the given subsection of the builder.
     *
     * If no FontFamily has been set yet, spanner.getDefaultFont() is returned.
     *
     * @param builder the text to check
     * @param start start of the section
     * @param end end of the section
     * @return a FontFamily object
     */
    private FontFamilySpan getFontFamilySpan( SpannableStringBuilder builder, int start, int end ) {

        FontFamilySpan[] spans = builder.getSpans(start, end, FontFamilySpan.class);

        if ( spans != null && spans.length > 0 ) {
            return spans[spans.length-1];
        }

        return null;
    }

}

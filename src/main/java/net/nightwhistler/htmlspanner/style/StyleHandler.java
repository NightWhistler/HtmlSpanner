package net.nightwhistler.htmlspanner.style;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
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

    public StyleHandler(Style style) {
        this.style = style;
    }

    public void handleTagNode(TagNode node, SpannableStringBuilder builder,
                              int start, int end) {
          handleTagNode(node, builder, start, end, this.style);
    }

    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, Style useStyle ) {


        if ( useStyle.getFontFamily() != null || useStyle.getFontStyle() != null || useStyle.getFontWeight() != null ) {

            FontFamilySpan originalSpan = getFontFamilySpan(builder, start, end);
            FontFamilySpan newSpan;

            if ( useStyle.getFontFamily() == null && originalSpan == null ) {
                newSpan = new FontFamilySpan(getSpanner().getDefaultFont());
            } else if ( useStyle.getFontFamily() != null ) {
                newSpan = new FontFamilySpan(  useStyle.getFontFamily() );
            } else {
                newSpan = new FontFamilySpan(originalSpan.getFontFamily());
            }

            if ( style.getFontWeight() != null ) {
                newSpan.setBold( useStyle.getFontWeight() == Style.FontWeight.BOLD );
            } else if ( originalSpan != null ) {
                newSpan.setBold( originalSpan.isBold() );
            }

            if ( useStyle.getFontStyle() != null ) {
                newSpan.setItalic( useStyle.getFontStyle() == Style.FontStyle.ITALIC );
            } else if ( originalSpan != null ) {
                newSpan.setItalic( originalSpan.isItalic() );
            }

            Log.d("StyleHandler", "Applying FontFamilySpan from " + start + " to " + end );
            builder.setSpan(newSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if ( useStyle.getFontSize() != null ) {
            builder.setSpan(new RelativeSizeSpan(useStyle.getFontSize()), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if ( useStyle.getColor() != null ) {
            Log.d("StyleHandler", "Applying ForegroundColorSpan from " + start + " to " + end );
            builder.setSpan(new ForegroundColorSpan(useStyle.getColor()), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if ( useStyle.getTextAlignment() != null ) {

            AlignmentSpan alignSpan = null;

            switch ( style.getTextAlignment()  ) {
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

            Log.d("StyleHandler", "Applying AlignMentSpan from " + start + " to " + end );
            builder.setSpan(alignSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
    }

}

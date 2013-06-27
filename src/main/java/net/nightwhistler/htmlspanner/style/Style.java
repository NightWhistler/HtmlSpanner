package net.nightwhistler.htmlspanner.style;

import net.nightwhistler.htmlspanner.FontFamily;

import java.lang.reflect.Field;

/**
 * CSS Style object.
 *
 * A Style is immutable: using a setter creates a new Style object with the
 * changed setings.
 */
public class Style {

    public static enum TextAlignment { LEFT, CENTER, RIGHT };
    public static enum FontWeight {  NORMAL, BOLD }
    public static enum FontStyle { NORMAL, ITALIC }
    public static enum DisplayStyle { BLOCK, INLINE };

    private final FontFamily fontFamily;
    private final TextAlignment textAlignment;
    private final Float relativeFontSize;
    private final Integer absoluteFontSize;

    private final FontWeight fontWeight;
    private final FontStyle fontStyle;

    private final Integer color;
    private final Integer backgroundColor;

    private final DisplayStyle displayStyle;

    private final Float relativeMarginBottom;

    public Style() {
        fontFamily = null;
        textAlignment = null;
        relativeFontSize = null;
        absoluteFontSize = null;
        fontWeight = null;
        fontStyle = null;
        color = null;
        backgroundColor = null;
        displayStyle = null;
        relativeMarginBottom = null;
    }

    public Style(FontFamily family, TextAlignment textAlignment, Float relativeFontSize,
                 Integer absoluteFontSize, FontWeight fontWeight, FontStyle fontStyle, Integer color,
                 Integer backgroundColor, DisplayStyle displayStyle, Float relativeMarginBottom ) {
        this.fontFamily = family;
        this.textAlignment = textAlignment;
        this.relativeFontSize = relativeFontSize;
        this.absoluteFontSize = absoluteFontSize;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.displayStyle = displayStyle;
        this.relativeMarginBottom = relativeMarginBottom;
    }

    public Style setFontFamily(FontFamily fontFamily) {
        return new Style(fontFamily, this.textAlignment, this.relativeFontSize, this.absoluteFontSize,
                this.fontWeight, this.fontStyle, this.color, this.backgroundColor, this.displayStyle,
                this.relativeMarginBottom );
    }


    public Style setTextAlignment(TextAlignment alignment) {
        return new Style(this.fontFamily, alignment, this.relativeFontSize, this.absoluteFontSize, this.fontWeight,
                this.fontStyle, this.color, this.backgroundColor, this.displayStyle, this.relativeMarginBottom );
    }

    public Style setRelativeFontSize(Float fontSize) {
        return new Style(this.fontFamily, this.textAlignment, fontSize, this.absoluteFontSize, this.fontWeight,
                this.fontStyle, this.color, this.backgroundColor, this.displayStyle, this.relativeMarginBottom );
    }

    public Style setAbsoluteFontSize(Integer absoluteFontSize) {
        return new Style(this.fontFamily, this.textAlignment, this.relativeFontSize, absoluteFontSize, this.fontWeight,
                this.fontStyle, this.color, this.backgroundColor, this.displayStyle, this.relativeMarginBottom );
    }

    public Style setFontWeight(FontWeight fontWeight) {
        return new Style(fontFamily, this.textAlignment, this.relativeFontSize, this.absoluteFontSize, fontWeight,
                this.fontStyle, this.color, this.backgroundColor, this.displayStyle, this.relativeMarginBottom);
    }

    public Style setFontStyle(FontStyle fontStyle) {
        return new Style(fontFamily, this.textAlignment, this.relativeFontSize, this.absoluteFontSize,
                this.fontWeight, fontStyle, this.color, this.backgroundColor
                , this.displayStyle, this.relativeMarginBottom);
    }

    public Style setColor(Integer color) {
        return new Style(fontFamily, this.textAlignment, this.relativeFontSize, this.absoluteFontSize,
                this.fontWeight, fontStyle, color, this.backgroundColor,
                this.displayStyle, this.relativeMarginBottom);
    }

    public Style setBackgroundColor( Integer bgColor ) {
        return new Style(fontFamily, this.textAlignment, this.relativeFontSize, this.absoluteFontSize,
                this.fontWeight, fontStyle, this.color, bgColor,
                this.displayStyle, this.relativeMarginBottom );
    }

    public Style setDisplayStyle( DisplayStyle displayStyle ) {
        return new Style(fontFamily, this.textAlignment, this.relativeFontSize, this.absoluteFontSize,
                this.fontWeight, fontStyle, this.color, this.backgroundColor,
                displayStyle, this.relativeMarginBottom );
    }

    public Style setRelativeMarginBottom( Float relativeMarginBottom ) {
        return new Style(fontFamily, this.textAlignment, this.relativeFontSize, this.absoluteFontSize,
                this.fontWeight, fontStyle, this.color, this.backgroundColor,
                this.displayStyle, relativeMarginBottom );
    }


    public Integer getBackgroundColor() {
        return this.backgroundColor;
    }

    public FontFamily getFontFamily() {
        return this.fontFamily;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public Float getRelativeFontSize() {
        return this.relativeFontSize;
    }

    public Integer getAbsoluteFontSize() {
        return this.absoluteFontSize;
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public FontStyle getFontStyle() {
        return fontStyle;
    }

    public Integer getColor() {
        return this.color;
    }

    public DisplayStyle getDisplayStyle() {
        return this.displayStyle;
    }

    public Float getRelativeMarginBottom() {
        return this.relativeMarginBottom;
    }

    public String toString() {

        StringBuilder result = new StringBuilder( "{\n" );

        if ( fontFamily != null  ) {
            result.append( "  font-family: " + fontFamily.getName() + "\n");
        }

        if ( textAlignment != null ) {
            result.append( "  text-alignment: " + textAlignment + "\n");
        }

        if ( relativeFontSize != null ) {
            result.append( "  relative-font-size: " + relativeFontSize + "\n");
        }

        if ( absoluteFontSize != null ) {
            result.append( "  absolute-font-size: " + relativeFontSize + "\n");
        }

        if ( fontWeight != null ) {
            result.append( "  font-weight: " + fontWeight + "\n" );
        }

        if ( fontStyle != null ) {
            result.append( "  font-style: " + fontStyle + "\n" );
        }

        if ( color != null ) {
            result.append("  color: " + color + "\n");
        }

        if ( backgroundColor != null ) {
            result.append("  background-color: " + backgroundColor + "\n");
        }

        if ( displayStyle != null ) {
            result.append("  display: " + displayStyle + "\n");
        }

        if ( relativeMarginBottom != null ) {
            result.append("  relative-margin-bottom: " + relativeMarginBottom + "\n" );
        }

        result.append("}\n");

        return result.toString();
    }

}

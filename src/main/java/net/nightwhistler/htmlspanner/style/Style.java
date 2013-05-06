package net.nightwhistler.htmlspanner.style;

import net.nightwhistler.htmlspanner.FontFamily;

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

    private final FontFamily fontFamily;
    private final TextAlignment textAlignment;
    private final Float fontSize;
    private final FontWeight fontWeight;
    private final FontStyle fontStyle;

    private final Integer color;

    public Style() {
        fontFamily = null;
        textAlignment = null;
        fontSize = null;
        fontWeight = null;
        fontStyle = null;
        color = null;
    }

    public Style(FontFamily family, TextAlignment textAlignment, Float fontSize, FontWeight fontWeight, FontStyle fontStyle, Integer color ) {
        this.fontFamily = family;
        this.textAlignment = textAlignment;
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.color = color;
    }

    public Style setFontFamily(FontFamily fontFamily) {
        return new Style(fontFamily, this.textAlignment, this.fontSize, this.fontWeight, this.fontStyle, this.color );
    }


    public Style setTextAlignment(TextAlignment alignment) {
        return new Style(this.fontFamily, alignment, this.fontSize, this.fontWeight, this.fontStyle, this.color);
    }

    public Style setFontSize(Float fontSize) {
        return new Style(fontFamily, this.textAlignment, fontSize, this.fontWeight, this.fontStyle, this.color);
    }

    public Style setFontWeight(FontWeight fontWeight) {
        return new Style(fontFamily, this.textAlignment, this.fontSize, fontWeight, this.fontStyle, this.color);
    }

    public Style setFontStyle(FontStyle fontStyle) {
        return new Style(fontFamily, this.textAlignment, this.fontSize, this.fontWeight, fontStyle, this.color);
    }

    public Style setColor(Integer color) {
        return new Style(fontFamily, this.textAlignment, this.fontSize, this.fontWeight, fontStyle, color);
    }

    public FontFamily getFontFamily() {
        return this.fontFamily;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public Float getFontSize() {
        return fontSize;
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

}

package net.nightwhistler.htmlspanner.css;

import android.graphics.Color;
import android.util.Log;
import com.osbcp.cssparser.PropertyValue;
import com.osbcp.cssparser.Rule;
import com.osbcp.cssparser.Selector;
import net.nightwhistler.htmlspanner.FontFamily;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.style.Style;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Compiler for CSS Rules.
 *
 * The compiler takes the raw parsed form (a Rule) of a CSS rule
 * and transforms it into an executable CompiledRule where all
 * the parsing of values has already been done.
 *
 *
 */
public class CSSCompiler {

    public static interface StyleUpdater {
        Style updateStyle( Style style, HtmlSpanner spanner );
    }

    public static interface TagNodeMatcher {
        boolean matches( TagNode tagNode );
    }

    public static CompiledRule compile( Rule rule, HtmlSpanner spanner ) {

        Log.d("CSSCompiler", "Compiling rule " + rule );

        List<List<TagNodeMatcher>> matchers = new ArrayList<List<TagNodeMatcher>>();
        List<StyleUpdater> styleUpdaters = new ArrayList<StyleUpdater>();

        for ( Selector selector: rule.getSelectors() ) {
            List<CSSCompiler.TagNodeMatcher> selMatchers = CSSCompiler.createMatchersFromSelector(selector);
            matchers.add( selMatchers );
        }

        for ( PropertyValue propertyValue: rule.getPropertyValues() ) {
            CSSCompiler.StyleUpdater updater = CSSCompiler.getStyleUpdater(propertyValue.getProperty(),
                    propertyValue.getValue());

            if ( updater != null ) {
                styleUpdaters.add( updater );
            }
        }

        String asText = rule.toString();

        return new CompiledRule(spanner, matchers, styleUpdaters, asText );
    }

    public static Integer parseCSSColor( String colorString ) {

        //Check for CSS short-hand notation: #0fc -> #00ffcc
        if ( colorString.length() == 4 && colorString.startsWith("#") ) {
            StringBuilder builder = new StringBuilder("#");
            for ( int i =1; i < colorString.length(); i++ ) {
                //Duplicate each char
                builder.append( colorString.charAt(i) );
                builder.append( colorString.charAt(i) );
            }

            colorString = builder.toString();
        }

        return Color.parseColor(colorString);
    }

    public static List<TagNodeMatcher> createMatchersFromSelector( Selector selector ) {
        List<TagNodeMatcher> matchers = new ArrayList<TagNodeMatcher>();

        String selectorString = selector.toString();

        String[] parts = selectorString.split("\\s");

        //Create a reversed matcher list
        for ( int i=parts.length -1; i >= 0; i-- ) {
            matchers.add( createMatcherFromPart(parts[i]));
        }

        return matchers;
    }

    private static TagNodeMatcher createMatcherFromPart( String selectorPart ) {

        //Match by class
        if ( selectorPart.indexOf('.') != -1 ) {
            return new ClassMatcher(selectorPart);
        }

        if ( selectorPart.startsWith("#") ) {
            return new IdMatcher( selectorPart );
        }

        return new TagNameMatcher(selectorPart);
    }



    private static class ClassMatcher implements TagNodeMatcher {

        private String tagName;
        private String className;

        private ClassMatcher( String selectorString ) {

            String[] elements = selectorString.split("\\.");

            if ( elements.length == 2 ) {
                tagName = elements[0];
                className = elements[1];
            }
        }

        @Override
        public boolean matches(TagNode tagNode) {

            if ( tagNode == null ) {
                return false;
            }

            //If a tag name is given it should match
            if (tagName != null && tagName.length() > 0 && ! tagName.equals(tagNode.getName() ) ) {
                return  false;
            }

            String classAttribute = tagNode.getAttributeByName("class");
            return classAttribute != null && classAttribute.equals(className);
        }
    }

    private static class TagNameMatcher implements TagNodeMatcher {
        private String tagName;

        private TagNameMatcher( String selectorString ) {
            this.tagName = selectorString.trim();
        }

        @Override
        public boolean matches(TagNode tagNode) {
            return tagNode != null && tagName.equalsIgnoreCase( tagNode.getName() );
        }
    }

    private static class IdMatcher implements TagNodeMatcher {
        private String id;

        private IdMatcher( String selectorString ) {
            id = selectorString.substring(1);
        }

        @Override
        public boolean matches(TagNode tagNode) {

            if ( tagNode == null ) {
                return false;
            }

            String idAttribute = tagNode.getAttributeByName("id");
            return idAttribute != null && idAttribute.equals( id );
        }
    }

    public static StyleUpdater getStyleUpdater( final String key, final String value) {

        if ( "color".equals(key)) {
            try {
                final Integer color = parseCSSColor(value);
                return new StyleUpdater() {
                    @Override
                    public Style updateStyle(Style style, HtmlSpanner spanner) {
                        Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                        return style.setColor(color);
                    }
                };
            } catch ( IllegalArgumentException ia ) {
                Log.e("CSSCompiler", "Can't parse colour definition: " + value);
                return null;
            }
        }

        if ( "background-color".equals(key) ) {
            try {
                final Integer color = parseCSSColor(value);
                return new StyleUpdater() {
                    @Override
                    public Style updateStyle(Style style, HtmlSpanner spanner) {
                        Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                        return style.setBackgroundColor(color);
                    }
                };
            } catch ( IllegalArgumentException ia ) {
                Log.e("CSSCompiler", "Can't parse colour definition: " + value);
                return null;
            }
        }

        if ( "align".equals(key) || "text-align".equals(key)) {
            try {
                final Style.TextAlignment alignment = Style.TextAlignment.valueOf(value.toUpperCase());
                return new StyleUpdater() {
                    @Override
                    public Style updateStyle(Style style, HtmlSpanner spanner) {
                        Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                        return style.setTextAlignment(alignment);
                    }
                };

            } catch ( IllegalArgumentException i ) {
                Log.e("CSSCompiler", "Can't parse alignment: " + value);
                return null;
            }
        }

        if ( "font-weight".equals(key)) {

            try {
                final Style.FontWeight weight = Style.FontWeight.valueOf(value.toUpperCase());

                return new StyleUpdater() {
                    @Override
                    public Style updateStyle(Style style, HtmlSpanner spanner) {
                        Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                        return style.setFontWeight(weight);
                    }
                };

            } catch ( IllegalArgumentException i ) {
                Log.e("CSSCompiler", "Can't parse font-weight: " + value);
                return null;
            }
        }

        if ( "font-style".equals(key)) {
            try {
                final Style.FontStyle fontStyle = Style.FontStyle.valueOf(value.toUpperCase());
                return new StyleUpdater() {
                    @Override
                    public Style updateStyle(Style style, HtmlSpanner spanner) {
                        Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                        return style.setFontStyle(fontStyle);
                    }
                };
            }
            catch ( IllegalArgumentException i ) {
                Log.e("CSSCompiler", "Can't parse font-style: " + value);
                return null;
            }
        }

        if ( "font-family".equals(key)) {
            return new StyleUpdater() {
                @Override
                public Style updateStyle(Style style, HtmlSpanner spanner) {
                    Log.d("CSSCompiler", "Applying style " + key + ": " + value );

                    FontFamily family = spanner.getFont( value );

                    Log.d("CSSCompiler", "Got font " + family );

                    return style.setFontFamily(family);
                }
            };

        }

        if ( "font-size".equals(key)) {

            if ( value.endsWith("px") ) {

                try {
                    final Integer fontSize = Integer.parseInt( value.substring(0, value.length() -2) );
                    return new StyleUpdater() {
                        @Override
                        public Style updateStyle(Style style, HtmlSpanner spanner) {
                            Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                            return style.setAbsoluteFontSize(fontSize);
                        }
                    };
                } catch (NumberFormatException nfe ) {
                    Log.e("CSSCompiler", "Can't parse font-size: " + value );
                    return null;
                }
            }

            if ( value.endsWith("%") ) {
                Log.d("CSSCompiler", "translating percentage " + value );
                try {
                    final int percentage = Integer.parseInt( value.substring(0, value.length() -1 ) );
                    final float relativeFontSize = percentage / 100f;

                    return new StyleUpdater() {
                        @Override
                        public Style updateStyle(Style style, HtmlSpanner spanner) {
                            Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                            return style.setRelativeFontSize(relativeFontSize);
                        }
                    };
                } catch ( NumberFormatException nfe ) {
                    Log.e("CSSCompiler", "Can't parse font-size: " + value );
                    return null;
                }
            }

            if ( value.endsWith("em") ) {
                try {
                    final Float number = Float.parseFloat(value.substring(0, value.length() - 2));
                    return new StyleUpdater() {
                        @Override
                        public Style updateStyle(Style style, HtmlSpanner spanner) {
                            Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                            return style.setRelativeFontSize(number);
                        }
                    };
                } catch ( NumberFormatException nfe ) {
                    Log.e("CSSCompiler", "Can't parse font-size: " + value );
                    return null;
                }
            }

            try {
                final Float number = translateFontSize(Integer.parseInt(value));
                return new StyleUpdater() {
                    @Override
                    public Style updateStyle(Style style, HtmlSpanner spanner) {
                        Log.d("CSSCompiler", "Applying style " + key + ": " + value );
                        return style.setRelativeFontSize(number);
                    }
                };
            } catch ( NumberFormatException nfe ) {
                Log.e("CSSCompiler", "Can't parse font-size: " + value );
                return null;
            }

        }

        Log.d("CSSCompiler", "Don't understand CSS property '" + key + "'. Ignoring it.");
        return null;
    }

    private static float translateFontSize( int fontSize ) {

        switch (fontSize ) {
            case 1:
                return 0.6f;
            case 2:
                return 0.8f;
            case 3:
                return 1.0f;
            case 4:
                return 1.2f;
            case 5:
                return 1.4f;
            case 6:
                return 1.6f;
            case 7:
                return 1.8f;
        }

        return 1.0f;
    }


}

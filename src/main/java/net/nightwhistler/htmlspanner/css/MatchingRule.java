package net.nightwhistler.htmlspanner.css;

import com.osbcp.cssparser.PropertyValue;
import com.osbcp.cssparser.Rule;
import com.osbcp.cssparser.Selector;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.style.Style;
import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/22/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class MatchingRule  {

    private Rule rule;
    private HtmlSpanner spanner;

    public MatchingRule( HtmlSpanner spanner, Rule rule ) {
        this.rule = rule;
        this.spanner = spanner;
    }

    public boolean matches( TagNode tagNode ) {

        for ( Selector selector: rule.getSelectors() ) {
            if ( matches(selector, tagNode) ) {
                return true;
            }
        }

        return false;
    }

    private boolean matches( Selector selector, TagNode tagNode ) {
        String selectorString = selector.toString();

        String[] parts = selectorString.split("\\s");
        TagNode node = tagNode;

        for ( int i= parts.length -1; i >= 0; i-- ) {

            if ( node == null || ! matchesPart(parts[i], node ) ) {
                return false;
            }

            node = node.getParent();
        }

        return true;
    }

    private boolean matchesPart( String selectorString, TagNode tagNode ) {



        //Match by class
        if ( selectorString.indexOf('.') != -1 ) {

            String[] elements = selectorString.split("\\.");

            if ( elements.length != 2 ) {
                return false;
            }

            String tagName = elements[0];
            String className = elements[1];

            String classAttribute = tagNode.getAttributeByName("class");

            //If a tag name is given it should match
            if ( tagName.length() > 0 && ! tagName.equals(tagNode.getName() ) ) {
                return  false;
            }

            return classAttribute != null && classAttribute.equals(className);
        }

        if ( selectorString.startsWith("#") ) {
            String idString = selectorString.substring(1);
            String idAttribute = tagNode.getAttributeByName("id");

            return idAttribute != null && idAttribute.equals( idString );
        }


        return selectorString.trim().equalsIgnoreCase( tagNode.getName() );
    }

    public Style applyStyle( final Style style ) {

        Style result = style;

        for ( PropertyValue prop: rule.getPropertyValues() ) {
            result = CSSUtil.mapToStyle( spanner, result, prop.getProperty(), prop.getValue() );
        }

        return result;
    }

    @Override
    public String toString() {
        return rule.toString();
    }
}

package net.nightwhistler.htmlspanner.css;

import android.util.Log;
import com.osbcp.cssparser.PropertyValue;
import com.osbcp.cssparser.Rule;
import com.osbcp.cssparser.Selector;
import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.style.Style;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Compiled matcher / updater structure based on a Rule.
 *
 * This compiles a Rule into a Matcher structure which is stateless,
 * so it can be shared and reused.
 *
 * This speeds up matching of TagNodes against CSS rules.
 */
public class MatchingRule  {

    private List<List<TagNodeMatcher>> matchers = new ArrayList<List<TagNodeMatcher>>();
    private List<CSSCompiler.StyleUpdater> styleUpdaters = new ArrayList<CSSCompiler.StyleUpdater>();

    private HtmlSpanner spanner;

    private String asText;

    private static interface TagNodeMatcher {
        boolean matches( TagNode tagNode );
    }

    private static interface StyleUpdater {
        void updateStyle( Style style );
    }

    public MatchingRule( HtmlSpanner spanner, Rule rule ) {

        this.spanner = spanner;

        for ( Selector selector: rule.getSelectors() ) {
            List<TagNodeMatcher> selMatchers = createMatchersFromSelector( selector );
            this.matchers.add( selMatchers );
        }

        for ( PropertyValue propertyValue: rule.getPropertyValues() ) {
            CSSCompiler.StyleUpdater updater = CSSCompiler.getStyleUpdater(propertyValue.getProperty(),
                    propertyValue.getValue());

            if ( updater != null ) {
                styleUpdaters.add( updater );
            }
        }

        this.asText = rule.toString();
    }

    public String toString() {
        return asText;
    }

    public Style applyStyle( final Style style ) {

        Style result = style;

       for ( CSSCompiler.StyleUpdater updater: styleUpdaters ) {
           result = updater.updateStyle(result, spanner);
       }

        return result;
    }

    private static List<TagNodeMatcher> createMatchersFromSelector( Selector selector ) {
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

    private static boolean matchesChain( List<TagNodeMatcher> matchers, TagNode tagNode ) {

        TagNode nodeToMatch = tagNode;

        for ( TagNodeMatcher matcher: matchers ) {
            if ( ! matcher.matches(nodeToMatch) ) {
                return false;
            }

            nodeToMatch = nodeToMatch.getParent();
        }

        return true;
    }

    public boolean matches( TagNode tagNode ) {

        for ( List<TagNodeMatcher> matcherList: matchers ) {
            if ( matchesChain(matcherList, tagNode)) {
                return true;
            }
        }

        return false;
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
}

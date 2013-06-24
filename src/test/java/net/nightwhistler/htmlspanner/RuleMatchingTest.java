package net.nightwhistler.htmlspanner;

import com.osbcp.cssparser.CSSParser;
import com.osbcp.cssparser.Rule;

import net.nightwhistler.htmlspanner.css.MatchingRule;
import org.htmlcleaner.TagNode;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/22/13
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleMatchingTest {

    @Test
    public void straightTagNameMatch() throws Exception {

        List<Rule> rules = CSSParser.parse( "a { color: red;}" );
        MatchingRule rule = new MatchingRule(new HtmlSpanner(), rules.get(0));

        TagNode nodeA = new TagNode( "a" );
        TagNode nodeB = new TagNode( "b" );

        assertTrue( rule.matches( nodeA ) );
        assertFalse( rule.matches( nodeB ) );
    }

    @Test
    public void tagClassMatch() throws Exception {
        List<Rule> rules = CSSParser.parse( ".red { color: red;}" );
        MatchingRule rule = new MatchingRule(new HtmlSpanner(), rules.get(0));

        TagNode nodeA = new TagNode( "a" );
        nodeA.setAttribute("class", "red");

        TagNode nodeB = new TagNode( "b" );
        nodeB.setAttribute("class", "blue");

        assertTrue( rule.matches( nodeA ) );
        assertFalse( rule.matches( nodeB ) );
    }

    @Test
    public void tagClassAndNameMatch() throws Exception {
        List<Rule> rules = CSSParser.parse( "a.red { color: red;}" );
        MatchingRule rule = new MatchingRule(new HtmlSpanner(), rules.get(0));

        TagNode nodeA = new TagNode( "a" );
        nodeA.setAttribute("class", "red");

        TagNode nodeB = new TagNode( "b" );
        nodeB.setAttribute("class", "red");

        assertTrue( rule.matches( nodeA ) );
        assertFalse( rule.matches( nodeB ) );
    }

    @Test
    public void tagMatchById() throws Exception {
        List<Rule> rules = CSSParser.parse( "#red { color: red;}" );
        MatchingRule rule = new MatchingRule(new HtmlSpanner(), rules.get(0));

        TagNode nodeA = new TagNode( "a" );
        nodeA.setAttribute("id", "red");

        TagNode nodeB = new TagNode( "b" );
        nodeB.setAttribute("class", "red");

        assertTrue( rule.matches( nodeA ) );
        assertFalse( rule.matches( nodeB ) );
    }


    @Test
    public void tagMatchMultiRule() throws Exception {
        List<Rule> rules = CSSParser.parse( "div .red { color: red;}" );
        MatchingRule rule = new MatchingRule(new HtmlSpanner(), rules.get(0));

        TagNode divNode = new TagNode("div");

        TagNode nodeA = new TagNode( "a" );
        nodeA.setAttribute("class", "red");

        divNode.addChild( nodeA );

        TagNode spanNode = new TagNode("span");

        TagNode nodeB = new TagNode( "b" );
        nodeB.setAttribute("class", "red");
        spanNode.addChild(nodeB);

        assertTrue( rule.matches( nodeA ) );
        assertFalse( rule.matches( nodeB ) );

    }

    @Test
    public void testImport() throws Exception {
        List<Rule> rules = CSSParser.parse("@import \"extrapage.css\";");


    }


}

/*
 * Copyright (C) 2011 Alex Kuiper <http://www.nightwhistler.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nightwhistler.htmlspanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import com.osbcp.cssparser.Rule;
import com.osbcp.cssparser.Selector;
import net.nightwhistler.htmlspanner.handlers.*;
import net.nightwhistler.htmlspanner.handlers.attributes.AlignmentAttributeHandler;

import net.nightwhistler.htmlspanner.handlers.attributes.StyleAttributeHandler;
import net.nightwhistler.htmlspanner.style.Style;
import net.nightwhistler.htmlspanner.style.StyledTextHandler;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

/**
 * HtmlSpanner provides an alternative to Html.fromHtml() from the Android
 * libraries.
 * 
 * In its simplest form, just call new HtmlSpanner().fromHtml() to get a similar
 * result. The real strength is in being able to register custom NodeHandlers.
 * 
 * @author work
 * 
 */
public class HtmlSpanner {

	private Map<String, TagNodeHandler> handlers;

	private boolean stripExtraWhiteSpace = false;

	private HtmlCleaner htmlCleaner;

    private FontResolver fontResolver;


	/**
	 * Creates a new HtmlSpanner using a default HtmlCleaner instance.
	 */
	public HtmlSpanner() {
		this(createHtmlCleaner(), new SystemFontResolver());
	}

	/**
	 * Creates a new HtmlSpanner using the given HtmlCleaner instance.
	 * 
	 * This allows for a custom-configured HtmlCleaner.
	 * 
	 * @param cleaner
	 */
	public HtmlSpanner(HtmlCleaner cleaner, FontResolver fontResolver) {
		this.htmlCleaner = cleaner;
		this.fontResolver = fontResolver;
        this.handlers = new HashMap<String, TagNodeHandler>();
		
		registerBuiltInHandlers();
	}

	public FontResolver getFontResolver() {
        return this.fontResolver;
    }

    public void setFontResolver( FontResolver fontResolver ) {
        this.fontResolver = fontResolver;
    }

    public FontFamily getFont( String name ) {
        return this.fontResolver.getFont(name);
    }

	/**
	 * Switch to specify whether excess whitespace should be stripped from the
	 * input.
	 * 
	 * @param stripExtraWhiteSpace
	 */
	public void setStripExtraWhiteSpace(boolean stripExtraWhiteSpace) {
		this.stripExtraWhiteSpace = stripExtraWhiteSpace;
	}

	/**
	 * Returns if whitespace is being stripped.
	 * 
	 * @return
	 */
	public boolean isStripExtraWhiteSpace() {
		return stripExtraWhiteSpace;
	}

	/**
	 * Registers a new custom TagNodeHandler.
	 * 
	 * If a TagNodeHandler was already registered for the specified tagName it
	 * will be overwritten.
	 * 
	 * @param tagName
	 * @param handler
	 */
	public void registerHandler(String tagName, TagNodeHandler handler) {
		this.handlers.put(tagName, handler);
		handler.setSpanner(this);
	}

    /**
     * Removes the handler for the given tag.
     *
     * @param tagName the tag to remove handlers for.
     */
    public void unregisterHandler(String tagName) {
        this.handlers.remove(tagName);
    }

	/**
	 * Parses the text in the given String.
	 * 
	 * @param html
	 * 
	 * @return a Spanned version of the text.
	 */
	public Spannable fromHtml(String html) {
		return fromTagNode(this.htmlCleaner.clean(html));
	}

	/**
	 * Parses the text in the given Reader.
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public Spannable fromHtml(Reader reader) throws IOException {
		return fromTagNode(this.htmlCleaner.clean(reader));
	}

	/**
	 * Parses the text in the given InputStream.
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public Spannable fromHtml(InputStream inputStream) throws IOException {
		return fromTagNode(this.htmlCleaner.clean(inputStream));
	}

	/**
	 * Gets the currently registered handler for this tag.
	 * 
	 * Used so it can be wrapped.
	 * 
	 * @param tagName
	 * @return the registed TagNodeHandler, or null if none is registered.
	 */
	public TagNodeHandler getHandlerFor(String tagName) {
		return this.handlers.get(tagName);
	}

	/**
	 * Creates spanned text from a TagNode.
	 * 
	 * @param node
	 * @return
	 */
	public Spannable fromTagNode(TagNode node) {
		SpannableStringBuilder result = new SpannableStringBuilder();
        SpanStack stack = new SpanStack();
		handleContent(result, node, stack);

        stack.applySpans(result);

		return result;
	}

	private static HtmlCleaner createHtmlCleaner() {
		HtmlCleaner result = new HtmlCleaner();
		CleanerProperties cleanerProperties = result.getProperties();

		cleanerProperties.setAdvancedXmlEscape(true);

		cleanerProperties.setOmitXmlDeclaration(true);
		cleanerProperties.setOmitDoctypeDeclaration(false);

		cleanerProperties.setTranslateSpecialEntities(true);
		cleanerProperties.setTransResCharsToNCR(true);
		cleanerProperties.setRecognizeUnicodeChars(true);

		cleanerProperties.setIgnoreQuestAndExclam(true);
		cleanerProperties.setUseEmptyElementTags(false);

		cleanerProperties.setPruneTags("script,title");

		return result;
	}

	private void handleContent(SpannableStringBuilder builder, Object node,
			 SpanStack stack) {
		if (node instanceof ContentNode) {

			ContentNode contentNode = (ContentNode) node;

			if (builder.length() > 0) {
				char lastChar = builder.charAt(builder.length() - 1);
				if (lastChar != ' ' && lastChar != '\n') {
					builder.append(' ');
				}
			}

			String text = TextUtil.replaceHtmlEntities(
					contentNode.getContent().toString(), false);

            if ( isStripExtraWhiteSpace() ) {
                //Replace unicode non-breaking space with normal space.
                text = text.replace( '\u00A0', ' ' );
            }

            text = text.trim();

			builder.append(text);

		} else if (node instanceof TagNode) {
			applySpan(builder, (TagNode) node, stack);
		}
	}

	private void applySpan(SpannableStringBuilder builder, TagNode node, SpanStack stack) {

		TagNodeHandler handler = this.handlers.get(node.getName());

		int lengthBefore = builder.length();

		if (handler != null) {
			handler.beforeChildren(node, builder);
		}

		if (handler == null || !handler.rendersContent()) {

			for (Object childNode : node.getChildren()) {
				handleContent(builder, childNode, stack);
			}
		}

		int lengthAfter = builder.length();

		if (handler != null) {
			handler.handleTagNode(node, builder, lengthBefore, lengthAfter, stack);
		}
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

    public static Style setFontSize( Style style, String fontSize ) {

        if ( fontSize.endsWith("px") ) {
            return style.setAbsoluteFontSize( Integer.parseInt( fontSize.substring(0, fontSize.length() -2) ));
        }


        if ( fontSize.endsWith("%") ) {
            Log.d("HtmlSpanner", "translating percentage " + fontSize );
            int percentage = Integer.parseInt( fontSize.substring(0, fontSize.length() -1 ) );

            return style.setRelativeFontSize(percentage / 100f);
        }

        if ( fontSize.endsWith("em") ) {
            String number = fontSize.substring(0, fontSize.length() - 2 );
            return style.setRelativeFontSize( Float.parseFloat(number) );
        }

        //TODO: parse things like em and px, larger, smaller, etc.
        return style.setRelativeFontSize( translateFontSize(Integer.parseInt(fontSize)) );
    }

    private static StyledTextHandler wrap( StyledTextHandler handler ) {
        return new StyleAttributeHandler(new AlignmentAttributeHandler(handler));
    }

	private void registerBuiltInHandlers() {

		TagNodeHandler italicHandler = new StyledTextHandler(
                new Style().setFontStyle(Style.FontStyle.ITALIC));

		registerHandler("i", italicHandler);
		registerHandler("em", italicHandler);
		registerHandler("cite", italicHandler);
		registerHandler("dfn", italicHandler);

		TagNodeHandler boldHandler = new StyledTextHandler(
                new Style().setFontWeight(Style.FontWeight.BOLD));

		registerHandler("b", boldHandler);
		registerHandler("strong", boldHandler);

		TagNodeHandler marginHandler = new MarginHandler();

		registerHandler("blockquote", marginHandler);
		registerHandler("ul", marginHandler);
		registerHandler("ol", marginHandler);

        registerHandler("code", wrap(new StyledTextHandler(
                new Style().setFontFamily(fontResolver.getMonoSpaceFont()))));

        registerHandler("style", new StyleNodeHandler() );

        //We wrap an alignment-handler to support
        //align attributes
        StyledTextHandler blockAlignment = wrap(new StyledTextHandler());

        TagNodeHandler brHandler = new NewLineHandler(1, blockAlignment);

		registerHandler("br", brHandler);

        //And add 2 newlines at the end
		TagNodeHandler pHandler = new NewLineHandler(2, blockAlignment);

		registerHandler("p", pHandler);
		registerHandler("div", pHandler);

        registerHandler("span", blockAlignment );
        registerHandler("body", blockAlignment);

		registerHandler("h1", wrap(new HeaderHandler(1.5f)));
		registerHandler("h2", wrap(new HeaderHandler(1.4f)));
		registerHandler("h3", wrap(new HeaderHandler(1.3f)));
		registerHandler("h4", wrap(new HeaderHandler(1.2f)));
		registerHandler("h5", wrap(new HeaderHandler(1.1f)));
		registerHandler("h6", wrap(new HeaderHandler(1f)));

		TagNodeHandler monSpaceHandler = new MonoSpaceHandler();

		registerHandler("tt", monSpaceHandler);

		TagNodeHandler preHandler = new PreHandler();

		registerHandler("pre", preHandler);

		TagNodeHandler bigHandler = new StyledTextHandler(new Style().setRelativeFontSize(1.25f));
		registerHandler("big", bigHandler);

		TagNodeHandler smallHandler = new StyledTextHandler(new Style().setRelativeFontSize(0.8f));
		registerHandler("small", smallHandler);

		TagNodeHandler subHandler = new SubScriptHandler();
		registerHandler("sub", subHandler);

		TagNodeHandler superHandler = new SuperScriptHandler();
		registerHandler("sup", superHandler);

		TagNodeHandler centerHandler = new StyledTextHandler(new Style().setTextAlignment(Style.TextAlignment.CENTER));
		registerHandler("center", centerHandler);

		registerHandler("li", new ListItemHandler());

		registerHandler("a", new LinkHandler());
		registerHandler("img", new ImageHandler());
		
		registerHandler("font", new FontHandler() );

        registerHandler("border", new BorderHandler() );

	}

}

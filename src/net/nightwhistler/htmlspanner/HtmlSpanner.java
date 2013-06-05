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
import java.util.Map;

import net.nightwhistler.htmlspanner.handlers.AlignmentHandler;
import net.nightwhistler.htmlspanner.handlers.BoldHandler;
import net.nightwhistler.htmlspanner.handlers.CenterHandler;
import net.nightwhistler.htmlspanner.handlers.FontHandler;
import net.nightwhistler.htmlspanner.handlers.HeaderHandler;
import net.nightwhistler.htmlspanner.handlers.ImageHandler;
import net.nightwhistler.htmlspanner.handlers.ItalicHandler;
import net.nightwhistler.htmlspanner.handlers.LinkHandler;
import net.nightwhistler.htmlspanner.handlers.ListItemHandler;
import net.nightwhistler.htmlspanner.handlers.MarginHandler;
import net.nightwhistler.htmlspanner.handlers.MonoSpaceHandler;
import net.nightwhistler.htmlspanner.handlers.NewLineHandler;
import net.nightwhistler.htmlspanner.handlers.PreHandler;
import net.nightwhistler.htmlspanner.handlers.RelativeSizeHandler;
import net.nightwhistler.htmlspanner.handlers.SubScriptHandler;
import net.nightwhistler.htmlspanner.handlers.SuperScriptHandler;

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

	private final Map<String, TagNodeHandler> handlers;

	private boolean stripExtraWhiteSpace = false;

	private final HtmlCleaner htmlCleaner;

	private FontFamily defaultFont;

	private FontFamily serifFont;
	private FontFamily sansSerifFont;

	/**
	 * Creates a new HtmlSpanner using a default HtmlCleaner instance.
	 */
	public HtmlSpanner() {
		this(createHtmlCleaner());
	}

	/**
	 * Creates a new HtmlSpanner using the given HtmlCleaner instance.
	 *
	 * This allows for a custom-configured HtmlCleaner.
	 *
	 * @param cleaner
	 */
	public HtmlSpanner(HtmlCleaner cleaner) {
		this.htmlCleaner = cleaner;
		this.handlers = new HashMap<String, TagNodeHandler>();
		this.defaultFont = new FontFamily("default", Typeface.DEFAULT);
		this.serifFont = new FontFamily("serif", Typeface.SERIF);
		this.sansSerifFont = new FontFamily("sans-serif", Typeface.SANS_SERIF);

		registerBuiltInHandlers();

	}

	public FontFamily getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(FontFamily defaultFont) {
		this.defaultFont = defaultFont;
	}

	public FontFamily getSansSerifFont() {
		return sansSerifFont;
	}

	public void setSansSerifFont(FontFamily sansSerifFont) {
		this.sansSerifFont = sansSerifFont;
	}

	public FontFamily getSerifFont() {
		return serifFont;
	}

	public void setSerifFont(FontFamily serifFont) {
		this.serifFont = serifFont;
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
		handleContent(result, node);

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

		cleanerProperties.setPruneTags("script,style,title");

		return result;
	}

    private void handleContent(SpannableStringBuilder builder, Object node) {

        if (node instanceof TagNode) {

            TagNode tagNode = (TagNode) node;

            TagNodeHandler handler = this.handlers.get(tagNode.getName());

            int lengthBefore = builder.length();

            if (handler != null) {
                handler.beforeChildren(tagNode, builder);
            }

            if (handler == null || !handler.rendersContent()) {

                for (Object childNode : tagNode.getChildren()) {
                    handleContent(builder, childNode);
                }
            }

            int lengthAfter = builder.length();

            if (handler != null) {
                handler.handleTagNode(tagNode, builder, lengthBefore, lengthAfter);
            }

        } else if (node instanceof ContentNode) {
            handleContentNode(builder, (ContentNode) node);
        }
    }


    private void handleContentNode( SpannableStringBuilder builder, ContentNode contentNode ) {

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
    }


	private void registerBuiltInHandlers() {

		TagNodeHandler italicHandler = new ItalicHandler();

		registerHandler("i", italicHandler);
		registerHandler("strong", italicHandler);
		registerHandler("cite", italicHandler);
		registerHandler("dfn", italicHandler);

		TagNodeHandler boldHandler = new BoldHandler();

		registerHandler("b", boldHandler);
		registerHandler("em", boldHandler);

		TagNodeHandler marginHandler = new MarginHandler();

		registerHandler("blockquote", marginHandler);
		registerHandler("ul", marginHandler);
		registerHandler("ol", marginHandler);

		TagNodeHandler brHandler = new NewLineHandler(1);

		registerHandler("br", brHandler);

		TagNodeHandler pHandler = new NewLineHandler(2);

		//We wrap an alignment-handler to support
		//align attributes
		registerHandler("p", new AlignmentHandler(pHandler));
		registerHandler("div", new AlignmentHandler(pHandler));

		registerHandler("h1", new HeaderHandler(1.5f));
		registerHandler("h2", new HeaderHandler(1.4f));
		registerHandler("h3", new HeaderHandler(1.3f));
		registerHandler("h4", new HeaderHandler(1.2f));
		registerHandler("h5", new HeaderHandler(1.1f));
		registerHandler("h6", new HeaderHandler(1f));

		TagNodeHandler monSpaceHandler = new MonoSpaceHandler();

		registerHandler("tt", monSpaceHandler);

		TagNodeHandler preHandler = new PreHandler();

		registerHandler("pre", preHandler);

		TagNodeHandler bigHandler = new RelativeSizeHandler(1.25f);
		registerHandler("big", bigHandler);

		TagNodeHandler smallHandler = new RelativeSizeHandler(0.8f);
		registerHandler("small", smallHandler);

		TagNodeHandler subHandler = new SubScriptHandler();
		registerHandler("sub", subHandler);

		TagNodeHandler superHandler = new SuperScriptHandler();
		registerHandler("sup", superHandler);

		TagNodeHandler centerHandler = new CenterHandler();
		registerHandler("center", centerHandler);

		registerHandler("li", new ListItemHandler());

		registerHandler("a", new LinkHandler());
		registerHandler("img", new ImageHandler());

		registerHandler("font", new FontHandler() );
	}

}

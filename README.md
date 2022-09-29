# Android HTML rendering library

### Gradle dependency:

``implementation 'com.github.NightWhistler:HtmlSpanner:0.4'``

HtmlSpanner started as the HTML rendering library for PageTurner, but looking through some questions on StackOverflow I noticed how many people were struggling with the infamous ``Html.fromHtml()`` and getting its results to display properly in TextViews.

HtmlSpanner allows you full control over how tags are rendered and gives you all the data about the location of a tag in the text. This allows proper handling of anchors, tables, numbered lists and unordered lists.

The default link implementation just opens URLs, but it can be easily overridden to support anchors.

HtmlSpanner uses HtmlCleaner to do most of the heavy lifting for parsing HTML files.

# CSS support

HtmlSpanner now also supports the most common subset of CSS: both style tags and style attributes are parsed by default, and the style of all built-in tags can be updated.

# Supported Tags

* i
* em
* cite
* dfn
* b
* u
* strong
* blockquote
* ul
* ol
* tt
* code
* style
* br
* p
* div
* h1
* h2
* h3
* h4
* h5
* h6
* pre
* big
* small
* sub
* sup
* center
* li
* a
* img
* font
* span

# Supported Style Attributes

* font-family
* text-alignment
* font-size
* font-weight
* font-style
* color
* background-color
* display
* margin-top
* margin-bottom
* margin-left
* margin-right
* text-indent
* border-style
* border-color
* border-style
* text-decoration (underline)

# Usage
1. add following in root project
  allprojects {
    maven { url 'https://jitpack.io' }
    }
  }

2. add 
## compile 'com.github.NightWhistler:HtmlSpanner:0.4'##
in app module

In its simplest form, just call ``(new HtmlSpanner()).fromHtml()`` to get similar output as Android's ``Html.fromHtml()``.

# HTMLCleaner Source
see http://htmlcleaner.sourceforge.net/index.php

The fork under https://github.com/amplafi/htmlcleaner can not be used on Android <= 2.1 as it uses java.lang.String.isEmpty


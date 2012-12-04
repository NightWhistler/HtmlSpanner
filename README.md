# Android HTML rendering library

HtmlSpanner started as the HTML rendering library for PageTurner, but looking through some questions on StackOverflow I noticed how many people were struggling with the infamous ``Html.fromHtml()`` and getting its results to display properly in TextViews.

HtmlSpanner allows you full control over how tags are rendered and gives you all the data about the location of a tag in the text. This allows proper handling of anchors, tables, numbered lists and unordered lists.

It currently renders:

* ``<pre>`` tags
* ``<i>``, ``<strong>``, ``<cite>``, ``<dfn>`` as italic text
* ``<b>`` and ``<strong>`` as bold text
* ``<blockquote>``
* ``<ul>`` and ``<ol>`` with bullets and numbering
* ``<br>`` and ``<p>`` with optional whitespace limiting
* ``<h1>``..``<h6>``
* ``<tt>``
* ``<big>``, ``<small>``
* ``<sup>`` and ``<sub>``
* ``<center>``
* ``<tables>``
* ``<img>``
* ``<a>``

The default link implementation just opens URLs, but it can be easily overridden to support anchors.

HtmlSpanner uses HtmlCleaner to do most of the heavy lifting for parsing HTML files.

# Usage
In its simplest form, just call ``(new HtmlSpanner()).fromHtml()`` to get similar output as Android's ``Html.fromHtml()``.

# HTMLCleaner Source
see http://htmlcleaner.sourceforge.net/index.php

The fork under https://github.com/amplafi/htmlcleaner can not be used on Android <= 2.1 as it uses java.lang.String.isEmpty

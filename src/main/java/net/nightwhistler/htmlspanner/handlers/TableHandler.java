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
package net.nightwhistler.htmlspanner.handlers;

import java.util.ArrayList;
import java.util.List;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.ContentNode;
import org.htmlcleaner.TagNode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.AlignmentSpan;
import android.text.style.ImageSpan;

/**
 * Handles simple HTML tables.
 * 
 * Since it renders these tables itself, it needs to know things
 * like font size and text colour to use.
 * 
 * @author Alex Kuiper
 *
 */
public class TableHandler extends TagNodeHandler {
	
	private int tableWidth = 400;	
	private Typeface typeFace = Typeface.DEFAULT;
	private float textSize = 16f;
	private int textColor = Color.BLACK;
			
	private static final int PADDING = 5;
	
	/**
	 * Sets how wide the table should be.
	 * 
	 * @param tableWidth
	 */
	public void setTableWidth(int tableWidth) {
		this.tableWidth = tableWidth;
	}	
	
	/**
	 * Sets the text colour to use.
	 * 
	 * Default is black.
	 * 
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}
	
	/**
	 * Sets the font size to use.
	 * 
	 * Default is 16f.
	 * 
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}
	
	/**
	 * Sets the TypeFace to use.
	 * 
	 * Default is Typeface.DEFAULT
	 * 
	 * @param typeFace
	 */
	public void setTypeFace(Typeface typeFace) {
		this.typeFace = typeFace;
	}
	
	@Override
	public boolean rendersContent() {
		return true;
	}
	
	private void readNode( Object node, Table table ) {
		
		//We can't handle plain content nodes within the table.
		if ( node instanceof ContentNode ) {			
			return;
		}
		
		TagNode tagNode = (TagNode) node;
		
		if ( tagNode.getName().equals("td") ) {
			Spanned result = this.getSpanner().fromTagNode( tagNode );
			table.addCell(result);
			return;
		}
		
		if ( tagNode.getName().equals("tr") ) {
			table.addRow();
		}
		
		for ( Object child: tagNode.getChildren() ) {
			readNode(child, table);
		}
		
	}
		
	private Table getTable( TagNode node ) {
	
		Table result = new Table();
		
		readNode(node, result);		
		
		return result;
	}	
	
	private TextPaint getTextPaint() {
		TextPaint textPaint = new TextPaint();
		textPaint.setColor( this.textColor );
		textPaint.setAntiAlias(true);
		textPaint.setTextSize( this.textSize );
		textPaint.setTypeface( this.typeFace );
		
		return textPaint;
	}	
	
	private int calculateRowHeight( List<Spanned> row ) {
		
		if ( row.size() == 0 ) {
			return 0;
		}
		
		TextPaint textPaint = getTextPaint();
		
		int columnWidth = tableWidth / row.size();

		int rowHeight = 0;

		for ( Spanned cell: row ) {

			StaticLayout layout = new StaticLayout(cell, textPaint,
					columnWidth - 2*PADDING,
					Alignment.ALIGN_NORMAL, 1f, 0f, true);			

			if ( layout.getHeight() > rowHeight ) {
				rowHeight = layout.getHeight();
			}
		}	
		
		return rowHeight;		
	}
	
	@Override
	public void handleTagNode(TagNode node, SpannableStringBuilder builder,
			int start, int end) {	
        		
		Table table = getTable(node);
				
		for (int i=0; i < table.getRows().size(); i++ ) {
			
			List<Spanned> row = table.getRows().get(i);
			builder.append("\uFFFC");
			
			TableRowDrawable drawable = new TableRowDrawable(row );
			drawable.setBounds( 0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() );

			builder.setSpan( new ImageSpan(drawable), start, start + 1, 
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			builder.setSpan(new AlignmentSpan() {
				@Override
				public Alignment getAlignment() {
					return Alignment.ALIGN_CENTER;
				}
			}, start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			start++;
		}		
	}
	
	/**
	 * Drawable of the table, which does the actual rendering.
	 * 
	 * @author Alex Kuiper.
	 *
	 */
	private class TableRowDrawable extends Drawable {
		
		private List<Spanned> tableRow;

		public TableRowDrawable(List<Spanned> tableRow) {
			this.tableRow = tableRow;			
		}
		
		
		@Override
		public void draw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setColor( textColor );
			paint.setStyle(Style.STROKE);
			
			int numberOfColumns = tableRow.size();
			
			if ( numberOfColumns == 0 ) {
				return;
			}
			
			int columnWidth = tableWidth / numberOfColumns;		
			int rowHeight = calculateRowHeight(tableRow);
			
			int offset = 0;
			
			for ( int i=0; i < numberOfColumns; i++ ) {
				
				offset = i * columnWidth;
				
				//The rect is open at the bottom, so there's a single line between rows.
				canvas.drawRect(offset, 0, offset + columnWidth, rowHeight, paint);
				
				StaticLayout layout = new StaticLayout(tableRow.get(i), getTextPaint(),
						(columnWidth - 2*PADDING), Alignment.ALIGN_NORMAL, 1f, 0f, true);			
				
				canvas.translate(offset + PADDING, 0);				
				layout.draw(canvas);				
				canvas.translate( -1 * PADDING, 0);
				
			}			
		}
		
		
		@Override
		public int getIntrinsicHeight() {
			return calculateRowHeight(tableRow);
		}
		
		@Override
		public int getIntrinsicWidth() {
			return tableWidth;
		}
		
		@Override
		public int getOpacity() {
			return PixelFormat.OPAQUE;
		}
		
		@Override
		public void setAlpha(int alpha) {
						
		}
		
		@Override
		public void setColorFilter(ColorFilter cf) {
					
		}
	}
	
	private class Table {
		private List<List<Spanned>> content = new ArrayList<List<Spanned>>();
		
		public void addRow() {
			content.add( new ArrayList<Spanned>() );
		}
		
		public List<Spanned> getBottomRow() {
			return content.get( content.size() -1 );
		}			
		
		public List<List<Spanned>> getRows() {
			return content;
		}
		
		public void addCell(Spanned text) {
			if ( content.isEmpty() ) {
				throw new IllegalStateException("No rows added yet");
			}
			
			getBottomRow().add( text );			
		}
	}
	
}

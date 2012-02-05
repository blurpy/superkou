
/***************************************************************************
 *   Copyright 2005-2012 by Christian Ihle                                 *
 *   kontakt@usikkert.net                                                  *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/

package net.usikkert.superkou.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.usikkert.superkou.Constants;

public class GameMenu
{
	private List<GameMenuItem> itemList;
	private int selectedItemPos, fontSize, offsetY, visibleStartItemPos, visibleStopItemPos;
	private String name;

	public GameMenu( String name, int fontSize, int offsetY )
	{
		this.name = name;
		this.fontSize = fontSize;
		this.offsetY = offsetY;

		itemList = new ArrayList<GameMenuItem>();
	}

	private void calculateVisibleItems()
	{
		visibleStartItemPos = selectedItemPos -2;
		visibleStopItemPos = selectedItemPos +2;

		if ( visibleStartItemPos < 0 )
		{
			int offset = visibleStartItemPos * -1;
			visibleStartItemPos += offset;
			visibleStopItemPos += offset;
		}

		else if ( visibleStopItemPos > itemList.size() -1 )
		{
			int offset = visibleStopItemPos - itemList.size() +1;
			visibleStartItemPos -= offset;
			visibleStopItemPos -= offset;
		}

		visibleStopItemPos++;

		if ( visibleStopItemPos > itemList.size() )
			visibleStopItemPos = itemList.size();

		if ( visibleStartItemPos < 0 )
			visibleStartItemPos = 0;
	}

	public void selectUp()
	{
		if ( selectedItemPos > 0 )
		{
			itemList.get( selectedItemPos ).setSelected( false );
			selectedItemPos--;
			itemList.get( selectedItemPos ).setSelected( true );
			calculateVisibleItems();
		}
	}

	public void selectDown()
	{
		if ( selectedItemPos < itemList.size() -1 )
		{
			itemList.get( selectedItemPos ).setSelected( false );
			selectedItemPos++;
			itemList.get( selectedItemPos ).setSelected( true );
			calculateVisibleItems();
		}
	}

	public void addGameMenuItem( GameMenuItem gmi )
	{
		itemList.add( gmi );
		calculateVisibleItems();
	}

	public void drawMenu( Graphics graphics )
	{
		Graphics2D g = (Graphics2D) graphics;

		//g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g.setFont( new Font( Constants.FONT_NAME, Font.PLAIN, fontSize ) );
		FontMetrics fm = g.getFontMetrics();

		int totWidth = 0;
		int newAscent = (int) ( fm.getAscent() * 1.2 );
		int totHeight = ( newAscent + fm.getDescent() ) * ( visibleStopItemPos - visibleStartItemPos ) -5;

		for ( int i = visibleStartItemPos; i < visibleStopItemPos; i++ )
		{
			int tmp = fm.stringWidth( itemList.get( i ).getName() );

			if ( tmp > totWidth )
				totWidth = tmp;
		}

		int startXPos = ( Constants.WINDOW_WIDTH / 2 ) - ( ( totWidth + 30 ) / 2 );
		int startYPos = Constants.WINDOW_HEIGHT / 2 - ( ( totHeight + 10 ) / 2 ) + offsetY;

		g.setColor( new Color( 240, 240, 240 ) );
		g.fillRoundRect( startXPos, startYPos, totWidth + 30, totHeight + 10, 10, 10 );
		g.setColor( new Color( 60, 120, 171 ) );
		g.drawRoundRect( startXPos, startYPos, totWidth + 30, totHeight + 10, 10, 10 );

		int widthPos = startXPos + 15;
		int heightPos = startYPos + 1 + newAscent;

		for ( int i = visibleStartItemPos; i < visibleStopItemPos; i++ )
		{
			GameMenuItem gmi = itemList.get( i );

			if ( gmi.isSelected() )
				g.setColor( Color.BLACK );
			else
				g.setColor( Color.GRAY );

			g.drawString( gmi.getName(), widthPos, heightPos );

			if ( i < visibleStopItemPos -1 )
			{
				g.setColor( new Color( 60, 120, 171 ) );
				g.drawLine( startXPos +15, heightPos + fm.getDescent() +2, totWidth + startXPos +15, heightPos + fm.getDescent() +2 );
			}

			heightPos += ( newAscent + fm.getDescent() );
		}

		if ( visibleStartItemPos > 0 )
		{
			int polXPos = startXPos + totWidth / 2;
			int polYPos = startYPos;
			Polygon polUp = new Polygon();
			polUp.addPoint( polXPos, polYPos );
			polUp.addPoint( polXPos + 30, polYPos );
			polUp.addPoint( polXPos + 15, polYPos -15 );
			g.setColor( Color.BLACK );
			g.fillPolygon( polUp );
		}

		if ( visibleStopItemPos < itemList.size() )
		{
			int polXPos = startXPos + totWidth / 2;
			int polYPos = startYPos + totHeight + 11;
			Polygon polDown = new Polygon();
			polDown.addPoint( polXPos, polYPos );
			polDown.addPoint( polXPos + 30, polYPos );
			polDown.addPoint( polXPos + 15, polYPos +15 );
			g.setColor( Color.BLACK );
			g.fillPolygon( polDown );
		}
	}

	public String getSelectedMenuItemName()
	{
		return itemList.get( selectedItemPos ).getName();
	}

	public GameMenuItem getSelectedMenuItem()
	{
		return itemList.get( selectedItemPos );
	}

	public String getName()
	{
		return name;
	}

	public List<GameMenuItem> getMenuItems()
	{
		return itemList;
	}
}

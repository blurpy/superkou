
/***************************************************************************
 *   Copyright 2005-2007 by Christian Ihle                                 *
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

import net.usikkert.superkou.Constants;

public class MessageBox
{
	public void drawMessage( Graphics graphics, String msg, int fontSize, int offsetX, int offsetY )
	{
		ArrayList<String> lines = new ArrayList<String>();
		
		while ( msg.length() > 0 )
		{
			if ( msg.contains( "\n" ) )
			{
				String substr = msg.substring( msg.indexOf( "\n" ) );
				// Need the Q thing, or else it will parse () as regex
				msg = msg.replaceFirst( "\\Q" + substr, "" );
				
				lines.add( msg );
				msg = substr.replaceFirst( "\n", "" );
			}
			
			else
			{
				lines.add( msg );
				msg = "";
			}
		}

		Graphics2D g = (Graphics2D) graphics;
		//g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		g.setFont( new Font( Constants.FONT_NAME, Font.PLAIN, fontSize ) );
		g.setColor( Color.BLACK );
		FontMetrics fm = g.getFontMetrics();
		
		int totWidth = 0;
		int newAscent = (int) ( fm.getAscent() * 1.1 );
		int totHeight = ( newAscent + fm.getDescent() ) * lines.size();
		
		for ( int i = 0; i < lines.size(); i++ )
		{
			int tmp = fm.stringWidth( lines.get( i ) );
			
			if ( tmp > totWidth )
				totWidth = tmp;
		}
		
		int startXPos = ( Constants.WINDOW_WIDTH / 2 ) - ( ( totWidth + 30 ) / 2 ) + offsetX;
		int startYPos = ( Constants.WINDOW_HEIGHT / 2 ) - ( ( totHeight + 10 ) / 2 ) + offsetY;
		
		g.setColor( new Color( 240, 240, 240 ) );
		g.fillRoundRect( startXPos, startYPos, totWidth + 30, totHeight + 10, 10, 10 );
		g.setColor( new Color( 60, 120, 171 ) );
		g.drawRoundRect( startXPos, startYPos, totWidth + 30, totHeight + 10, 10, 10 );
		
		int widthPos = startXPos + 15;
		int heightPos = startYPos + 5 + newAscent;
		
		g.setColor( Color.BLACK );
		
		for ( int i = 0; i < lines.size(); i++ )
		{
			String line = lines.get( i );
			g.drawString( line, widthPos, heightPos );
			heightPos += ( newAscent + fm.getDescent() );
		}
	}
}

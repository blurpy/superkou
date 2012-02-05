
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

package net.usikkert.superkou;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.InputStreamReader;
import javax.swing.ImageIcon;

public class Tools
{
	public static Image getImage( String url )
	{
		return new ImageIcon( Tools.class.getResource( "/" + url ) ).getImage();
	}

	public static InputStreamReader getTextStream( String url )
	{
		return new InputStreamReader( Tools.class.getResourceAsStream( "/" + url ) );
	}

	public static int pixelToTile( int pixels )
	{
		return pixels / Constants.TILE_SIZE;
	}

	public static int tileToPixel( int tiles )
	{
		return tiles * Constants.TILE_SIZE;
	}

	public static Image transformImage( Image image, double x, double y )
	{
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

		AffineTransform transform = new AffineTransform();
		transform.scale( x, y );
		transform.translate( ( x -1 ) * image.getWidth( null ) / 2, ( y -1 ) * image.getHeight( null ) / 2 );

		Image newImage = gc.createCompatibleImage( image.getWidth( null ), image.getHeight( null ), Transparency.BITMASK );

		Graphics2D g = (Graphics2D) newImage.getGraphics();
		g.drawImage( image, transform, null );
		g.dispose();

		return newImage;
	}
}

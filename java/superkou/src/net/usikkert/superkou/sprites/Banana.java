
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

package net.usikkert.superkou.sprites;

import java.awt.*;

import net.usikkert.superkou.Tools;

public class Banana extends Sprite
{
	public Banana( int xPos, int yPos )
	{
		super( xPos, yPos );
		
		Animation bananaAni = new Animation();
		
		Image image1 = Tools.getImage( "graphics/banana1.png" );
		Image image2 = Tools.getImage( "graphics/banana2.png" );
		Image image3 = Tools.getImage( "graphics/banana3.png" );
		
		bananaAni.addFrame( image1, 200 );
		bananaAni.addFrame( image2, 200 );
		bananaAni.addFrame( image3, 200 );
		bananaAni.addFrame( image2, 200 );
		
		setAnimation( bananaAni );
		getRectangle().setBounds( xPos, yPos, image1.getWidth( null ), image1.getHeight( null ) );
	}
}

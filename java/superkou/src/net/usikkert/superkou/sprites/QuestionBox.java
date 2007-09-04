
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

public class QuestionBox extends Sprite
{
	private boolean hit;
	private Sprite contents;
	private Animation newBoxAni, usedBoxAni;

	public QuestionBox( int xPos, int yPos )
	{
		super( xPos, yPos );
		
		newBoxAni = new Animation();
		usedBoxAni = new Animation();

		Image imageNewBox = Tools.getImage( "graphics/box_question.png" );
		Image imageUsedBox = Tools.getImage( "graphics/box_question_used.png" );

		newBoxAni.addFrame( imageNewBox, 20000 );
		usedBoxAni.addFrame( imageUsedBox, 20000 );
		
		setAnimation( newBoxAni );
		getRectangle().setBounds( xPos, yPos, imageNewBox.getWidth( null ), imageNewBox.getHeight( null ) );
	}

	public Sprite getContents()
	{
		return contents;
	}

	public void setContents( Sprite contents )
	{
		this.contents = contents;
		contents.setVisible( false );
	}

	public boolean isHit()
	{
		return hit;
	}

	public void setHit()
	{
		if ( !hit )
		{
			setAnimation( usedBoxAni );
			contents.setVisible( true );
			hit = true;
		}
	}
}

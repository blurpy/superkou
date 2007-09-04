
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

public class Goompa extends Enemy
{
	private Animation jumpAni, deadAni;

	public Goompa( int xPos, int yPos )
	{
		super( xPos, yPos );
		
		jumpAni = new Animation();
		deadAni = new Animation();

		Image imageStraight = Tools.getImage(  "graphics/goompa_straight.png" );
		Image imageMiddle = Tools.getImage(  "graphics/goompa_middle.png" );
		Image imageUp = Tools.getImage(  "graphics/goompa_up.png" );

		jumpAni.addFrame( imageStraight, 120 );
		jumpAni.addFrame( imageMiddle, 120 );
		jumpAni.addFrame( imageUp, 120 );

		Image imageDead = Tools.transformImage( imageUp, 1, -1 );
		deadAni.addFrame( imageDead, 10000 );
		
		setAnimation( jumpAni );
		getRectangle().setBounds( getXPos(), getYPos(), getWidth(), getHeight() );
	}

	@Override
	public void collideX()
	{
		setXSpeed( getXSpeed() * -1 );
	}
	
	@Override
	public void collideY()
	{
		if ( isAlive() )
			setYSpeed( -0.8 );
	}
	
	@Override
	public void update( long fpsTime )
	{
		super.update( fpsTime );
		
		if ( isAlive() )
			setAnimation( jumpAni );
		else
			setAnimation( deadAni );
		
		getRectangle().setBounds( getXPos() +3, getYPos(), getWidth() -6, getHeight() );
	}

	@Override
	public void collidesWith( Spungy spungy )
	{
		if ( spungy.isAlive() )
		{
			setYPos( spungy.getYPos() - getHeight() );
			collideY();
		}
	}

	@Override
	public void collidesWith( Fireball fireball )
	{
		die();
		fireball.die();
	}
}

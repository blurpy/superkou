
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

public class Spungy extends Enemy
{
	private Animation rightWalkAni, leftWalkAni, rightDeadAni, leftDeadAni;

	public Spungy( int xPos, int yPos )
	{
		super( xPos, yPos );
		
		rightWalkAni = new Animation();
		leftWalkAni = new Animation();
		rightDeadAni = new Animation();
		leftDeadAni = new Animation();
		
		Image image1Left = Tools.getImage( "graphics/spungy_center.png" );
		Image image2Left = Tools.getImage( "graphics/spungy_right.png" );
		Image image3Left = Tools.getImage( "graphics/spungy_left.png" );
		
		leftWalkAni.addFrame( image1Left, 300 );
		leftWalkAni.addFrame( image2Left, 300 );
		leftWalkAni.addFrame( image1Left, 300 );
		leftWalkAni.addFrame( image3Left, 300 );
		
		Image image1Right = Tools.transformImage( image1Left, -1, 1 );
		Image image2Right = Tools.transformImage( image2Left, -1, 1 );
		Image image3Right = Tools.transformImage( image3Left, -1, 1 );
		
		rightWalkAni.addFrame( image1Right, 300 );
		rightWalkAni.addFrame( image2Right, 300 );
		rightWalkAni.addFrame( image1Right, 300 );
		rightWalkAni.addFrame( image3Right, 300 );
		
		Image imageLeftDead = Tools.transformImage( image1Left, 1, -1 );
		leftDeadAni.addFrame( imageLeftDead, 10000 );
		
		Image imageRightDead = Tools.transformImage( image1Right, 1, -1 );
		rightDeadAni.addFrame( imageRightDead, 10000 );
		
		setAnimation( rightWalkAni );
		setXSpeed( 0.09 );
	}

	@Override
	public void collideX()
	{
		setXSpeed( getXSpeed() * -1 );
	}

	@Override
	public void update( long fpsTime )
	{
		super.update( fpsTime );
		
		if ( getXSpeed() > 0 )
			setAnimation( rightWalkAni );
		else if ( getXSpeed() < 0 )
			setAnimation( leftWalkAni );

		if ( isAlive() == false )
		{
			if ( getAnimation() == rightWalkAni )
				setAnimation( rightDeadAni );
			else if ( getAnimation() == leftWalkAni )
				setAnimation( leftDeadAni );
		}
	}

	@Override
	public void collidesWith( Spungy spungy )
	{
		if ( spungy.isAlive() )
		{
			collideX();
			spungy.collideX();
			
			int offset = Math.round( super.getRectangle().intersection( spungy.getRectangle() ).width / 2 ) +1;
			
			if ( getXSpeed() > 0 )
			{
				setXPos( getXPos() + offset );
				spungy.setXPos( spungy.getXPos() - offset );
			}
			
			else
			{
				setXPos( getXPos() - offset );
				spungy.setXPos( spungy.getXPos() + offset );
			}
			
			update( 0 );
			spungy.update( 0 );
		}
	}
	
	@Override
	public void collidesWith( Fireball fireball )
	{
		die();
		fireball.die();
	}

	// Spungy has a tendency to get stuck if it crashes with another spungy.
	// Need to update this every time
	@Override
	public Rectangle getRectangle()
	{
		super.getRectangle().setBounds( getXPos() +5, getYPos(), getWidth() -10, getHeight() );
		return super.getRectangle();
	}
}


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

package net.usikkert.superkou.sprites;

public class Fireball extends Being
{
	public Fireball( int xPos, int yPos, Animation animation )
	{
		super( xPos, yPos );

		setAnimation( animation );
		getRectangle().setBounds( getXPos(), getYPos(), getWidth(), getHeight() );
	}

	@Override
	public void collideX()
	{
		die();
	}

	@Override
	public void collideY()
	{
		setYSpeed( -0.2 );
	}

	@Override
	public void die()
	{
		setXSpeed( 0 );
		setVisible( false );
		setAlive( false );
		setRemovable( true );
	}

	@Override
	public void update( long fpsTime )
	{
		getRectangle().setBounds( getXPos(), getYPos(), getWidth(), getHeight() );
		super.update( fpsTime );
	}
}

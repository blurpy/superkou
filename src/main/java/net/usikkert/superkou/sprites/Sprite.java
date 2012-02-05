
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

import java.awt.Rectangle;

public abstract class Sprite
{
	private int xPos, yPos;
	private Animation animation;
	private boolean visible, removable;
	private Rectangle rectangle;

	public Sprite( int xPos, int yPos )
	{
		this.xPos = xPos;
		this.yPos = yPos;

		visible = true;
		rectangle = new Rectangle();
	}

	public void update( long fpsTime )
	{
		animation.update( fpsTime );
	}

	public Animation getAnimation()
	{
		return animation;
	}

	public void setAnimation( Animation animation )
	{
		this.animation = animation;
	}

	public boolean isRemovable()
	{
		return removable;
	}

	public void setRemovable( boolean removable )
	{
		this.removable = removable;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible( boolean visible )
	{
		this.visible = visible;
	}

	public int getXPos()
	{
		return xPos;
	}

	public void setXPos( int pos )
	{
		xPos = pos;
	}

	public int getYPos()
	{
		return yPos;
	}

	public void setYPos( int pos )
	{
		yPos = pos;
	}

	public int getHeight()
	{
		return animation.getFrame().getHeight( null );
	}

	public int getWidth()
	{
		return animation.getFrame().getWidth( null );
	}

	public Rectangle getRectangle()
	{
		return rectangle;
	}
}

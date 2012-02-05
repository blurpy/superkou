
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

public abstract class Being extends Sprite
{
	public enum Direction { LEFT, RIGHT };
	
	private double xSpeed, ySpeed;
	private boolean alive, ground, gravity;
	private Direction direction;
	
	public Being( int xPos, int yPos )
	{
		super( xPos, yPos );
		
		alive = true;
		gravity = true;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive( boolean alive )
	{
		this.alive = alive;
	}

	public double getXSpeed()
	{
		return xSpeed;
	}

	public void setXSpeed( double xSpeed )
	{
		this.xSpeed = xSpeed;
	}

	public double getYSpeed()
	{
		return ySpeed;
	}

	public void setYSpeed( double ySpeed )
	{
		this.ySpeed = ySpeed;
	}
	
	public void collideX()
	{
		
	}
	
	public void collideY()
	{

	}

	public boolean isGround()
	{
		return ground;
	}

	public void setGround( boolean ground )
	{
		this.ground = ground;
	}
	
	public void die()
	{
		setAlive( false );
		setXSpeed( 0.0 );
	}
	
	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection( Direction direction )
	{
		this.direction = direction;
	}

	public void collidesWith( Cheese cheese )
	{
		
	}
	
	public void collidesWith( Banana banana )
	{
		
	}
	
	public void collidesWith( QuestionBox qb )
	{
		
	}
	
	public void collidesWith( Upgrade upgrade )
	{
		
	}
	
	public void collidesWith( Spungy spungy )
	{
		
	}
	
	public void collidesWith( Goompa goompa )
	{
		
	}
	
	public void collidesWith( Fireball fireball )
	{
		
	}
	
	public void collidesWith( Kou kou )
	{
		
	}

	public boolean isGravity()
	{
		return gravity;
	}

	public void setGravity( boolean gravity )
	{
		this.gravity = gravity;
	}
}

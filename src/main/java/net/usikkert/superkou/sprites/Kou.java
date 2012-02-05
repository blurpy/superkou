
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
import java.util.*;
import java.util.List;
import net.usikkert.superkou.*;
import net.usikkert.superkou.Status.State;
import net.usikkert.superkou.sprites.Upgrade.Stage;

public class Kou extends Being
{
	private Animation largeLeftWalkAni, largeLeftStandAni, largeLeftCheerAni, largeLeftDeadAni;
	private Animation largeRightWalkAni, largeRightStandAni, largeRightCheerAni, largeRightDeadAni;
	private Animation smallLeftWalkAni, smallLeftStandAni, smallLeftCheerAni, smallLeftDeadAni;
	private Animation smallRightWalkAni, smallRightStandAni, smallRightCheerAni, smallRightDeadAni;
	private Animation crazyLeftWalkAni, crazyLeftStandAni, crazyLeftCheerAni, crazyLeftDeadAni;
	private Animation crazyRightWalkAni, crazyRightStandAni, crazyRightCheerAni, crazyRightDeadAni;
	private Animation fireballAni;
	private boolean victory, invinsible, readyToShoot;
	private Status status;
	private Timer hitTimer, shootTimer;
	private List<Fireball> fireballs;

	public Kou( int xPos, int yPos )
	{
		super( xPos, yPos );
		
		hitTimer = new Timer();
		shootTimer = new Timer();
		fireballs = new ArrayList<Fireball>();
		readyToShoot = true;
		
		largeLeftWalkAni = new Animation();
		largeLeftStandAni = new Animation();
		largeLeftCheerAni = new Animation();
		largeLeftDeadAni = new Animation();
		largeRightWalkAni = new Animation();
		largeRightStandAni = new Animation();
		largeRightCheerAni = new Animation();
		largeRightDeadAni = new Animation();

		Image image1LargeRight = Tools.getImage( "graphics/kou_large_behind.png" );
		Image image2LargeRight = Tools.getImage( "graphics/kou_large_middle.png" );
		Image image3LargeRight = Tools.getImage( "graphics/kou_large_front.png" );

		Image image2LargeRightCheer = Tools.getImage( "graphics/kou_large_cheer2.png" );
		Image image3LargeRightCheer = Tools.getImage( "graphics/kou_large_cheer3.png" );

		largeRightWalkAni.addFrame( image1LargeRight, 200 );
		largeRightWalkAni.addFrame( image2LargeRight, 200 );
		largeRightWalkAni.addFrame( image3LargeRight, 200 );
		largeRightWalkAni.addFrame( image2LargeRight, 200 );

		largeRightStandAni.addFrame( image2LargeRight, 20000 );
		largeRightDeadAni.addFrame( Tools.transformImage( image2LargeRight, 1, -1 ), 20000 );

		largeRightCheerAni.addFrame( image2LargeRight, 200 );
		largeRightCheerAni.addFrame( image2LargeRightCheer, 200 );
		largeRightCheerAni.addFrame( image3LargeRightCheer, 200 );
		largeRightCheerAni.addFrame( image2LargeRightCheer, 200 );

		largeLeftWalkAni.addFrame( Tools.transformImage( image1LargeRight, -1, 1 ), 200 );
		largeLeftWalkAni.addFrame( Tools.transformImage( image2LargeRight, -1, 1 ), 200 );
		largeLeftWalkAni.addFrame( Tools.transformImage( image3LargeRight, -1, 1 ), 200 );
		largeLeftWalkAni.addFrame( Tools.transformImage( image2LargeRight, -1, 1 ), 200 );

		largeLeftStandAni.addFrame( Tools.transformImage( image2LargeRight, -1, 1 ), 200 );
		largeLeftDeadAni.addFrame( Tools.transformImage( image2LargeRight, -1, -1 ), 20000 );

		largeLeftCheerAni.addFrame( Tools.transformImage( image2LargeRight, -1, 1 ), 200 );
		largeLeftCheerAni.addFrame( Tools.transformImage( image2LargeRightCheer, -1, 1 ), 200 );
		largeLeftCheerAni.addFrame( Tools.transformImage( image3LargeRightCheer, -1, 1 ), 200 );
		largeLeftCheerAni.addFrame( Tools.transformImage( image2LargeRightCheer, -1, 1 ), 200 );

		smallLeftWalkAni = new Animation();
		smallLeftStandAni = new Animation();
		smallLeftCheerAni = new Animation();
		smallLeftDeadAni = new Animation();
		smallRightWalkAni = new Animation();
		smallRightStandAni = new Animation();
		smallRightCheerAni = new Animation();
		smallRightDeadAni = new Animation();

		Image image1SmallRight = Tools.getImage( "graphics/kou_small_behind.png" );
		Image image2SmallRight = Tools.getImage( "graphics/kou_small_middle.png" );
		Image image3SmallRight = Tools.getImage( "graphics/kou_small_front.png" );

		Image image2SmallRightCheer = Tools.getImage( "graphics/kou_small_cheer2.png" );
		Image image3SmallRightCheer = Tools.getImage( "graphics/kou_small_cheer3.png" );

		smallRightWalkAni.addFrame( image1SmallRight, 180 );
		smallRightWalkAni.addFrame( image2SmallRight, 180 );
		smallRightWalkAni.addFrame( image3SmallRight, 180 );
		smallRightWalkAni.addFrame( image2SmallRight, 180 );

		smallRightStandAni.addFrame( image2SmallRight, 20000 );
		smallRightDeadAni.addFrame( Tools.transformImage( image2SmallRight, 1, -1 ), 20000 );

		smallRightCheerAni.addFrame( image2SmallRight, 180 );
		smallRightCheerAni.addFrame( image2SmallRightCheer, 180 );
		smallRightCheerAni.addFrame( image3SmallRightCheer, 180 );
		smallRightCheerAni.addFrame( image2SmallRightCheer, 180 );

		smallLeftWalkAni.addFrame( Tools.transformImage( image1SmallRight, -1, 1 ), 180 );
		smallLeftWalkAni.addFrame( Tools.transformImage( image2SmallRight, -1, 1 ), 180 );
		smallLeftWalkAni.addFrame( Tools.transformImage( image3SmallRight, -1, 1 ), 180 );
		smallLeftWalkAni.addFrame( Tools.transformImage( image2SmallRight, -1, 1 ), 180 );

		smallLeftStandAni.addFrame( Tools.transformImage( image2SmallRight, -1, 1 ), 20000 );
		smallLeftDeadAni.addFrame( Tools.transformImage( image2SmallRight, -1, -1 ), 20000 );

		smallLeftCheerAni.addFrame( Tools.transformImage( image2SmallRight, -1, 1 ), 180 );
		smallLeftCheerAni.addFrame( Tools.transformImage( image2SmallRightCheer, -1, 1 ), 180 );
		smallLeftCheerAni.addFrame( Tools.transformImage( image3SmallRightCheer, -1, 1 ), 180 );
		smallLeftCheerAni.addFrame( Tools.transformImage( image2SmallRightCheer, -1, 1 ), 180 );

		crazyLeftWalkAni = new Animation();
		crazyLeftStandAni = new Animation();
		crazyLeftCheerAni = new Animation();
		crazyLeftDeadAni = new Animation();
		crazyRightWalkAni = new Animation();
		crazyRightStandAni = new Animation();
		crazyRightCheerAni = new Animation();
		crazyRightDeadAni = new Animation();

		Image image1CrazyRight = Tools.getImage( "graphics/kou_crazy_behind.png" );
		Image image2CrazyRight = Tools.getImage( "graphics/kou_crazy_middle.png" );
		Image image3CrazyRight = Tools.getImage( "graphics/kou_crazy_front.png" );

		Image image2CrazyRightCheer = Tools.getImage( "graphics/kou_crazy_cheer2.png" );
		Image image3CrazyRightCheer = Tools.getImage( "graphics/kou_crazy_cheer3.png" );

		crazyRightWalkAni.addFrame( image1CrazyRight, 200 );
		crazyRightWalkAni.addFrame( image2CrazyRight, 200 );
		crazyRightWalkAni.addFrame( image3CrazyRight, 200 );
		crazyRightWalkAni.addFrame( image2CrazyRight, 200 );

		crazyRightStandAni.addFrame( image2CrazyRight, 20000 );
		crazyRightDeadAni.addFrame( Tools.transformImage( image2CrazyRight, 1, -1  ), 20000 );

		crazyRightCheerAni.addFrame( image2CrazyRight, 200 );
		crazyRightCheerAni.addFrame( image2CrazyRightCheer, 200 );
		crazyRightCheerAni.addFrame( image3CrazyRightCheer, 200 );
		crazyRightCheerAni.addFrame( image2CrazyRightCheer, 200 );

		crazyLeftWalkAni.addFrame( Tools.transformImage( image1CrazyRight, -1, 1 ), 200 );
		crazyLeftWalkAni.addFrame( Tools.transformImage( image2CrazyRight, -1, 1 ), 200 );
		crazyLeftWalkAni.addFrame( Tools.transformImage( image3CrazyRight, -1, 1 ), 200 );
		crazyLeftWalkAni.addFrame( Tools.transformImage( image2CrazyRight, -1, 1 ), 200 );

		crazyLeftStandAni.addFrame( Tools.transformImage( image2CrazyRight, -1, 1 ), 200 );
		crazyLeftDeadAni.addFrame( Tools.transformImage( image2CrazyRight, -1, -1 ), 20000 );

		crazyLeftCheerAni.addFrame( Tools.transformImage( image2CrazyRight, -1, 1 ), 200 );
		crazyLeftCheerAni.addFrame( Tools.transformImage( image2CrazyRightCheer, -1, 1 ), 200 );
		crazyLeftCheerAni.addFrame( Tools.transformImage( image3CrazyRightCheer, -1, 1 ), 200 );
		crazyLeftCheerAni.addFrame( Tools.transformImage( image2CrazyRightCheer, -1, 1 ), 200 );
		
		fireballAni = new Animation();

		Image imageFireball_ur = Tools.getImage( "graphics/fireball_up_right.png" );
		Image imageFireball_dr = Tools.getImage( "graphics/fireball_down_right.png" );
		Image imageFireball_ul = Tools.getImage( "graphics/fireball_up_left.png" );
		Image imageFireball_dl = Tools.getImage( "graphics/fireball_down_left.png" );

		fireballAni.addFrame( imageFireball_ur, 100 );
		fireballAni.addFrame( imageFireball_dr, 100 );
		fireballAni.addFrame( imageFireball_dl, 100 );
		fireballAni.addFrame( imageFireball_ul, 100 );
		
		setAnimation( smallRightStandAni );
		setDirection( Direction.RIGHT );
		
		getRectangle().setBounds( getXPos(), getYPos(), getWidth(), getHeight() );
	}

	public boolean isVictory()
	{
		return victory;
	}

	public void setVictory( boolean victory )
	{
		this.victory = victory;
	}
	
	public Status getStatus()
	{
		return status;
	}

	public void setStatus( Status status )
	{
		this.status = status;
	}

	public void jump( boolean extraJump )
	{
		if ( extraJump || ( isGround() && getYSpeed() >= 0 && getYSpeed() < 0.1 ) )
		{
			setYSpeed( -1 );
			setGround( false );
		}
	}
	
	public void shoot()
	{
		if ( readyToShoot && status.getState() == State.CRAZY )
		{
			double xSpeed = 0.35;
	
			if ( getXSpeed() < 0 || getAnimation() == smallLeftStandAni || getAnimation() == largeLeftStandAni || getAnimation() == crazyLeftStandAni )
				xSpeed = -0.35;
	
			Fireball fireball = new Fireball( getXPos() + getWidth() / 2, getYPos() + getHeight() / 2, fireballAni.clone() );
			fireball.setXSpeed( xSpeed );
			fireball.setYSpeed( -0.3 );
			fireballs.add( fireball );
			
			shootTimer.schedule( new TimerTask()
			{
				@Override
				public void run()
				{
					readyToShoot = false;
					
					try
					{
						Thread.sleep( 700 );
					}
					
					catch ( InterruptedException e )
					{
						e.printStackTrace();
					}
					
					readyToShoot = true;
				}
			}, 0 );
		}
	}
	
	public List<Fireball> getFireballs()
	{
		return fireballs;
	}

	@Override
	public void collideY()
	{
		setGround( true );
	}

	@Override
	public void update( long fpsTime )
	{
		if ( status.getState() == State.SMALL )
		{
			if ( getXSpeed() > 0 )
			{
				// Because the small image of Kou has more empty space on one side than the other.
				// So if Kou turns around after looking into a wall, his back will be inside it.
				if ( getDirection() == Direction.LEFT )
					setXPos( getXPos() + 7 );
				
				setAnimation( smallRightWalkAni );
				setDirection( Direction.RIGHT );
			}
			
			else if ( getXSpeed() < 0 )
			{
				if ( getDirection() == Direction.RIGHT )
					setXPos( getXPos() - 7 );
				
				setAnimation( smallLeftWalkAni );
				setDirection( Direction.LEFT );
			}
			
			else
			{
				if ( isAlive() == false )
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( smallRightDeadAni );
					else
						setAnimation( smallLeftDeadAni );
				}
		
				else if ( victory )
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( smallRightCheerAni );
					else
						setAnimation( smallLeftCheerAni );
				}
				
				else
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( smallRightStandAni );
					else
						setAnimation( smallLeftStandAni );
				}
			}
			
			if ( getDirection() == Direction.RIGHT )
				getRectangle().setBounds( getXPos() +5, getYPos(), getWidth() -20, getHeight() );
			else
				getRectangle().setBounds( getXPos() +14, getYPos(), getWidth() -20, getHeight() );
		}

		else if ( status.getState() == State.LARGE )
		{
			if ( getXSpeed() > 0 )
			{
				setAnimation( largeRightWalkAni );
				setDirection( Direction.RIGHT );
			}
			
			else if ( getXSpeed() < 0 )
			{
				setAnimation( largeLeftWalkAni );
				setDirection( Direction.LEFT );
			}
			
			else
			{
				if ( isAlive() == false )
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( largeRightDeadAni );
					else
						setAnimation( largeLeftDeadAni );
				}

				else if ( victory )
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( largeRightCheerAni );
					else
						setAnimation( largeLeftCheerAni );
				}
				
				else
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( largeRightStandAni );
					else
						setAnimation( largeLeftStandAni );
				}
			}
			
			if ( getDirection() == Direction.RIGHT )
				getRectangle().setBounds( getXPos() +14, getYPos(), getWidth() -29, getHeight() );
			else
				getRectangle().setBounds( getXPos() +14, getYPos(), getWidth() -29, getHeight() );
		}

		else if ( status.getState() == State.CRAZY )
		{
			if ( getXSpeed() > 0 )
			{
				setAnimation( crazyRightWalkAni );
				setDirection( Direction.RIGHT );
			}
			
			else if ( getXSpeed() < 0 )
			{
				setAnimation( crazyLeftWalkAni );
				setDirection( Direction.LEFT );
			}
			
			else
			{
				if ( isAlive() == false )
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( crazyRightDeadAni );
					else
						setAnimation( crazyLeftDeadAni );
				}

				else if ( victory )
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( crazyRightCheerAni );
					else
						setAnimation( crazyLeftCheerAni );
				}
				
				else
				{
					if ( getDirection() == Direction.RIGHT )
						setAnimation( crazyRightStandAni );
					else
						setAnimation( crazyLeftStandAni );
				}
			}
			
			if ( getDirection() == Direction.RIGHT )
				getRectangle().setBounds( getXPos() +14, getYPos(), getWidth() -29, getHeight() );
			else
				getRectangle().setBounds( getXPos() +14, getYPos(), getWidth() -29, getHeight() );
		}
		
		super.update( fpsTime );
	}
	
	@Override
	public void collidesWith( Cheese cheese )
	{
		cheese.setVisible( false );
		cheese.setRemovable( true );
		
		if ( status.getCheese() == 49 )
		{
			status.setCheese( 0 );
			status.incLives();
		}
		
		else
			status.incCheese();
	}
	
	@Override
	public void collidesWith( QuestionBox qb )
	{
		if ( !qb.isHit() )
		{
			Sprite contents = qb.getContents();
			
			if ( contents instanceof Upgrade )
			{
				Upgrade up = (Upgrade) contents;
				
				if ( status.getState() != State.SMALL )
					up.setStage( Upgrade.Stage.CRAZY );
			}
			
			qb.setHit();
		}
	}
	
	@Override
	public void collidesWith( Upgrade upgrade )
	{
		upgrade.setVisible( false );
		upgrade.setRemovable( true );

		if ( upgrade.getStage() == Stage.BIG )
			status.setState( State.LARGE );
		else if ( upgrade.getStage() == Stage.CRAZY )
			status.setState( State.CRAZY );
		
		update( 0 );
		setYPos( upgrade.getYPos() + upgrade.getHeight() - getHeight() -5 );
	}

	@Override
	public void collidesWith( Banana banana )
	{
		banana.setVisible( false );
		banana.setRemovable( true );
		setVictory( true );
		status.incNextLevel();
		setXSpeed( 0.0 );
		update( 0 );
	}

	@Override
	public void collidesWith( Goompa goompa )
	{
		if ( goompa.isAlive() )
		{
			Rectangle crashRect = getRectangle().intersection( goompa.getRectangle() );
			
			boolean badHit = false;
			
			// Check if Kou hit below a Goompa
			if ( crashRect.getWidth() > 10 )
			{
				if ( getYPos() == crashRect.getMinY() )
					badHit = true;
			}
	
			// Or hit the side
			else if ( crashRect.getHeight() > 10 )
				badHit = true;
			
			if ( badHit )
				checkHealth();
			
			else
			{
				setYPos( goompa.getYPos() - getHeight() );
				jump( true );
				goompa.die();
				status.incEnemies();
			}
		}
	}

	@Override
	public void collidesWith( Spungy spungy )
	{
		if ( spungy.isAlive() )
		{
			Rectangle crashRect = getRectangle().intersection( spungy.getRectangle() );
			
			boolean badHit = false;
			
			// Check if Kou hit the side of a Spungy
			if ( crashRect.getWidth() < 5 && crashRect.getHeight() > 10 )
				badHit = true;
			
			if ( badHit )
				checkHealth();
			
			else
			{
				setYPos( spungy.getYPos() - getHeight() );
				jump( true );
				spungy.die();
				status.incEnemies();
			}
		}
	}
	
	private void checkHealth()
	{
		if ( !invinsible && !victory )
		{
			if ( status.getState() == State.SMALL )
			{
				die();
			}
			
			else
			{
				if ( status.getState() == State.CRAZY )
					status.setState( State.LARGE );
				else
					status.setState( State.SMALL );
				
				hitTimer.schedule( new TimerTask()
				{
					@Override
					public void run()
					{
						invinsible = true;
						
						try
						{
							Thread.sleep( 2000 );
						}
						
						catch ( InterruptedException e )
						{
							e.printStackTrace();
						}
						
						invinsible = false;
					}
				}, 0 );
			}
		}
	}

	@Override
	public void die()
	{
		if ( isAlive() )
		{
			setAlive( false );
			setXSpeed( 0.0 );
			update( 0 );
		}
	}
}

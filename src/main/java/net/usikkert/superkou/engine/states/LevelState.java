
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

package net.usikkert.superkou.engine.states;

import java.awt.*;
import java.util.*;
import java.util.List;
import net.usikkert.superkou.*;
import net.usikkert.superkou.engine.*;
import net.usikkert.superkou.gui.*;
import net.usikkert.superkou.input.ButtonEvent;
import net.usikkert.superkou.level.*;
import net.usikkert.superkou.sprites.*;

public class LevelState implements State, SecondListener
{
	private Image levelImage;
	private Kou kou;
	private GameFacade facade;
	private LevelParser parser;
	private Level level;
	private boolean ready, pause, updateReady;
	private GameHUD hud;
	private MessageBox msgBox;

	public LevelState( GameFacade facade )
	{
		this.facade = facade;
		parser = new LevelParser();
		hud = new GameHUD( facade );
		msgBox = new MessageBox();
	}

	public void init()
	{
		System.out.println( "LevelState.init()" );

		loadLevel();
	}

	private void loadLevel()
	{
		facade.getFpsCounter().removeSecondListener( this );
		ready = false;
		updateReady = false;

		try
		{
			level = parser.loadLevel( "levels/level" + facade.getStatus().getNextLevel() + ".txt" );
			levelImage = level.getBackground();
			kou = level.getKou();
			kou.setStatus( facade.getStatus() );
			kou.update( 0 );
			kou.setYPos( kou.getYPos() - kou.getHeight() + Constants.TILE_SIZE -1 );
			kou.update( 0 );
			facade.getStatus().setLevelTime( level.getTime() );
			facade.getStatus().setLevel( facade.getStatus().getNextLevel() );

			ready = true;
			facade.getFpsCounter().addSecondListener( this );
		}

		catch ( LevelException e )
		{
			e.printStackTrace();

			if ( facade.getStatus().getNextLevel() == 1 )
			{
				facade.getStatus().resetStatus();
				facade.changeState( new ErrorState( facade, "Uh oh. No maps found :(" ) );
			}

			else
			{
				facade.getStatus().resetStatus();
				facade.changeState( new GameFinishedState( facade ) );
			}
		}
	}

	public void cleanup()
	{
		System.out.println( "LevelState.cleanup()" );

		ready = false;
		updateReady = false;
		facade.getFpsCounter().removeSecondListener( this );
	}

	public void draw( Graphics g )
	{
		if ( ready )
		{
			int offset = 0;
			int levelWidthPix = Tools.tileToPixel( level.getWidth() );

			// Keep Kou at the center of the screen, if Kou is not too close to the edges
			if ( kou.getXPos() > Constants.WINDOW_WIDTH / 2 )
			{
				if ( kou.getXPos() > levelWidthPix - Constants.WINDOW_WIDTH / 2 )
					offset = levelWidthPix - Constants.WINDOW_WIDTH;
				else
					offset = kou.getXPos() - Constants.WINDOW_WIDTH / 2;
			}

			// Scroll the background
			double bgStartPos = (double) levelImage.getWidth( null ) / ( (double) levelWidthPix
					- Constants.WINDOW_WIDTH ) * (double) offset / 2.0;
			g.drawImage( levelImage, (int) bgStartPos * -1, 0, null );

			// Locate the visible tiles
			int startYTile = 0;
			int startXTile = Tools.pixelToTile(  kou.getXPos() - ( kou.getXPos() - offset ) );
			int stopXTile = Math.min( startXTile + Tools.pixelToTile( Constants.WINDOW_WIDTH ) +1,
					Tools.pixelToTile( levelWidthPix ) );

			// Draw visible tiles
			for ( int y = startYTile; y < level.getHeight(); y++ )
			{
				for ( int x = startXTile; x < stopXTile; x++ )
				{
					Image tile = level.getTile( y, x );

					if ( tile != null )
						g.drawImage( tile, Tools.tileToPixel( x ) - offset, Tools.tileToPixel( y ),
								Constants.TILE_SIZE, Constants.TILE_SIZE, null );

					//g.setColor( Color.LIGHT_GRAY );
					//g.drawRect( Tools.tileToPixel( x ) - offset, Tools.tileToPixel( y ), Constants.TILE_SIZE, Constants.TILE_SIZE );
				}
			}

			// Draw sprites
			Iterator<Sprite> sprites = level.getSprites();

			while ( sprites.hasNext() )
			{
				Sprite sprite = sprites.next();

				if ( sprite.isVisible() )
					g.drawImage( sprite.getAnimation().getFrame(), sprite.getXPos() -offset, sprite.getYPos(), null );

				//g.drawRect( sprite.getRectangle().x -offset, sprite.getRectangle().y, sprite.getRectangle().width, sprite.getRectangle().height );
			}

			// Stop Kou from moving beyond the edges of the level
			if ( kou.getXPos() < 0 )
				kou.setXPos( 0 );
			else if ( kou.getXPos() > levelWidthPix - kou.getWidth() )
				kou.setXPos( levelWidthPix - kou.getWidth() );

			g.drawImage( kou.getAnimation().getFrame(), kou.getXPos() -offset, kou.getYPos(), null );
			//g.drawRect( kou.getRectangle().x -offset, kou.getRectangle().y, kou.getRectangle().width, kou.getRectangle().height );

			if ( kou.isVictory() && !pause )
			{
				msgBox.drawMessage( g, "Congratulations!\nYou found a banana!", 30, 0, 0 );
			}

			else if ( !kou.isAlive() && !pause )
			{
				if ( facade.getStatus().getLevelTime() == 0 )
					msgBox.drawMessage( g, "Darn it! Time's up :(", 30, 0, 0 );
				else
					msgBox.drawMessage( g, "Darn it! You died :(", 30, 0, 0 );
			}

			hud.drawHUD( g );
		}
	}

	public void update( long fpsTime )
	{
		// Animate sprites
		if ( !pause && ready && updateReady )
		{
			updateSpriteList();
			checkTileCollision( kou, fpsTime );

			if ( kou.isAlive() && !kou.isVictory() )
				checkSpriteCollision( kou );

			kou.update( fpsTime );

			Iterator<Sprite> sprites = level.getSprites();

			while ( sprites.hasNext() )
			{
				Sprite sprite = sprites.next();

				if ( sprite.isVisible() )
				{
					if ( sprite instanceof Being )
					{
						Being being = (Being) sprite;
						checkTileCollision( being, fpsTime );

						if ( being.isAlive() )
							checkSpriteCollision( being );
					}

					sprite.update( fpsTime );
				}
			}
		}

		// Hope this will stabilize the fps before updating the positions
		else if ( !pause && ready && !updateReady )
		{
			updateReady = true;
		}
	}

	public void buttonPressed( ButtonEvent e )
	{
		if ( e.getButton() == ButtonEvent.Button.MENU )
		{
			facade.pushState( new LevelMenuState( facade ) );
		}

		else if ( e.getButton() == ButtonEvent.Button.LEFT )
		{
			if ( kou.isAlive() && !kou.isVictory() )
				kou.setXSpeed( -0.3 );
		}

		else if ( e.getButton() == ButtonEvent.Button.RIGHT )
		{
			if ( kou.isAlive() && !kou.isVictory() )
				kou.setXSpeed( 0.3 );
		}

		else if ( e.getButton() == ButtonEvent.Button.JUMP )
		{
			if ( kou.isAlive() && !kou.isVictory() )
				kou.jump( false );
		}

		else if ( e.getButton() == ButtonEvent.Button.ACTION )
		{
			if ( !kou.isAlive() )
			{
				if ( facade.getStatus().getLives() <= 0 )
				{
					facade.getStatus().resetStatus();
					facade.changeState( new GameOverState( facade ) );
				}

				else
				{
					facade.getStatus().decLives();
					facade.getStatus().setState( Status.State.SMALL );
					loadLevel();
				}

			}

			else if ( kou.isVictory() )
			{
				loadLevel();
			}
		}

		else if ( e.getButton() == ButtonEvent.Button.SHOOT )
		{
			if ( kou.isAlive() && !kou.isVictory() )
			{
				kou.shoot();
			}
		}
	}

	public void buttonReleased( ButtonEvent e )
	{
		if ( e.getButton() == ButtonEvent.Button.LEFT || e.getButton() == ButtonEvent.Button.RIGHT )
		{
			kou.setXSpeed( 0.0 );
		}
	}

	public void pause()
	{
		System.out.println( "LevelState.pause()" );

		pause = true;
	}

	public void resume()
	{
		System.out.println( "LevelState.resume()" );

		pause = false;
	}

	private void updateSpriteList()
	{
		ListIterator<Sprite> sprites = level.getSprites();
		List<Fireball> fireballs = kou.getFireballs();

		if ( fireballs.size() > 0 )
		{
			Iterator<Fireball> it = fireballs.iterator();

			while ( it.hasNext() )
			{
				sprites.add( it.next() );
			}

			fireballs.clear();
		}

		while ( sprites.hasNext() )
		{
			Sprite sprite = sprites.next();

			if ( sprite.isRemovable() )
				sprites.remove();
		}
	}

	private void checkSpriteCollision( Being being )
	{
		Iterator<Sprite> sprites = level.getSprites();

		while ( sprites.hasNext() )
		{
			Sprite sprite = sprites.next();

			if ( sprite != being && sprite.isVisible() )
			{
				Rectangle beingRect = being.getRectangle();
				Rectangle spriteRect = sprite.getRectangle();

				if ( beingRect.intersects( spriteRect ) )
				{
					if ( sprite instanceof QuestionBox )
					{
						being.collidesWith( (QuestionBox) sprite );
					}

					else if ( sprite instanceof Cheese )
					{
						being.collidesWith( (Cheese) sprite );
					}

					else if ( sprite instanceof Upgrade )
					{
						being.collidesWith( (Upgrade) sprite );
					}

					else if ( sprite instanceof Spungy )
					{
						being.collidesWith( (Spungy) sprite );
					}

					else if ( sprite instanceof Goompa )
					{
						being.collidesWith( (Goompa) sprite );
					}

					else if ( sprite instanceof Fireball )
					{
						being.collidesWith( (Fireball) sprite );
					}

					else if ( sprite instanceof Banana )
					{
						being.collidesWith( (Banana) sprite );
					}
				}
			}
		}
	}

	private void checkTileCollision( Being being, long fpsTime )
	{
		// Apply gravity to beings
		if ( being.isGravity() )
			being.setYSpeed( being.getYSpeed() + 0.003 * fpsTime );

		Rectangle rect = being.getRectangle();

		// Move to the right
		if ( being.getXSpeed() > 0 )
		{
			int newXPos = (int) ( rect.x + Math.round( being.getXSpeed() * fpsTime ) );
			int colTile = Tools.pixelToTile( newXPos + rect.width );
			int startY = Math.max( 0, Tools.pixelToTile( rect.y ) );
			int stopY = Tools.pixelToTile( rect.y + rect.height );
			boolean stop = false;
			stopY++;

			for ( int i = startY; i < stopY; i++ )
			{
				if ( i < level.getHeight() && level.getWidth( i ) > colTile && level.getTile( i, colTile ) != null )
				{
					stop = true;
					break;
				}
			}

			int offset = rect.x - being.getXPos();
			newXPos -= offset;

			if ( stop == false )
				being.setXPos( newXPos );

			else
			{
				being.setXPos( Tools.tileToPixel( colTile ) - rect.width -1 - offset );
				being.collideX();
			}
		}

		// Move to the left
		else if ( being.getXSpeed() < 0 )
		{
			int newXPos = (int) ( rect.x + Math.round( being.getXSpeed() * fpsTime ) );
			int colTile = Tools.pixelToTile( newXPos );
			int startY = Math.max( 0, Tools.pixelToTile( rect.y ) );
			int stopY = Tools.pixelToTile( rect.y + rect.height );
			boolean stop = false;
			stopY++;

			for ( int i = startY; i < stopY; i++ )
			{
				if ( i < level.getHeight() && level.getWidth( i ) > colTile && level.getTile( i, colTile ) != null )
				{
					stop = true;
					break;
				}
			}

			int offset = rect.x - being.getXPos();
			newXPos -= offset;

			if ( stop == false )
				being.setXPos( newXPos );

			else
			{
				being.setXPos( Tools.tileToPixel( colTile ) + Constants.TILE_SIZE - offset );
				being.collideX();
			}
		}

		// Fall
		if ( being.getYSpeed() > 0 )
		{
			int newYPos = (int) ( rect.y + Math.round( being.getYSpeed() * fpsTime ) );
			int startY = Math.min( Math.max( 0, Tools.pixelToTile( rect.y + rect.height ) ), level.getHeight() -1 );
			int stopY = Math.min( Math.max( 0, Tools.pixelToTile( newYPos + rect.height ) ), level.getHeight() -1 );
			int startX = Tools.pixelToTile( rect.x );
			int stopX = Tools.pixelToTile( rect.x + rect.width );
			int colTile = 0;
			boolean stop = false;
			stopX++;
			stopY++;

			for ( int y = startY; y < stopY; y++ )
			{
				for ( int x = startX; x < stopX; x++ )
				{
					if ( x < level.getWidth( y ) && level.getTile( y, x ) != null )
					{
						stop = true;
						colTile = y;
						break;
					}
				}

				if ( stop )
					break;
			}

			if ( stop == false )
				being.setYPos( newYPos );

			else
			{
				being.setYSpeed( 0.0 );
				being.setYPos( Tools.tileToPixel( colTile ) - rect.height -1 );
				being.collideY();
			}

			if ( rect.y + rect.height > Tools.tileToPixel( level.getHeight() ) )
			{
				being.die();
				being.setGravity( false );
				being.setYSpeed( 0.0 );
			}
		}

		// Jump
		else if ( being.getYSpeed() < 0 )
		{
			int newYPos = (int) ( rect.y + Math.round( being.getYSpeed() * fpsTime ) );
			int startY = Math.min( Math.max( 0, Tools.pixelToTile( newYPos ) ), level.getHeight() -1 );
			int stopY = Math.min( Math.max( 0, Tools.pixelToTile( rect.y ) ), level.getHeight() -1 );
			int startX = Tools.pixelToTile( rect.x );
			int stopX = Tools.pixelToTile( rect.x + rect.width );
			int colTile = 0;
			boolean stop = false;
			stopX++;
			stopY++;

			for ( int y = startY; y < stopY; y++ )
			{
				for ( int x = startX; x < stopX; x++ )
				{
					if ( x < level.getWidth( y ) && level.getTile( y, x ) != null )
					{
						stop = true;
						colTile = y;
						break;
					}
				}

				if ( stop )
					break;
			}


			if ( stop == false )
				being.setYPos( newYPos );

			else
			{
				being.setYPos( Tools.tileToPixel(  colTile ) + Constants.TILE_SIZE );
				being.setYSpeed( being.getYSpeed() * -1 );
			}
		}
	}

	public void tick()
	{
		if ( !pause && ready && !kou.isVictory() && kou.isAlive() )
		{
			int time = facade.getStatus().getLevelTime();

			if ( time > 0 )
				facade.getStatus().decLevelTime();

			else
				kou.die();
		}
	}
}

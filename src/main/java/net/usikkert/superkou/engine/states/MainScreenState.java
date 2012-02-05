
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

package net.usikkert.superkou.engine.states;

import java.awt.*;
import net.usikkert.superkou.*;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.input.ButtonEvent;
import net.usikkert.superkou.sprites.Spungy;

public class MainScreenState implements State
{
	private Image screenImage;
	private GameFacade facade;
	private Spungy spungy;
	private boolean ready, updateReady;
	
	public MainScreenState( GameFacade facade )
	{
		this.facade = facade;
		screenImage = Tools.getImage( "graphics/superkou_bg.png" );
		spungy = new Spungy( 1, 10 );
	}
	
	public void cleanup()
	{
		System.out.println( "MainScreenState.cleanup()" );
		
		ready = false;
		updateReady = false;
	}

	public void draw( Graphics g )
	{
		if ( ready )
		{
			g.drawImage( screenImage, 0, 0, null );
			g.drawImage( spungy.getAnimation().getFrame(), spungy.getXPos(), spungy.getYPos(), null );
		}
	}

	public void init()
	{
		System.out.println( "MainScreenState.init()" );
		
		spungy.setYPos( Constants.WINDOW_HEIGHT - spungy.getHeight() );
		facade.pushState( new MainMenuState( facade ) );
		
		ready = true;
		updateReady = false;
	}

	public void pause()
	{
		System.out.println( "MainScreenState.pause()" );
	}

	public void resume()
	{
		System.out.println( "MainScreenState.resume()" );
	}

	public void update( long fpsTime )
	{
		if ( ready && updateReady)
		{
			int newXPos = (int) ( spungy.getXPos() + Math.round( spungy.getXSpeed() * fpsTime ) );
			
			if ( newXPos > Constants.WINDOW_WIDTH - spungy.getWidth() || newXPos < 0 )
				spungy.collideX();
			else
				spungy.setXPos( newXPos );
			
			spungy.update( fpsTime );
		}
		
		else if ( ready && !updateReady )
		{
			updateReady = true;
		}
	}

	public void buttonPressed( ButtonEvent e )
	{

	}
	
	public void buttonReleased( ButtonEvent e )
	{

	}
}

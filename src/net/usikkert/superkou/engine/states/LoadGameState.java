
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
import net.usikkert.superkou.Constants;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.input.ButtonEvent;

public class LoadGameState implements State
{
	private GameFacade facade;
	private boolean done;
	
	public LoadGameState( GameFacade facade )
	{
		this.facade = facade;
	}
	
	public void cleanup()
	{
		System.out.println( "LoadGameState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		if ( !done )
		{
			g.setColor( Color.BLACK );
			g.fillRect( 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );
			g.setColor( Color.WHITE );
			g.drawString( "LOADING", Constants.WINDOW_WIDTH / 2 -20, Constants.WINDOW_HEIGHT / 2 );
			done = true;
		}
		
		else
			facade.changeState( new MainScreenState( facade ) );
	}

	public void init()
	{
		System.out.println( "LoadGameState.init()" );
	}

	public void buttonPressed( ButtonEvent e )
	{

	}
	
	public void buttonReleased( ButtonEvent e )
	{

	}

	public void pause()
	{
		System.out.println( "LoadGameState.pause()" );
	}

	public void resume()
	{
		System.out.println( "LoadGameState.resume()" );
	}

	public void update( long fpsTime )
	{

	}
}

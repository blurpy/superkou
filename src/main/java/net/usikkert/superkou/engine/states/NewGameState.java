
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
import net.usikkert.superkou.Constants;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.input.ButtonEvent;

public class NewGameState implements State
{
	private GameFacade facade;
	private boolean done, pause;
	private int offsetY;

	public NewGameState( GameFacade facade, int offsetY )
	{
		this.facade = facade;
		this.offsetY = offsetY;
		pause = true;
	}

	public void cleanup()
	{
		System.out.println( "NewGameState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		if ( !pause )
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
				facade.changeState( new LevelState( facade ) );
		}
	}

	public void init()
	{
		System.out.println( "NewGameState.init()" );

		String msg = "You are Super Kou, 1337 Special";
		msg += "\nCow Forces soldier. Your objective";
		msg += "\nis to find the bananas that was";
		msg += "\nstolen from the world's secret";
		msg += "\nbanana storage facility.";

		facade.pushState( new MessageState( facade, msg, 24, offsetY ) );
	}

	public void buttonPressed( ButtonEvent e )
	{

	}

	public void buttonReleased( ButtonEvent e )
	{

	}

	public void pause()
	{
		System.out.println( "NewGameState.pause()" );

		pause = true;
	}

	public void resume()
	{
		System.out.println( "NewGameState.resume()" );

		pause = false;
	}

	public void update( long fpsTime )
	{

	}
}

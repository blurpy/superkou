
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

import java.awt.Graphics;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.gui.*;
import net.usikkert.superkou.input.ButtonEvent;

public class LevelMenuState implements State
{
	private GameMenu levelMenu;
	private GameFacade facade;
	private boolean pause;
	private int fontSize;

	public LevelMenuState( GameFacade facade )
	{
		this.facade = facade;
		fontSize = 28;
		levelMenu = new GameMenu( "Level menu", fontSize, 0 );

		levelMenu.addGameMenuItem( new GameMenuItem( "Continue", true ) );
		levelMenu.addGameMenuItem( new GameMenuItem( "Options", false ) );
		levelMenu.addGameMenuItem( new GameMenuItem( "Quit", false ) );
	}

	public void cleanup()
	{
		System.out.println( "LevelMenuState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		if ( !pause )
			levelMenu.drawMenu( g );
	}

	public void init()
	{
		System.out.println( "LevelMenuState.init()" );
	}

	public void buttonPressed( ButtonEvent e )
	{
		if ( e.getButton() == ButtonEvent.Button.UP )
		{
			levelMenu.selectUp();
		}

		else if ( e.getButton() == ButtonEvent.Button.DOWN )
		{
			levelMenu.selectDown();
		}

		else if ( e.getButton() == ButtonEvent.Button.ACTION )
		{
			String item = levelMenu.getSelectedMenuItemName();

			if ( item.equals( "Continue" ) )
			{
				facade.popState();
			}

			else if ( item.equals( "Options" ) )
			{
				facade.pushState( new OptionsMenuState( facade, fontSize, 0 ) );
			}

			else if ( item.equals( "Quit" ) )
			{
				facade.changeState( new MainScreenState( facade ) );
			}
		}

		else if ( e.getButton() == ButtonEvent.Button.MENU )
		{
			facade.popState();
		}
	}

	public void buttonReleased( ButtonEvent e )
	{

	}

	public void pause()
	{
		System.out.println( "LevelMenuState.pause()" );

		pause = true;
	}

	public void resume()
	{
		System.out.println( "LevelMenuState.resume()" );

		pause = false;
	}

	public void update( long fpsTime )
	{

	}
}

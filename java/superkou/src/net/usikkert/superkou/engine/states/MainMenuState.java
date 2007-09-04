
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

import java.awt.Graphics;

import net.usikkert.superkou.Constants;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.gui.GameMenu;
import net.usikkert.superkou.gui.GameMenuItem;
import net.usikkert.superkou.input.ButtonEvent;

public class MainMenuState implements State
{
	private GameMenu mainMenu;
	private GameFacade facade;
	private boolean pause;
	private int fontSize, offsetY;
	
	public MainMenuState( GameFacade facade )
	{
		this.facade = facade;
		fontSize = 28;
		offsetY = 100;
		mainMenu = new GameMenu( "Main menu", fontSize, offsetY );
		
		mainMenu.addGameMenuItem( new GameMenuItem( "New game", true ) );
		mainMenu.addGameMenuItem( new GameMenuItem( "Options", false ) );
		mainMenu.addGameMenuItem( new GameMenuItem( "How to play", false ) );
		mainMenu.addGameMenuItem( new GameMenuItem( "About", false ) );
		mainMenu.addGameMenuItem( new GameMenuItem( "Quit", false ) );
	}
	
	public void cleanup()
	{
		System.out.println( "MainMenuState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		if ( !pause )
			mainMenu.drawMenu( g );
	}

	public void init()
	{
		System.out.println( "MainMenuState.init()" );
	}

	public void buttonPressed( ButtonEvent e )
	{
		if ( e.getButton() == ButtonEvent.Button.UP )
		{
			mainMenu.selectUp();
		}
		
		else if ( e.getButton() == ButtonEvent.Button.DOWN )
		{
			mainMenu.selectDown();
		}
		
		else if ( e.getButton() == ButtonEvent.Button.ACTION )
		{
			String item = mainMenu.getSelectedMenuItemName();
			
			if ( item.equals( "New game" ) )
				facade.pushState( new NewGameState( facade, offsetY ) );
			
			else if ( item.equals( "Options" ) )
				facade.pushState( new OptionsMenuState( facade, fontSize, offsetY ) );
			
			else if ( item.equals( "How to play" ) )
				facade.pushState( new MessageState( facade, getHowToPlayMessage(), 24, offsetY ) );
			
			else if ( item.equals( "About" ) )
				facade.pushState( new MessageState( facade, getAboutMessage(), 24, offsetY ) );
			
			else if ( item.equals( "Quit" ) )
				System.exit( 0 );
		}
	}
	
	private String getAboutMessage()
	{
		String about = Constants.APP_NAME + " v" + Constants.APP_VERSION;
		about += "\nCopyright " + Constants.APP_COPYRIGHT_YEARS + " " + Constants.AUTHOR_NAME;
		about += "\n" + Constants.AUTHOR_EMAIL;
		about += "\n" + Constants.AUTHOR_WEB;
		about += "\n\nSource code available under the";
		about += "\n" + Constants.APP_LICENSE + ". See " + Constants.APP_README_FILE + " for details.";
		
		return about;
	}
	
	private String getHowToPlayMessage()
	{
		String howto = "Menu: escape";
		howto += "\nMove: arrow keys";
		howto += "\nJump: space";
		howto += "\nShoot: control";
		
		return howto;
	}
	
	public void buttonReleased( ButtonEvent e )
	{

	}

	public void pause()
	{
		System.out.println( "MainMenuState.pause()" );
		
		pause = true;
	}

	public void resume()
	{
		System.out.println( "MainMenuState.resume()" );
		
		pause = false;
	}

	public void update( long fpsTime )
	{

	}
}

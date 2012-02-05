
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

public class OptionsMenuState implements State
{
	private GameMenu optionsMenu;
	private GameFacade facade;
	private boolean pause;
	private int offsetY, fontSize;

	public OptionsMenuState( GameFacade facade, int fontSize, int offsetY )
	{
		this.facade = facade;
		this.offsetY = offsetY;
		this.fontSize = fontSize;
		optionsMenu = new GameMenu( "Options menu", fontSize, offsetY );

		String fs = "Fullscreen: Off";
		String fps = "Show FPS: Off";
		String capFPS = "Cap FPS: Off";

		if ( facade.getSettings().isFullScreen() )
			fs = "Fullscreen: On";

		if ( facade.getSettings().isFPS() )
			fps = "Show FPS: On";

		if ( facade.getSettings().isCapFPS() )
			capFPS = "Cap FPS: On";

		optionsMenu.addGameMenuItem( new GameMenuItem( fs, true ) );
		optionsMenu.addGameMenuItem( new GameMenuItem( fps, false ) );
		optionsMenu.addGameMenuItem( new GameMenuItem( capFPS, false ) );
		optionsMenu.addGameMenuItem( new GameMenuItem( "Configure gamepad", false ) );
		optionsMenu.addGameMenuItem( new GameMenuItem( "Back", false ) );
	}

	public void cleanup()
	{
		System.out.println( "OptionsMenuState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		if ( !pause )
			optionsMenu.drawMenu( g );
	}

	public void init()
	{
		System.out.println( "OptionsMenuState.init()" );
	}

	public void buttonPressed( ButtonEvent e )
	{
		if ( e.getButton() == ButtonEvent.Button.UP )
		{
			optionsMenu.selectUp();
		}

		else if ( e.getButton() == ButtonEvent.Button.DOWN )
		{
			optionsMenu.selectDown();
		}

		else if ( e.getButton() == ButtonEvent.Button.ACTION )
		{
			String item = optionsMenu.getSelectedMenuItemName();
			GameMenuItem gmi = optionsMenu.getSelectedMenuItem();

			if ( item.equals( "Back" ) )
				facade.popState();

			else if ( item.equals( "Configure gamepad" ) )
			{
				if ( facade.isGamepadDetected() )
					facade.pushState( new ConfigureButtonsMenuState( facade, fontSize, offsetY ) );

				else
				{
					String msg = "No gamepad detected.";
					msg += "\n\nThe gamepad will not be";
					msg += "\ndetected if it was plugged in";
					msg += "\nafter starting the game.";

					facade.pushState( new MessageState( facade, msg, 24, offsetY ) );
				}
			}

			else if ( item.equals( "Fullscreen: Off" ) )
			{
				if ( !facade.setFullScreenMode() )
				{
					String osname = System.getProperty( "os.name" );
					double javaver = 0.0;

					try
					{
						javaver = Double.parseDouble( System.getProperty( "java.version" ).substring( 0, 3 ) );
					}

					catch ( NumberFormatException e1 ) {}

					String msg = "Your system did not support fullscreen.";
					msg += "\n\nJava version: " + javaver + "\nOperating System: " + osname;

					if ( javaver < 1.6 && osname.equalsIgnoreCase( "linux" ) )
						msg += "\nFix: upgrade to Java 1.6";

					facade.pushState( new MessageState( facade, msg, 24, offsetY ) );
				}

				else
					gmi.setName( "Fullscreen: On" );
			}

			else if ( item.equals( "Fullscreen: On" ) )
			{
				gmi.setName( "Fullscreen: Off" );
				facade.setWindowedMode();
			}

			else if ( item.equals( "Show FPS: Off" ) )
			{
				gmi.setName( "Show FPS: On" );
				facade.getSettings().setFPS( true );
			}

			else if ( item.equals( "Show FPS: On" ) )
			{
				gmi.setName( "Show FPS: Off" );
				facade.getSettings().setFPS( false );
			}

			else if ( item.equals( "Cap FPS: Off" ) )
			{
				gmi.setName( "Cap FPS: On" );
				facade.getSettings().setCapFPS( true );
			}

			else if ( item.equals( "Cap FPS: On" ) )
			{
				gmi.setName( "Cap FPS: Off" );
				facade.getSettings().setCapFPS( false );
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
		System.out.println( "OptionsMenuState.pause()" );

		pause = true;
	}

	public void resume()
	{
		System.out.println( "OptionsMenuState.resume()" );

		pause = false;
	}

	public void update( long fpsTime )
	{

	}
}

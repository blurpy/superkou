
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
import net.usikkert.superkou.Tools;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.gui.MessageBox;
import net.usikkert.superkou.input.ButtonEvent;

public class GameFinishedState implements State
{
	private Image gameFinishedImage;
	private GameFacade facade;
	private MessageBox msgBox;
	private int fontSize;
	
	public GameFinishedState( GameFacade facade )
	{
		this.facade = facade;
		fontSize = 28;
		msgBox = new MessageBox();
		gameFinishedImage = Tools.getImage( "graphics/congrats.png" );
	}
	
	public void cleanup()
	{
		System.out.println( "GameFinishedState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		g.drawImage( gameFinishedImage, 0, 0, null );
		msgBox.drawMessage( g, "Wow, you saved the world!\nHave a banana!", fontSize, 70, 20 );
	}

	public void init()
	{
		System.out.println( "GameFinishedState.init()" );
	}

	public void buttonPressed( ButtonEvent e )
	{
		if ( e.getButton() == ButtonEvent.Button.ACTION )
		{
			facade.changeState( new MainScreenState( facade ) );
		}
	}
	
	public void buttonReleased( ButtonEvent e )
	{

	}

	public void pause()
	{
		System.out.println( "GameFinishedState.pause()" );
	}

	public void resume()
	{
		System.out.println( "GameFinishedState.resume()" );
	}

	public void update( long fpsTime )
	{

	}
}

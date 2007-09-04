
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
import net.usikkert.superkou.input.ButtonEvent;

public class GameOverState implements State
{
	private Image gameOverImage;
	private GameFacade facade;
	
	public GameOverState( GameFacade facade )
	{
		this.facade = facade;
		gameOverImage = Tools.getImage( "graphics/gameover.png" );
	}
	
	public void cleanup()
	{
		System.out.println( "GameOverState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		g.drawImage( gameOverImage, 0, 0, null );
	}

	public void init()
	{
		System.out.println( "GameOverState.init()" );
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
		System.out.println( "GameOverState.pause()" );
	}

	public void resume()
	{
		System.out.println( "GameOverState.resume()" );
	}

	public void update( long fpsTime )
	{

	}
}

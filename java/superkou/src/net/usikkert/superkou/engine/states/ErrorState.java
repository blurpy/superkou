
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
import net.usikkert.superkou.gui.MessageBox;
import net.usikkert.superkou.input.ButtonEvent;

public class ErrorState implements State
{
	private GameFacade facade;
	private MessageBox msgBox;
	private String error;
	
	public ErrorState( GameFacade facade, String error )
	{
		this.facade = facade;
		this.error = error;
		msgBox = new MessageBox();
	}
	
	public void cleanup()
	{
		System.out.println( "ErrorState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		g.clearRect( 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );
		msgBox.drawMessage( g, error, 30, 0, 0 );
	}

	public void init()
	{
		System.out.println( "ErrorState.init()" );
	}

	public void pause()
	{
		System.out.println( "ErrorState.pause()" );
	}

	public void resume()
	{
		System.out.println( "ErrorState.resume()" );
	}

	public void update( long fpsTime )
	{

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
}

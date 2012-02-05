
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
import net.usikkert.superkou.gui.MessageBox;
import net.usikkert.superkou.input.ButtonEvent;

public class MessageState implements State
{
	private GameFacade facade;
	private MessageBox msgBox;
	private String msg;
	private int fontSize, offsetY;

	public MessageState( GameFacade facade, String msg, int fontSize, int offsetY )
	{
		this.facade = facade;
		this.msg = msg;
		this.fontSize = fontSize;
		this.offsetY = offsetY;

		msgBox = new MessageBox();
	}

	public void cleanup()
	{
		System.out.println( "MessageState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		msgBox.drawMessage( g, msg, fontSize, 0, offsetY );
	}

	public void init()
	{
		System.out.println( "MessageState.init()" );
	}

	public void pause()
	{
		System.out.println( "MessageState.pause()" );
	}

	public void resume()
	{
		System.out.println( "MessageState.resume()" );
	}

	public void update( long fpsTime )
	{

	}

	public void buttonPressed( ButtonEvent e )
	{
		if ( e.getButton() == ButtonEvent.Button.ACTION || e.getButton() == ButtonEvent.Button.MENU )
		{
			facade.popState();
		}
	}

	public void buttonReleased( ButtonEvent e )
	{

	}
}

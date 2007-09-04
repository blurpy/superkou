
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

package net.usikkert.superkou.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.usikkert.superkou.engine.GameEngine;

public class ButtonHandler implements KeyListener, GamepadListener
{
	private GameEngine engine;
	private GamepadConfig gpConf;
	
	public ButtonHandler( GameEngine engine, GamepadConfig gpConf )
	{
		this.engine = engine;
		this.gpConf = gpConf;
	}
	
	public void keyTyped( KeyEvent e ) {}
	
	public void keyPressed( KeyEvent e )
	{
		engine.buttonPressed( decodeKeyEvent( e ) );
	}

	public void keyReleased( KeyEvent e )
	{
		engine.buttonReleased( decodeKeyEvent( e ) );
	}
	
	public void buttonPressed( GamepadEvent e )
	{
		engine.buttonPressed( decodeGamepadEvent( e ) );
	}

	public void buttonReleased( GamepadEvent e )
	{
		engine.buttonReleased( decodeGamepadEvent( e ) );
	}
	
	private ButtonEvent decodeKeyEvent( KeyEvent e )
	{
		ButtonEvent.Button button = ButtonEvent.Button.UNKNOWN;
		
		int keyCode = e.getKeyCode();
		
		switch ( keyCode )
		{
			case KeyEvent.VK_LEFT	: button = ButtonEvent.Button.LEFT;
									  break;
			case KeyEvent.VK_RIGHT	: button = ButtonEvent.Button.RIGHT;
									  break;
			case KeyEvent.VK_SPACE	: button = ButtonEvent.Button.JUMP;
									  break;
			case KeyEvent.VK_CONTROL	: button = ButtonEvent.Button.SHOOT;
									  break;
			case KeyEvent.VK_UP		: button = ButtonEvent.Button.UP;
									  break;
			case KeyEvent.VK_DOWN	: button = ButtonEvent.Button.DOWN;
									  break;
			case KeyEvent.VK_ESCAPE : button = ButtonEvent.Button.MENU;
									  break;
			case KeyEvent.VK_ENTER	: button = ButtonEvent.Button.ACTION;
									  break;
			default					: break;
		}
		
		return new ButtonEvent( e, button );
	}
	
	private ButtonEvent decodeGamepadEvent( GamepadEvent e )
	{
		ButtonEvent.Button button = ButtonEvent.Button.UNKNOWN;
		
		int buttonID = e.getComponentID();
		float buttonValue = e.getPollData();
		
		if ( buttonID == gpConf.getLeft().getId() && ( buttonValue == gpConf.getLeft().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.LEFT;
		else if ( buttonID == gpConf.getRight().getId() && ( buttonValue == gpConf.getRight().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.RIGHT;
		else if ( buttonID == gpConf.getJump().getId() && ( buttonValue == gpConf.getJump().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.JUMP;
		else if ( buttonID == gpConf.getShoot().getId() && ( buttonValue == gpConf.getShoot().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.SHOOT;
		else if ( buttonID == gpConf.getUp().getId() && ( buttonValue == gpConf.getUp().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.UP;
		else if ( buttonID == gpConf.getDown().getId() && ( buttonValue == gpConf.getDown().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.DOWN;
		else if ( buttonID == gpConf.getMenu().getId() && ( buttonValue == gpConf.getMenu().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.MENU;
		else if ( buttonID == gpConf.getAction().getId() && ( buttonValue == gpConf.getAction().getValue() || e.getDeadZone() >= Math.abs( buttonValue ) ) )
			button = ButtonEvent.Button.ACTION;
		
		return new ButtonEvent( e, button );
	}
}

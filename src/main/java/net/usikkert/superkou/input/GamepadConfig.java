
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

package net.usikkert.superkou.input;

public class GamepadConfig
{
	private GamepadButton left, right, up, down, action, menu, shoot, jump;

	public GamepadConfig()
	{
		left = new GamepadButton( "left", 16, -1.0f );
		right = new GamepadButton( "right", 16, 1.0f );
		up = new GamepadButton( "up", 17, -1.0f );
		down = new GamepadButton( "down", 17, 1.0f );
		action = new GamepadButton( "action", 2, 1.0f );
		menu = new GamepadButton( "menu", 3, 1.0f );
		shoot = new GamepadButton( "shoot", 0, 1.0f );
		jump = new GamepadButton( "jump", 1, 1.0f );
	}

	public GamepadButton getAction()
	{
		return action;
	}

	public void setAction( GamepadButton action )
	{
		this.action = action;
	}

	public GamepadButton getDown()
	{
		return down;
	}

	public void setDown( GamepadButton down )
	{
		this.down = down;
	}

	public GamepadButton getJump()
	{
		return jump;
	}

	public void setJump( GamepadButton jump )
	{
		this.jump = jump;
	}

	public GamepadButton getLeft()
	{
		return left;
	}

	public void setLeft( GamepadButton left )
	{
		this.left = left;
	}

	public GamepadButton getMenu()
	{
		return menu;
	}

	public void setMenu( GamepadButton menu )
	{
		this.menu = menu;
	}

	public GamepadButton getRight()
	{
		return right;
	}

	public void setRight( GamepadButton right )
	{
		this.right = right;
	}

	public GamepadButton getShoot()
	{
		return shoot;
	}

	public void setShoot( GamepadButton shoot )
	{
		this.shoot = shoot;
	}

	public GamepadButton getUp()
	{
		return up;
	}

	public void setUp( GamepadButton up )
	{
		this.up = up;
	}
}

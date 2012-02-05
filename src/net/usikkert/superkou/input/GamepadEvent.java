
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

import java.util.EventObject;
import net.java.games.input.Component;

public class GamepadEvent extends EventObject
{
	private Component component;
	private int componentID;
	
	public GamepadEvent( Component component, int componentID )
	{
		super( component );
		this.component = component;
		this.componentID = componentID;
	}
	
	public float getDeadZone()
	{
		return component.getDeadZone();
	}
	
	public String getIdentifier()
	{
		return component.getIdentifier().getName();
	}
	
	public float getPollData()
	{
		return component.getPollData();
	}
	
	public boolean isAnalog()
	{
		return component.isAnalog();
	}
	
	public boolean isRelative()
	{
		return component.isRelative();
	}

	public int getComponentID()
	{
		return componentID;
	}
}

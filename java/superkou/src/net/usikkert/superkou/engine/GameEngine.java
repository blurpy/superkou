
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

package net.usikkert.superkou.engine;

import java.awt.Graphics;
import java.util.Stack;

import net.usikkert.superkou.engine.states.State;
import net.usikkert.superkou.input.ButtonEvent;

public class GameEngine
{
	private Stack<State> states;
	
	public GameEngine()
	{
		states = new Stack<State>();
	}
	
	public void init()
	{
		
	}
	
	public void cleanup()
	{
		while ( !states.empty() )
		{
			states.lastElement().cleanup();
			states.pop();
		}
	}
	
	public void buttonPressed( ButtonEvent e )
	{
		if ( !states.empty() )
			states.lastElement().buttonPressed( e );
	}
	
	public void buttonReleased( ButtonEvent e )
	{
		if ( !states.empty() )
			states.lastElement().buttonReleased( e );
	}
	
	public void update( long fpsTime )
	{
		if ( states.size() == 1 )
			states.lastElement().update( fpsTime );
		
		else if ( states.size() > 1 )
		{
			for ( int i = 0; i < states.size(); i++ )
			{
				states.get( i ).update( fpsTime );
			}
		}
	}
	
	public void draw( Graphics g )
	{
		if ( states.size() == 1 )
			states.lastElement().draw( g );
		
		else if ( states.size() > 1 )
		{
			for ( int i = 0; i < states.size(); i++ )
			{
				states.get( i ).draw( g );
			}
		}
	}
	
	public void changeState( State newState )
	{
		while ( !states.empty() )
		{
			states.lastElement().cleanup();
			states.pop();
		}
		
		states.push( newState );
		newState.init();
	}
	
	public void pushState( State newState )
	{
		if ( !states.empty() )
			states.lastElement().pause();
		
		
		states.push( newState );
		newState.init();
	}
	
	public void popState()
	{
		if ( !states.empty() )
		{
			states.lastElement().cleanup();
			states.pop();
		}
		
		if ( !states.empty() )
			states.lastElement().resume();
	}
}

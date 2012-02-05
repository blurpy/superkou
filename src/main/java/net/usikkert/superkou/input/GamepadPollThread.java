
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

import java.util.*;
import net.java.games.input.*;

public class GamepadPollThread extends Thread
{
	private Controller controller;
	private Component[] components;
	private boolean run;
	private List<GamepadListener> listeners;
	private float[] componentValues;
	private ControllerEnvironment ce;

	public GamepadPollThread()
	{
		ce = ControllerEnvironment.getDefaultEnvironment();
		controller = getGamepad();

		if ( controller != null )
		{
			components = controller.getComponents();
			componentValues = new float[components.length];
			run = true;

			System.out.println( "Using gamepad: " + controller.getName() );
		}

		listeners = new ArrayList<GamepadListener>();
	}

	private Controller getGamepad()
	{
		Controller[] controllers = ce.getControllers();
		Controller gamepad = null;

		for ( int i = 0; i < controllers.length; i++ )
		{
			Controller cont = controllers[i];
			Controller.Type type = cont.getType();

			if ( type == Controller.Type.GAMEPAD || type == Controller.Type.STICK )
			{
				gamepad = cont;
				break;
			}
		}

		return gamepad;
	}

	public void run()
	{
		while ( run )
		{
			if ( controller.poll() )
			{
				for ( int i = 0; i < components.length; i++ )
				{
					Component comp = components[i];

					if ( comp.getPollData() != componentValues[i] )
					{
						if ( comp.getDeadZone() >= Math.abs( comp.getPollData() ) )
							fireButtonReleased( comp, i );
						else
							fireButtonPressed( comp, i );

						componentValues[i] = comp.getPollData();
					}
				}
			}

			else
			{
				run = false;
				System.err.println( "Lost connection to " + controller.getName() + ". Disabling...");
				controller = null;
			}

			try	{ sleep( 50 ); }
			catch ( InterruptedException e ) {}
		}
	}

	private void fireButtonPressed( Component component, int id )
	{
		GamepadEvent event = new GamepadEvent( component, id );

		for ( int i = 0; i < listeners.size(); i++ )
		{
			listeners.get( i ).buttonPressed( event );
		}
	}

	private void fireButtonReleased( Component component, int id )
	{
		GamepadEvent event = new GamepadEvent( component, id );

		for ( int i = 0; i < listeners.size(); i++ )
		{
			listeners.get( i ).buttonReleased( event );
		}
	}

	public void addGamepadListener( GamepadListener listener )
	{
		listeners.add( listener );
	}

	public void removeGamepadListener( GamepadListener listener )
	{
		listeners.remove( listener );
	}

	public void stopGamepadPolling()
	{
		run = false;
	}

	public boolean isGamepadDetected()
	{
		if ( controller == null )
			return false;
		else
			return true;
	}
}

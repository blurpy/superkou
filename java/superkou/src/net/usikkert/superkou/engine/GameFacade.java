
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

import java.awt.image.BufferStrategy;

import net.usikkert.superkou.Settings;
import net.usikkert.superkou.Status;
import net.usikkert.superkou.engine.states.State;
import net.usikkert.superkou.gui.GameWindow;
import net.usikkert.superkou.input.GamepadConfig;
import net.usikkert.superkou.input.GamepadPollThread;

public class GameFacade
{
	private GameEngine engine;
	private GameWindow window;
	private Settings settings;
	private Status status;
	private FPSCounter fpsCounter;
	private GamepadConfig gpConf;
	private GamepadPollThread gamepadPoll;
	
	public GameFacade( GameEngine engine, GameWindow window, Settings settings, Status status, FPSCounter fpsCounter, GamepadPollThread gamepadPoll )
	{
		this.engine = engine;
		this.window = window;
		this.settings = settings;
		this.status = status;
		this.fpsCounter = fpsCounter;
		this.gamepadPoll = gamepadPoll;
		gpConf = new GamepadConfig();
	}
	
	public void changeState( State newState )
	{
		engine.changeState( newState );
	}
	
	public void pushState( State newState )
	{
		engine.pushState( newState );
	}
	
	public void popState()
	{
		engine.popState();
	}
	
	public BufferStrategy getBufferStrategy()
	{
		return window.getBufferStrategy();
	}
	
	public boolean setWindowedMode()
	{
		return window.setWindowedMode();
	}
	
	public boolean setFullScreenMode()
	{
		return window.setFullScreenMode();
	}
	
	public Settings getSettings()
	{
		return settings;
	}

	public Status getStatus()
	{
		return status;
	}

	public FPSCounter getFpsCounter()
	{
		return fpsCounter;
	}

	public GamepadConfig getGamepadConfig()
	{
		return gpConf;
	}
	
	public boolean isGamepadDetected()
	{
		return gamepadPoll.isGamepadDetected();
	}
}

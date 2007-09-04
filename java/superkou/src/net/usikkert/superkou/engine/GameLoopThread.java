
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
import java.awt.image.BufferStrategy;
import net.usikkert.superkou.*;
import net.usikkert.superkou.engine.states.*;
import net.usikkert.superkou.gui.GameWindow;
import net.usikkert.superkou.input.*;

public class GameLoopThread extends Thread implements SecondListener
{
	private boolean run, pause;
	private GameFacade facade;
	private GameEngine engine;
	private FPSCounter fpsCounter;
	private ButtonHandler buttonHandler;
	private int sleepTime;
	private GamepadPollThread gamepadPoll;
	
	public GameLoopThread( GameWindow window, Settings settings )
	{
		engine = new GameEngine();
		fpsCounter = new FPSCounter();
		Status status = new Status();
		gamepadPoll = new GamepadPollThread();
		facade = new GameFacade( engine, window, settings, status, fpsCounter, gamepadPoll );
		buttonHandler = new ButtonHandler( engine, facade.getGamepadConfig() );
		gamepadPoll.addGamepadListener( buttonHandler );
		
		run = true;
	}
	
	public void run()
	{
		gamepadPoll.start();
		engine.init();
		engine.changeState( new LoadGameState( facade ) );
		//engine.changeState( new BenchmarkState( facade ) );
		fpsCounter.init();
		sleepTime = 8;
		fpsCounter.addSecondListener( this );
		
		while ( run )
		{
			fpsCounter.update();
			
			if ( !pause )
			{
				BufferStrategy bufferStrategy = facade.getBufferStrategy();
				Graphics g = bufferStrategy.getDrawGraphics();

				if ( !bufferStrategy.contentsLost() )
				{
					engine.update( fpsCounter.getFPSTime() );
					engine.draw( g );
					
					g.dispose();
					bufferStrategy.show();
				}
			}
			
			try
			{
				if ( facade.getSettings().isCapFPS() )
					sleep( sleepTime );
				else
					sleep( 0 );
			}
			
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
		
		gamepadPoll.stopGamepadPolling();
		fpsCounter.removeSecondListener( this );
		engine.cleanup();
	}

	public void resumeEngine()
	{
		pause = false;
		fpsCounter.setPause( false );
	}

	public void pauseEngine()
	{
		pause = true;
		fpsCounter.setPause( true );
	}
	
	public void stopGameLoop()
	{
		run = false;
		
		while ( isAlive() )
		{
			try
			{
				sleep( 100 );
			}
			
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}

	// Try to keep fps between 60 and 100
	public void tick()
	{
		if ( facade.getSettings().isCapFPS() )
		{
			if ( fpsCounter.getFPS() < 60 )
			{
				if ( sleepTime > 0 )
					sleepTime--;
			}
			
			else if ( fpsCounter.getFPS() > 100 )
			{
				sleepTime++;
			}
		}
	}

	public ButtonHandler getButtonHandler()
	{
		return buttonHandler;
	}
}

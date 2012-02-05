
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

import java.util.*;

public class FPSCounter
{
	private long lastTime;
	private int fps, fpsTime, fpsCounter, secCounter;
	private List<SecondListener> listeners;
	private boolean pause;
	
	public FPSCounter()
	{
		listeners = new ArrayList<SecondListener>();
	}
	
	public void init()
	{
		lastTime = System.currentTimeMillis();
	}
	
	public void update()
	{
		long currentTime = System.currentTimeMillis();
		fpsTime = Math.round( currentTime - lastTime );
		secCounter += fpsTime;
		fpsCounter++;
		
		if ( secCounter >= 1000 )
		{
			secCounter %= 1000;
			fps = fpsCounter;
			fpsCounter = 0;
			
			if ( !pause )
				fireTick();
		}
		
		lastTime = currentTime;
	}

	public int getFPS()
	{
		return fps;
	}

	public int getFPSTime()
	{
		return fpsTime;
	}
	
	private void fireTick()
	{
		for ( int i = 0; i < listeners.size(); i++ )
		{
			listeners.get( i ).tick();
		}
	}
	
	public void addSecondListener( SecondListener sl )
	{
		listeners.add( sl );
	}
	
	public void removeSecondListener( SecondListener sl )
	{
		listeners.remove( sl );
	}

	public boolean isPause()
	{
		return pause;
	}

	public void setPause( boolean pause )
	{
		this.pause = pause;
	}
}

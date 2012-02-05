
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

package net.usikkert.superkou;

public class Settings
{
	private boolean fullScreen, fps, capFPS;

	public Settings()
	{
		fps = true;
		capFPS = true;
	}

	public boolean isFPS()
	{
		return fps;
	}

	public void setFPS( boolean fps )
	{
		this.fps = fps;
	}

	public boolean isFullScreen()
	{
		return fullScreen;
	}

	public void setFullScreen( boolean fullScreen )
	{
		this.fullScreen = fullScreen;
	}

	public boolean isCapFPS()
	{
		return capFPS;
	}

	public void setCapFPS( boolean capFPS )
	{
		this.capFPS = capFPS;
	}
}

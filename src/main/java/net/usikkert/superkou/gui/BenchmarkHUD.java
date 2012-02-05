
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

package net.usikkert.superkou.gui;

import java.awt.*;
import net.usikkert.superkou.engine.GameFacade;

public class BenchmarkHUD
{
	private GameFacade facade;

	public BenchmarkHUD( GameFacade facade )
	{
		this.facade = facade;
	}

	public void drawHUD( Graphics graphics )
	{
		Graphics2D g = (Graphics2D) graphics;
		//g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		g.clearRect( 560, 0, 640, 20 );
		g.setColor( Color.BLACK );
		g.drawString( "fps: " + facade.getFpsCounter().getFPS(), 570, 16 );
	}
}

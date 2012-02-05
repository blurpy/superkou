
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

package net.usikkert.superkou.engine.states;

import java.awt.*;
import net.usikkert.superkou.Tools;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.gui.BenchmarkHUD;
import net.usikkert.superkou.input.ButtonEvent;

public class BenchmarkState implements State
{
	private Image testImage, testImage2, testImage3;
	//private GameFacade facade;
	private BenchmarkHUD hud;

	public BenchmarkState( GameFacade facade )
	{
		//this.facade = facade;
		testImage = Tools.getImage( "graphics/background.png" );
		testImage2 = Tools.getImage( "graphics/placebo.png" );
		testImage3 = Tools.getImage( "graphics/kou_large_cheer3.png" );
		hud = new BenchmarkHUD( facade );
	}

	public void cleanup()
	{
		System.out.println( "BenchmarkState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		g.drawImage( testImage, 0, 0, null );
		g.setColor( new Color( 255, 255, 255, 100 ) );
		g.fillRect( 100, 100, 200, 200 );
		g.drawImage( testImage2, 350, 350, null );
		g.drawImage( testImage3, 0, 0, null );
		hud.drawHUD( g );
	}

	public void init()
	{
		System.out.println( "BenchmarkState.init()" );
	}

	public void buttonPressed( ButtonEvent e )
	{

	}

	public void buttonReleased( ButtonEvent e )
	{

	}

	public void pause()
	{
		System.out.println( "BenchmarkState.pause()" );
	}

	public void resume()
	{
		System.out.println( "BenchmarkState.resume()" );
	}

	public void update( long fpsTime )
	{

	}
}

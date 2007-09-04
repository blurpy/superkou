
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

package net.usikkert.superkou.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import net.usikkert.superkou.*;
import net.usikkert.superkou.engine.GameLoopThread;

public class GameWindow extends Canvas implements WindowListener
{
	private GraphicsDevice gDev;
	private DisplayMode originalDM, gameDM;
	private GameLoopThread gameLoopT;
	private Settings settings;
	private JFrame frame;
	private Cursor hiddenCursor;
	
	public GameWindow()
	{
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			public void run()
			{
				gameLoopT.stopGameLoop();
				System.out.println( "Quit..." );
			}
		} );
		
		frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setTitle( Constants.APP_NAME + "  v" + Constants.APP_VERSION );
		frame.setIconImage( Tools.getImage( "graphics/kou.png" ) );
		
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize( new Dimension( Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT ) );
		panel.setLayout( null );
		setBounds( 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );
		panel.add( this );
		
		setIgnoreRepaint( true );
		
		gDev = getGraphicsConfiguration().getDevice();
		originalDM = gDev.getDisplayMode();
		gameDM = new DisplayMode( Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, originalDM.getBitDepth(), DisplayMode.REFRESH_RATE_UNKNOWN );
		settings = new Settings();
		gameLoopT = new GameLoopThread( this, settings );
		
		hiddenCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB_PRE ), new Point( 0, 0 ), "" );
		
		if ( settings.isFullScreen() )
			setFullScreenMode();
		
		addKeyListener( gameLoopT.getButtonHandler() );
		frame.addWindowListener( this );
		frame.setResizable( false );
		frame.pack();
		frame.setVisible( true );
		
		createBufferStrategy( 2 );
		
		gameLoopT.start();
	}
	
	public boolean setWindowedMode()
	{
		System.out.println( "setWindowedMode()" );
		
		gameLoopT.pauseEngine();
		
		gDev.setDisplayMode( originalDM );
		gDev.setFullScreenWindow( null );
		
		frame.dispose();
		frame.setUndecorated( false );
		frame.pack();
		frame.setVisible( true );
		frame.setCursor( null );
		createBufferStrategy( 2 );
		
		settings.setFullScreen( false );
		gameLoopT.resumeEngine();
		
		requestFocus();
		
		return true;
	}
	
	public boolean setFullScreenMode()
	{
		System.out.println( "setFullScreenMode()" );
		
		boolean success = false;
		
		gameLoopT.pauseEngine();
		
		if ( gDev.isFullScreenSupported() )
		{
			frame.dispose();
			frame.setUndecorated( true );
			frame.setVisible( true );
			frame.setCursor( hiddenCursor );
			
			gDev.setFullScreenWindow( frame );
			
			if ( gDev.isDisplayChangeSupported() )
			{
				DisplayMode[] modes = gDev.getDisplayModes();
				boolean modeExists = false;
				
				for ( int i = 0; i < modes.length; i++ )
				{
					DisplayMode dm = modes[i];
					
					if ( gameDM.getWidth() == dm.getWidth() && gameDM.getHeight() == dm.getHeight() )
						modeExists = true;
				}
				
				if ( modeExists )
				{
					try
					{
						gDev.setDisplayMode( gameDM );
						settings.setFullScreen( true );
						createBufferStrategy( 2 );
						success = true;
					}
					
					catch ( UnsupportedOperationException e )
					{
						System.err.println( e );
					}
					
					catch ( IllegalArgumentException e )
					{
						System.err.println( e );
					}
				}
			}
			
			if ( !success )
			{
				setWindowedMode();
			}
		}
		
		gameLoopT.resumeEngine();
		requestFocus();
		
		return success;
	}
	

	public void windowActivated( WindowEvent arg0 )
	{
		gameLoopT.resumeEngine();
		requestFocus();
	}

	public void windowDeactivated( WindowEvent arg0 )
	{
		gameLoopT.pauseEngine();
	}

	public void windowDeiconified( WindowEvent arg0 )
	{
		requestFocus();
	}
	
	public void windowIconified( WindowEvent arg0 ) {}
	public void windowOpened( WindowEvent arg0 ) {}
	public void windowClosed( WindowEvent arg0 ) {}
	public void windowClosing( WindowEvent arg0 ) {}
}

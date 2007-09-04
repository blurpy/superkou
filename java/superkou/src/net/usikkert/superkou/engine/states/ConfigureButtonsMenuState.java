
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

package net.usikkert.superkou.engine.states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import net.java.games.input.Component;
import net.usikkert.superkou.engine.GameFacade;
import net.usikkert.superkou.gui.*;
import net.usikkert.superkou.input.*;

public class ConfigureButtonsMenuState implements State
{
	private GameFacade facade;
	private GameMenu buttonMenu;
	private GamepadConfig gpConf;
	private boolean pause, chooseButton;
	private GameMenuItem leftGMI, rightGMI, upGMI, downGMI;
	private GameMenuItem menuGMI, actionGMI, shootGMI, jumpGMI;
	private GamepadButton activeButton, tmpButton;
	private MessageBox msgBox;
	private String msg;
	private int offsetY;
	
	public ConfigureButtonsMenuState( GameFacade facade, int fontSize, int offsetY )
	{
		this.facade = facade;
		this.offsetY = offsetY;
		
		msgBox = new MessageBox();
		gpConf = facade.getGamepadConfig();
		buttonMenu = new GameMenu( "Button menu", fontSize, offsetY );
		
		GamepadButton leftB = gpConf.getLeft();
		leftGMI = new GameMenuItem( "Left button: " + leftB.getId() + " (" + leftB.getValue() + ")", true );
		leftGMI.setItem( leftB );
		buttonMenu.addGameMenuItem( leftGMI );
		
		GamepadButton rightB = gpConf.getRight();
		rightGMI = new GameMenuItem( "Right button: " + rightB.getId() + " (" + rightB.getValue() + ")", false );
		rightGMI.setItem( rightB );
		buttonMenu.addGameMenuItem( rightGMI );
		
		GamepadButton upB = gpConf.getUp();
		upGMI = new GameMenuItem( "Up button: " + upB.getId() + " (" + upB.getValue() + ")", false );
		upGMI.setItem( upB );
		buttonMenu.addGameMenuItem( upGMI );
		
		GamepadButton downB = gpConf.getDown();
		downGMI = new GameMenuItem( "Down button: " + downB.getId() + " (" + downB.getValue() + ")", false );
		downGMI.setItem( downB );
		buttonMenu.addGameMenuItem( downGMI );
		
		GamepadButton menuB = gpConf.getMenu();
		menuGMI = new GameMenuItem( "Menu button: " + menuB.getId() + " (" + menuB.getValue() + ")", false );
		menuGMI.setItem( menuB );
		buttonMenu.addGameMenuItem( menuGMI );
		
		GamepadButton actionB = gpConf.getAction();
		actionGMI = new GameMenuItem( "Action button: " + actionB.getId() + " (" + actionB.getValue() + ")", false );
		actionGMI.setItem( actionB );
		buttonMenu.addGameMenuItem( actionGMI );
		
		GamepadButton shootB = gpConf.getShoot();
		shootGMI = new GameMenuItem( "Shoot button: " + shootB.getId() + " (" + shootB.getValue() + ")", false );
		shootGMI.setItem( shootB );
		buttonMenu.addGameMenuItem( shootGMI );
		
		GamepadButton jumpB = gpConf.getJump();
		jumpGMI = new GameMenuItem( "Jump button: " + jumpB.getId() + " (" + jumpB.getValue() + ")", false );
		jumpGMI.setItem( jumpB );
		buttonMenu.addGameMenuItem( jumpGMI );
		
		buttonMenu.addGameMenuItem( new GameMenuItem( "Back", false ) );
	}
	
	public void cleanup()
	{
		System.out.println( "ConfigureButtonsMenuState.cleanup()" );
	}

	public void draw( Graphics g )
	{
		if ( !pause )
		{
			if ( chooseButton )
				msgBox.drawMessage( g, msg, 24, 0, offsetY );
			else
				buttonMenu.drawMenu( g );
		}
	}

	public void init()
	{
		System.out.println( "ConfigureButtonsMenuState.init()" );
	}

	public void buttonPressed( ButtonEvent e )
	{
		if ( chooseButton )
		{
			if ( e.getSource() instanceof GamepadEvent )
			{
				GamepadEvent ge = (GamepadEvent) e.getSource();
				String type = "";
				
				if ( ge.isAnalog() )
					type = "analog axis";
				else
				{
					if ( ge.getIdentifier().equals( Component.Identifier.Axis.POV ) )
						type = "digital pov hat";
					else
						type = "digital button";
				}
				
				tmpButton.setId(  ge.getComponentID() );
				tmpButton.setValue( ge.getPollData() );
				createMsg( type );
			}
			
			else if ( e.getSource() instanceof KeyEvent )
			{
				KeyEvent ke = (KeyEvent) e.getSource();
				
				if ( ke.getKeyCode() == KeyEvent.VK_ENTER )
				{
					checkButtons();
					activeButton.setId( tmpButton.getId() );
					activeButton.setValue( tmpButton.getValue() );
					updateMenu();
					chooseButton = false;
				}
				
				else if ( ke.getKeyCode() == KeyEvent.VK_ESCAPE )
				{
					chooseButton = false;
				}
			}
		}
		
		else
		{
			if ( e.getButton() == ButtonEvent.Button.UP )
			{
				buttonMenu.selectUp();
			}
			
			else if ( e.getButton() == ButtonEvent.Button.DOWN )
			{
				buttonMenu.selectDown();
			}
			
			else if ( e.getButton() == ButtonEvent.Button.ACTION )
			{
				String item = buttonMenu.getSelectedMenuItemName();
				GameMenuItem gmi = buttonMenu.getSelectedMenuItem();
				
				if ( item.equals( "Back" ) )
					facade.popState();
				
				else
				{
					activeButton = (GamepadButton) gmi.getItem();
					tmpButton = new GamepadButton( "tmp", activeButton.getId(), activeButton.getValue() );
					createMsg( "" );
					chooseButton = true;
				}
			}
			
			else if ( e.getButton() == ButtonEvent.Button.MENU )
			{
				facade.popState();
			}
		}
	}
	
	public void buttonReleased( ButtonEvent e )
	{

	}

	public void pause()
	{
		System.out.println( "ConfigureButtonsMenuState.pause()" );
		
		pause = true;
	}

	public void resume()
	{
		System.out.println( "ConfigureButtonsMenuState.resume()" );
		
		pause = false;
	}

	public void update( long fpsTime )
	{

	}
	
	private void updateMenu()
	{
		GamepadButton leftB = (GamepadButton) leftGMI.getItem();
		leftGMI.setName( "Left button: " + leftB.getId() + " (" + leftB.getValue() + ")" );

		GamepadButton rightB = (GamepadButton) rightGMI.getItem();
		rightGMI.setName( "Right button: " + rightB.getId() + " (" + rightB.getValue() + ")" );

		GamepadButton upB = (GamepadButton) upGMI.getItem();
		upGMI.setName( "Up button: " + upB.getId() + " (" + upB.getValue() + ")" );

		GamepadButton downB = (GamepadButton) downGMI.getItem();
		downGMI.setName( "Down button: " + downB.getId() + " (" + downB.getValue() + ")" );

		GamepadButton menuB = (GamepadButton) menuGMI.getItem();
		menuGMI.setName( "Menu button: " + menuB.getId() + " (" + menuB.getValue() + ")" );

		GamepadButton actionB = (GamepadButton) actionGMI.getItem();
		actionGMI.setName( "Action button: " + actionB.getId() + " (" + actionB.getValue() + ")" );

		GamepadButton shootB = (GamepadButton) shootGMI.getItem();
		shootGMI.setName( "Shoot button: " + shootB.getId() + " (" + shootB.getValue() + ")" );

		GamepadButton jumpB = (GamepadButton) jumpGMI.getItem();
		jumpGMI.setName( "Jump button: " + jumpB.getId() + " (" + jumpB.getValue() + ")" );
	}
	
	private void createMsg( String type )
	{
		msg = "Press the button for '" + activeButton.getName() + "' on your gamepad.\nButton ID: <" + tmpButton.getId()
				+ ">, value: <" + tmpButton.getValue() + "> " + type + "\nPress enter to save, or escape to discard.";
	}
	
	private void checkButtons()
	{
		List<GameMenuItem> items = buttonMenu.getMenuItems();
		
		for ( int i = 0; i < items.size(); i++ )
		{
			GamepadButton button = (GamepadButton) items.get( i ).getItem();

			// If the same button is chosen as an existing button, deactivate the existing button
			if ( button != null && button != activeButton && button.getId() == tmpButton.getId() && button.getValue() == tmpButton.getValue() )
			{
				button.setId( -1 );
			}
		}
	}
}

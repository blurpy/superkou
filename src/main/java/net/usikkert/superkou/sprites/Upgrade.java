
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

package net.usikkert.superkou.sprites;

import java.awt.*;
import net.usikkert.superkou.Tools;

public class Upgrade extends PowerUp
{
	public enum Stage { BIG, CRAZY };
	private Stage stage;
	private Animation plantAni, acidAni;

	public Upgrade( int xPos, int yPos )
	{
		super( xPos, yPos );

		stage = Stage.BIG;

		plantAni = new Animation();
		acidAni = new Animation();

		Image imagePlant = Tools.getImage( "graphics/food_plant.png" );
		Image imageAcid = Tools.getImage( "graphics/food_acid.png" );

		plantAni.addFrame( imagePlant, 20000 );
		acidAni.addFrame( imageAcid, 20000 );

		setAnimation( plantAni );
		getRectangle().setBounds( xPos, yPos, imagePlant.getWidth( null ), imagePlant.getHeight( null ) );
	}

	public Stage getStage()
	{
		return stage;
	}

	public void setStage( Stage stage )
	{
		this.stage = stage;

		if ( stage == Stage.BIG )
			setAnimation( plantAni );
		else if ( stage == Stage.CRAZY )
			setAnimation( acidAni );
	}
}

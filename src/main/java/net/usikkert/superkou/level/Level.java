
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

package net.usikkert.superkou.level;

import java.awt.Image;
import java.util.*;
import net.usikkert.superkou.sprites.*;

public class Level
{
	private Image[][] tiles;
	private Image background;
	private int time;
	private String name;
	private Kou kou;
	private List<Sprite> sprites;

	public Level()
	{
		sprites = new ArrayList<Sprite>();
	}

	public Image getBackground()
	{
		return background;
	}

	public void setBackground( Image background )
	{
		this.background = background;
	}

	public Image getTile( int y, int x )
	{
		if ( y < 0 || y >= getHeight() || x < 0 || x >= getWidth( y ) )
			return null;
		else
			return tiles[y][x];
	}

	public void setTiles( Image[][] tiles )
	{
		this.tiles = tiles;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public int getTime()
	{
		return time;
	}

	public void setTime( int time )
	{
		this.time = time;
	}

	public int getWidth()
	{
		return tiles[0].length;
	}

	public int getWidth( int y )
	{
		return tiles[y].length;
	}

	public int getHeight()
	{
		return tiles.length;
	}

	public Kou getKou()
	{
		return kou;
	}

	public void setKou( Kou kou )
	{
		this.kou = kou;
	}

	public void addSprite( Sprite sprite )
	{
		sprites.add( sprite );
	}

	public ListIterator<Sprite> getSprites()
	{
		return sprites.listIterator();
	}
}

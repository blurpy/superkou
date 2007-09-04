
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

package net.usikkert.superkou.level;

import java.awt.Image;
import java.io.*;
import java.util.*;

import net.usikkert.superkou.Constants;
import net.usikkert.superkou.Tools;
import net.usikkert.superkou.sprites.*;

public class LevelParser
{
	private HashMap<Character, Image> tileMap;
	
	public LevelParser()
	{
		tileMap = new HashMap<Character, Image>();
	}
	
	public Level loadLevel( String url ) throws LevelException
	{
		Level level = new Level();
		
		try
		{
			InputStreamReader fileReader = Tools.getTextStream( url );
			BufferedReader buffReader = new BufferedReader( fileReader );
			
			ArrayList<String> content = new ArrayList<String>();
			int levelHeight = 0;
			int levelWidth = 0;
			
			while ( buffReader.ready() )
			{
				String line = buffReader.readLine();

				if ( line.startsWith( "$" ) )
				{
					if ( line.startsWith( "$background" ) )
					{
						String bgurl = line.replace( "$background=", "" );
						level.setBackground( Tools.getImage( bgurl ) );
					}
					
					else if ( line.startsWith( "$name" ) )
					{
						String name = line.replace( "$name=", "" );
						level.setName( name );
					}
					
					else if ( line.startsWith( "$time" ) )
					{
						int time = 0;
						
						try
						{
							time = Integer.parseInt( line.replace( "$time=", "" ) );
						}
						
						catch ( NumberFormatException e )
						{
							e.printStackTrace();
						}
						
						level.setTime( time );
					}
				}
				
				else
				{
					if ( line.length() > levelWidth )
						levelWidth = line.length();
					
					content.add( line );
				}
			}
			
			levelHeight = content.size();
			
			buffReader.close();
			fileReader.close();
			
			Image[][] tiles = new Image[levelHeight][levelWidth];
			
			for ( int y = 0; y < levelHeight; y++ )
			{
				String line = content.get( y );
				
				for ( int x = 0; x < line.length(); x++ )
				{
					tiles[y][x] = findTile( line.charAt( x ), x, y, level );
				}
			}
			
			level.setTiles( tiles );
		}
		
		catch ( IOException e )
		{
			throw new LevelException( e.getMessage() );
		}
		
		catch ( NullPointerException e )
		{
			e.printStackTrace();
			
			throw new LevelException( e.getMessage() );
		}
		
		return level;
	}
	
	private Image findTile( char tileChar, int x, int y, Level level )
	{
		if ( !tileMap.containsKey( tileChar ) )
		{
			switch ( tileChar )
			{
				case 'A' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_misc.png" ) );
						   break;
				case 'B' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_soil.png" ) );
						   break;
				case 'C' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_grass_top.png" ) );
						   break;
				case 'D' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_grass_left.png" ) );
						   break;
				case 'E' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_grass_left_edge.png" ) );
						   break;
				case 'F' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_grass_left_corner.png" ) );
						   break;
				case 'G' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_grass_right.png" ) );
						   break;
				case 'H' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_grass_right_edge.png" ) );
						   break;
				case 'I' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_grass_right_corner.png" ) );
						   break;
				case 'J' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown.png" ) );
						   break;
				case 'K' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_top.png" ) );
						   break;
				case 'L' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_left.png" ) );
						   break;
				case 'M' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right.png" ) );
						   break;
				case 'N' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_bottom.png" ) );
						   break;
				case 'O' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_left_bottom.png" ) );
						   break;
				case 'P' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_corner_right_top.png" ) );
						   break;
				case 'Q' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right_top.png" ) );
						   break;
				case 'R' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_left_top.png" ) );
						   break;
				case 'S' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_corner_left_top.png" ) );
						   break;
				case 'T' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right_top_left.png" ) );
						   break;
				case 'U' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right_top_bottom.png" ) );
						   break;
				case 'V' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_left_top_bottom.png" ) );
						   break;
				case 'W' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_top_bottom.png" ) );
						   break;
				case 'X' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right_top_bottom_left.png" ) );
						   break;
				case 'Y' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right_bottom.png" ) );
						   break;
				case 'Z' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_corner_right_bottom.png" ) );
						   break;
				case '1' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right_corner_top_left.png" ) );
						   break;
				case '2' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_left_corner_top_right.png" ) );
						   break;
				case '3' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_right_left.png" ) );
						   break;
				case '4' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_brown_corner_left_bottom.png" ) );
						   break;
				case '0' : tileMap.put( tileChar, Tools.getImage( "graphics/tile_green.png" ) );
						   break;
				case 'f' : tileMap.put( tileChar, Tools.getImage( "graphics/placebo.png" ) );
						   break;
				case 'x' : tileMap.put( tileChar, Tools.getImage( "graphics/placebo.png" ) );
						   break;
				default  : break;
			}
		}
		
		switch ( tileChar )
		{
			case 's' : level.addSprite( new Spungy( Tools.tileToPixel( x ), Tools.tileToPixel( y ) -13 ) );
					   break;
			case 'o' : level.addSprite( new Cheese( Tools.tileToPixel( x ), Tools.tileToPixel( y ) ) );
					   break;
			case 'k' : level.setKou( new Kou( Tools.tileToPixel( x ), Tools.tileToPixel( y ) ) );
					   break;
			case 'b' : level.addSprite( new Banana( Tools.tileToPixel( x ), Tools.tileToPixel( y ) -21 ) );
					   break;
			case 'f' : addBoxedUpgrade( Tools.tileToPixel( x ), Tools.tileToPixel( y ), level );
					   break;
			case 'g' : level.addSprite( new Goompa( Tools.tileToPixel( x ), Tools.tileToPixel( y ) -32 ) );
					   break;
			case 'x' : addBoxedCheese( Tools.tileToPixel( x ), Tools.tileToPixel( y ), level );
					   break;
			default  : break;
		}
		
		return tileMap.get( tileChar );
	}
	
	private void addBoxedCheese( int xPos, int yPos, Level level )
	{
		Cheese cheese = new Cheese( xPos, yPos - Constants.TILE_SIZE );
		level.addSprite( cheese );
		
		QuestionBox qb = new QuestionBox( xPos, yPos );
		qb.setContents( cheese );
		level.addSprite( qb );
	}
	
	private void addBoxedUpgrade( int xPos, int yPos, Level level )
	{
		Upgrade upgrade = new Upgrade( xPos, yPos - Constants.TILE_SIZE );
		level.addSprite( upgrade );
		
		QuestionBox qb = new QuestionBox( xPos, yPos );
		qb.setContents( upgrade );
		level.addSprite( qb );
	}
}

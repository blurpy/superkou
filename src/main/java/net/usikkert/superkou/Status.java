
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

package net.usikkert.superkou;

public class Status
{
	public enum State { SMALL, LARGE, CRAZY };
	
	private State state;
	private int lives, cheese, enemies, score, level, nextLevel, levelTime;
	
	public Status()
	{
		resetStatus();
	}
	
	public void resetStatus()
	{
		state = State.SMALL;
		lives = 5;
		cheese = 0;
		enemies = 0;
		score = 0;
		level = 0;
		nextLevel = 1;
		levelTime = 0;
	}

	public int getCheese()
	{
		return cheese;
	}
	
	public void setCheese( int cheese )
	{
		this.cheese = cheese;
	}
	
	public void incCheese()
	{
		cheese++;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore( int score )
	{
		this.score = score;
	}
	
	public int getEnemies()
	{
		return enemies;
	}

	public void setEnemies( int enemies )
	{
		this.enemies = enemies;
	}

	public void incEnemies()
	{
		enemies++;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setLevel( int level )
	{
		this.level = level;
	}
	
	public void incLevel()
	{
		level++;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public void setLives( int lives )
	{
		this.lives = lives;
	}
	
	public void incLives()
	{
		lives++;
	}
	
	public void decLives()
	{
		if ( lives > 0 )
			lives--;
	}
	
	public State getState()
	{
		return state;
	}
	
	public void setState( State state )
	{
		this.state = state;
	}

	public int getLevelTime()
	{
		return levelTime;
	}

	public void setLevelTime( int levelTime )
	{
		this.levelTime = levelTime;
	}

	public void decLevelTime()
	{
		if ( levelTime > 0 )
			levelTime--;
	}

	public int getNextLevel()
	{
		return nextLevel;
	}

	public void setNextLevel( int nextLevel )
	{
		this.nextLevel = nextLevel;
	}
	
	public void incNextLevel()
	{
		nextLevel++;
	}
}

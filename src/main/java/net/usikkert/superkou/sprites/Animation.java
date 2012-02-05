
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

import java.awt.Image;
import java.util.ArrayList;

public class Animation
{
	private ArrayList<Frame> frames;
	private int activeFrameNr;
	private long animationLength, animationTime;

	public Animation()
	{
		frames = new ArrayList<Frame>();
	}

	private Animation( ArrayList<Frame> frames, long animationLength )
	{
		this.frames = frames;
		this.animationLength = animationLength;
	}

	public void addFrame( Image frame, long frameDelay )
	{
		animationLength += frameDelay;
		frames.add( new Frame( frame, animationLength ) );
	}

	public Image getFrame()
	{
		return frames.get( activeFrameNr ).frame;
	}

	public void update( long fpsTime )
	{
		animationTime += fpsTime;

		if ( animationTime >= animationLength )
		{
			animationTime = animationTime % animationLength;
			activeFrameNr = 0;
		}

		while ( animationTime > frames.get( activeFrameNr ).animationDoneTime )
		{
			activeFrameNr++;
		}
	}

	@Override
	protected Animation clone()
	{
		return new Animation( frames, animationLength );
	}

	private class Frame
	{
		private Image frame;
		private long animationDoneTime;

		public Frame( Image frame, long animationDoneTime )
		{
			this.frame = frame;
			this.animationDoneTime = animationDoneTime;
		}
	}
}

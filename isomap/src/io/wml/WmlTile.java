/*
 * Copyright (C) 2012-2013 Martin Steiger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.wml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Martin Steiger
 */
public class WmlTile
{
	private Integer pos;
	private String type;
	private Integer layer;
	private WmlImage image;
	private int x, y;
	
	private final List<String> requiredFlags = new ArrayList<>();
	private final List<String> excludingFlags = new ArrayList<>();
	private final List<String> setFlags = new ArrayList<>();

	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * @return the image
	 */
	public WmlImage getImage()
	{
		return image;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setPos(Integer pos)
	{
		this.pos = pos;
	}

	public void setLayer(Integer layer)
	{
		this.layer = layer;
	}

	/**
	 * @param image the image
	 */
	public void setImage(WmlImage image)
	{
		this.image = image;
	}

	/**
	 * @param flags the flags
	 */
	public void addRequiredFlags(Collection<String> flags)
	{
		requiredFlags.addAll(flags);
	}
	/**
	 * @param flags the flags
	 */
	public void addExcludingFlags(Collection<String> flags)
	{
		excludingFlags.addAll(flags);
	}

	/**
	 * @param flags the flags
	 */
	public void addSetFlags(Collection<String> flags)
	{
		setFlags.addAll(flags);
	}

	/**
	 * @return the pos
	 */
	public Integer getPos()
	{
		return pos;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return the layer
	 */
	public Integer getLayer()
	{
		return layer;
	}

	/**
	 * @return the requiredFlags
	 */
	public List<String> getRequiredFlags()
	{
		return Collections.unmodifiableList(requiredFlags);
	}

	/**
	 * @return the excludingFlags
	 */
	public List<String> getExcludingFlags()
	{
		return Collections.unmodifiableList(excludingFlags);
	}

	/**
	 * @return the setFlags
	 */
	public List<String> getSetFlags()
	{
		return Collections.unmodifiableList(setFlags);
	}
	
	
}

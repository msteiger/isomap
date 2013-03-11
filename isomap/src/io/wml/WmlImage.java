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

import java.util.List;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class WmlImage
{
	private int layer;
	private String name;
	private List<Integer> variations;
	private int baseX, baseY;
	private int centerX, centerY;
	
	/**
	 * @return the layer
	 */
	public int getLayer()
	{
		return layer;
	}
	/**
	 * @param layer the layer to set
	 */
	public void setLayer(int layer)
	{
		this.layer = layer;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the variations
	 */
	public List<Integer> getVariations()
	{
		return variations;
	}
	/**
	 * @param variations the variations to set
	 */
	public void setVariations(List<Integer> variations)
	{
		this.variations = variations;
	}

	/**
	 * @param centerX
	 * @param centerY
	 */
	public void setCenter(int centerX, int centerY)
	{
		this.centerX = centerX;
		this.centerY = centerY;
	}

	/**
	 * @param baseX
	 * @param baseY
	 */
	public void setBase(int baseX, int baseY)
	{
		this.baseX = baseX;
		this.baseY = baseY;
	}
	/**
	 * @return the baseX
	 */
	public int getBaseX()
	{
		return baseX;
	}
	/**
	 * @return the baseY
	 */
	public int getBaseY()
	{
		return baseY;
	}
	/**
	 * @return the centerX
	 */
	public int getCenterX()
	{
		return centerX;
	}
	/**
	 * @return the centerY
	 */
	public int getCenterY()
	{
		return centerY;
	}

}

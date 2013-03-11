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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import common.OctDirection;

/**
 * Represents a [terrain_graphics] element
 * @author Martin Steiger
 */
public class WmlTerrainGraphics
{
	private final List<WmlTile> tiles = new ArrayList<>();
	private final List<WmlImage> images = new ArrayList<>();
	
	private List<OctDirection> rotations = new ArrayList<>();
	private int probability = 100;
	private Map<?, ?> map = Collections.emptyMap();

	/**
	 * @return the images
	 */
	public List<WmlImage> getImages()
	{
		return images;
	}

	/**
	 * @param image the image to add
	 */
	public void addImage(WmlImage image)
	{
		images.add(image);
	}

	/**
	 * The probability of the rule (0..100)
	 * @return
	 */
	public int getProbability()
	{
		return probability;
	}

	/**
	 * @param probability the probability of the rule (0..100)
	 */
	public void setProbability(int probability)
	{
		this.probability = probability;
	}

	/**
	 * @param tile the tile to add
	 */
	public void addTile(WmlTile tile)
	{
		tiles.add(tile);
	}

	public List<WmlTile> getTiles()
	{
		return Collections.unmodifiableList(tiles);
	}

	public void setMap(Map<Object, Object> map)
	{
		this.map = map;
	}

	public void setRotations(List<OctDirection> rotations)
	{
		this.rotations = rotations;
	}

	/**
	 * @return the rotations
	 */
	public List<OctDirection> getRotations()
	{
		return rotations;
	}

	/**
	 * @return the map
	 */
	public Map<?, ?> getMap()
	{
		return map;
	}

	
}

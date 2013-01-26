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

package tiles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import terrain.TerrainType;

import com.google.common.base.Predicate;
import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;

import dirs.DiamondDirection;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileSet
{
	private BufferedImage image;
	
	private int tileWidth = 54;
	private int tileHeight = 28;

	private int tileImageWidth = 54;
	private int tileImageHeight = 48;

	private int offX = tileWidth;
	private int offY = tileHeight + 10;

	private HashMultimap<TerrainType, Integer> terrain = HashMultimap.create();
	private Map<Integer, Pattern> patterns = new HashMap<Integer, Pattern>();
	
	public TileSet(BufferedImage image)
	{
		this.image = image;
	}
	
	public void defineTerrain(Range<Integer> range, TerrainType type, Pattern p)
	{
		defineTerrain(range.asSet(DiscreteDomains.integers()), type, p);
	}
	
	public void defineTerrain(Set<Integer> set, TerrainType type, Pattern p)
	{
		for (Integer idx : set)
		{
			defineTerrain(idx, type, p);
		}
	}

	public void defineTerrain(int idx, TerrainType type, Pattern p)
	{
		terrain.put(type, idx);
		patterns.put(idx, p);
	}
	
	public Image getImage(int tile_index)
	{
		return image;
	}

	public int getTileImageX(int tile_index)
	{
		int six = tile_index % 8;
		return six * offX;
	}

	public int getTileImageY(int tile_index)
	{
		int siy = tile_index / 8;
		return siy * offY;
	}
	
	public int getTileImageWidth()
	{
		return tileImageWidth;
	}
	
	public int getTileImageHeight()
	{
		return tileImageHeight;
	}

	public int getTileWidth()
	{
		return tileWidth;
	}
	
	public int getTileHeight()
	{
		return tileHeight;
	}

	public Set<Integer> getIndicesFor(TerrainType type)
	{
		return terrain.get(type);
	}
	
	public Set<Integer> getIndicesFor(TerrainType type, final Pattern pattern)
	{
		Set<Integer> indices = getIndicesFor(type);

		Predicate<Integer> predicate = new Predicate<Integer>()
		{
			@Override
			public boolean apply(Integer input)
			{
				return doesMatch(patterns.get(input), pattern);
			}
		};

		return Sets.filter(indices, predicate);
	}

	/**
	 * @param needPattern
	 * @param tilePattern
	 * @return
	 */
	private boolean doesMatch(Pattern supp, Pattern needed)
	{
		for (DiamondDirection dir : DiamondDirection.values())
		{
			if (supp.get(dir).compareTo(needed.get(dir)) < 0) 
				return false;
		}
		
		return true;
	}
}

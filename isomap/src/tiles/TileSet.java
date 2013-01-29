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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import terrain.TerrainType;
import dirs.OctDirection;

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

	private int overlapX = 0;
	private int overlapY = 10;

	private final List<TileIndexGroup> tigs = new ArrayList<TileIndexGroup>();
	
	public TileSet(BufferedImage image)
	{
		this.image = image;
	}
	
	public void defineTerrain(Set<Integer> set, TerrainType type, Map<OctDirection, TerrainType> borders)
	{
		TileIndexGroup tig = getIndexGroup(type, borders);
		
		for (Integer idx : set)
			tig.addIndex(idx);
	}

	private TileIndexGroup getIndexGroup(TerrainType type, Map<OctDirection, TerrainType> borders) 
	{
		for (TileIndexGroup tig : tigs)
		{
			if (tig.getTerrain() == type)
			{
				if (tig.getBorders().equals(borders))
					return tig;
			}
		}
		
		TileIndexGroup tig = new TileIndexGroup(type, borders);
		
		tigs.add(tig);
				
		return tig;
	}

	public Image getImage(int tile_index)
	{
		return image;
	}

	public int getImageX(int tile_index)
	{
		int six = tile_index % 8;
		return six * (tileImageWidth - overlapX);
	}

	public int getImageY(int tile_index)
	{
		int siy = tile_index / 8;
		return siy * (tileImageHeight - overlapY);
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

	public Set<Integer> getIndicesFor(TerrainType type, final Map<OctDirection, TerrainType> pattern)
	{
		for (TileIndexGroup tig : tigs)
		{
			if (tig.getTerrain() == type)
			{
				if (doesMatch(tig, pattern))
					return tig.getIndices();
			}
		};

		return Collections.singleton(1);
	}

	/**
	 * @param needPattern
	 * @param tilePattern
	 * @return
	 */
	private boolean doesMatch(TileIndexGroup supp, Map<OctDirection, TerrainType> needed)
	{
		for (OctDirection dir : needed.keySet())
		{
			TerrainType ava = supp.getBorder(dir);
			
			if (ava == TerrainType.UNDEFINED)
				continue;
			
			if (ava.compareTo(needed.get(dir)) < 0) 
				return false;
		}
		
		return true;
	}
	
	/**
	 * @return the overlapX
	 */
	public int getOverlapX() 
	{
		return overlapX;
	}

	/**
	 * @param overlapX the overlapX to set
	 */
	public void setOverlapX(int overlapX) 
	{
		this.overlapX = overlapX;
	}

	/**
	 * @return the overlapY
	 */
	public int getOverlapY() 
	{
		return overlapY;
	}

	/**
	 * @param overlapY the overlapY to set
	 */
	public void setOverlapY(int overlapY) 
	{
		this.overlapY = overlapY;
	}

	public List<TileIndexGroup> getIndexGroups() 
	{
		return Collections.unmodifiableList(tigs);
	}
}

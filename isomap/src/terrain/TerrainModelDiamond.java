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

package terrain;

import static terrain.TerrainType.UNDEFINED;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tiles.TileSet;
import dirs.OctDirection;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TerrainModelDiamond
{
	private final TerrainData data;
	private TileSet tileSet;

	/**
	 * @param data the terrain data
	 */
	public TerrainModelDiamond(TerrainData data, TileSet tileSet)
	{
		this.data = data;
		this.tileSet = tileSet;
	}
	
//	private static boolean isEven(int v)
//	{
//		return v % 2 == 0;
//	}

	private static boolean isOdd(int v)
	{
		return v % 2 == 1;
	}

	/**
	 * @return the map height
	 */
	public int getMapHeight()
	{
		return data.getMapHeight();
	}

	/**
	 * @return the map width
	 */
	public int getMapWidth()
	{
		return data.getMapWidth();
	}	
	
	/**
	 * @param x the x coord
	 * @param y the y coord
	 * @return the type or UNDEFINED if x or y are invalid
	 */
	public TerrainType getTerrain(int x, int y)
	{
		// x and y are often invalid map coords.
		if (x < 0 || x >= getMapWidth())
			return UNDEFINED;
	
		if (y < 0 || y >= getMapHeight())
			return UNDEFINED;
		
		return data.getTerrain(x, y);
	}
	
	/**
	 * @param x the x coord.
	 * @param y the y coord.
	 * @return the tile index
	 */
	public Set<Integer> getIndex(int x, int y)
	{
		TerrainType type = getTerrain(x, y);
		Map<OctDirection, TerrainType> pattern = new HashMap<OctDirection, TerrainType>();
		
		for (OctDirection dir : OctDirection.values())
		{
			TerrainType neigh = getNeighborFor(x, y, dir);
			
			if (neigh != UNDEFINED)
				pattern.put(dir, neigh);
		}

		return tileSet.getIndicesFor(type, pattern);
	}
		
	public TerrainType getNeighborFor(int x, int y, OctDirection dir)
	{
		switch (dir)
		{
		case EAST:
			return getTerrain(x + 1, y);
			
		case NORTH:
			return getTerrain(x, y - 1);

		case SOUTH:
			return getTerrain(x, y + 1);
			
		case WEST:
			return getTerrain(x - 1, y);

		case NORTH_EAST:
			if (isOdd(y)) 
				return getTerrain(x + 1, y - 1); else 
				return getTerrain(x, y - 1);
			
		case NORTH_WEST:
			if (isOdd(y))
				return getTerrain(x, y - 1); else
				return getTerrain(x - 1, y - 1);
		case SOUTH_EAST:
			if (isOdd(y))
				return getTerrain(x + 1, y + 1); else
				return getTerrain(x, y + 1);
		case SOUTH_WEST:
			if (isOdd(y))
				return getTerrain(x, y + 1); else
				return getTerrain(x - 1, y + 1);
		}

		return UNDEFINED;
	}

	
}

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import tiles.TileSet;
import dirs.OctDirection;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TerrainModelDiamond
{
	private static final Integer INVALID_INDEX = 0;

	private final GridData<TerrainType> terrainData;
	private final GridData<Integer> indexData;
	private final GridData<Tile> tiles;
	
	private final TileSet tileSet;

	private final Random r = new Random(12345);

	/**
	 * @param data the terrain data
	 */
	public TerrainModelDiamond(GridData<TerrainType> data, TileSet tileSet)
	{
		int width = data.getWidth();
		int height = data.getHeight();

		this.terrainData = data;
		this.indexData = new GridData<Integer>(width, height, INVALID_INDEX);
		this.tiles = new GridData<Tile>(width, height, null);
		this.tileSet = tileSet;

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				tiles.setData(x, y, new Tile(this, x, y));
				updateIndex(x, y);
			}
		}
	}

	public void updateIndex(int x, int y) 
	{
		List<Integer> indices = new ArrayList<Integer>(computeIndices(x, y));

		int oldIndex = getIndex(x, y);
		
		// exclude old index from the set of possible new indices
		int rand = r.nextInt(indices.size() - 1) + 1;		

		// oldPos can be -1 if the old index was not set before
		int oldPos = indices.indexOf(oldIndex);
		
		// but rand is always in [1..size] so the sum is at least 0
		Integer index = indices.get((oldPos + rand) % indices.size());
		
		indexData.setData(x, y, index);
	}
	
	private static boolean isOdd(int v)
	{
		return v % 2 == 1;
	}

	/**
	 * @return the map height
	 */
	public int getMapHeight()
	{
		return terrainData.getHeight();
	}

	/**
	 * @return the map width
	 */
	public int getMapWidth()
	{
		return terrainData.getWidth();
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
		
		return terrainData.getData(x, y);
	}
	
	public int getIndex(int x, int y)
	{
		return indexData.getData(x, y);
	}

	public Tile getTile(int x, int y)
	{
		return tiles.getData(x, y);
	}

	public int getWorldImageX(int x, int y) 
	{
		return getWorldX(x, y) - tileSet.getOverlapX();
	}
	
	public int getWorldX(int x, int y)
	{
		return x * tileSet.getTileWidth() + (y % 2) * tileSet.getTileWidth() / 2;
	}

	public int getWorldY(int x, int y)
	{
		return y * tileSet.getTileHeight() / 2;
	}
	
	public int getWorldImageY(int x, int y)
	{
		return getWorldY(x, y) - tileSet.getOverlapY();
	}

	public int getMapX(int worldX, int worldY)
	{
		int y = getMapY(worldX, worldY);
		return (worldX - (y % 2) * tileSet.getTileWidth() / 2) / tileSet.getTileWidth();
	}

	public int getMapY(int worldX, int worldY)
	{
		return (worldY * 2) / tileSet.getTileHeight();
	}
	
	/**
	 * @param x the x coord.
	 * @param y the y coord.
	 * @return the tile index
	 */
	public Set<Integer> computeIndices(int x, int y)
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

	
	private TerrainType getNeighborFor(int x, int y, OctDirection dir)
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

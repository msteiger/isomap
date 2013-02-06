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

import com.google.common.base.Optional;

import tiles.TileSet;
import dirs.OctDirection;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TerrainModelDiamond
{
	private final GridData<Tile> tiles;
	private final Random r = new Random(12345);

	private final TileSet tileSet;

	private int mapWidth;
	private int mapHeight;


	/**
	 * @param data the terrain data
	 */
	public TerrainModelDiamond(GridData<TerrainType> terrainData, TileSet tileSet)
	{
		mapWidth = terrainData.getWidth();
		mapHeight = terrainData.getHeight();

		this.tiles = new GridData<Tile>(mapWidth, mapHeight, null);
		this.tileSet = tileSet;

		for (int y = 0; y < mapHeight; y++)
		{
			for (int x = 0; x < mapWidth; x++)
			{
				TerrainType terrain = terrainData.getData(x, y);
				Tile tile = new Tile(x, y, terrain);
				tiles.setData(x, y, tile);
			}
		}
		
		for (int y = 0; y < mapHeight; y++)
		{
			for (int x = 0; x < mapWidth; x++)
			{
				int index = computeIndices(x, y).iterator().next();
				tiles.getData(x, y).setIndex(index);
			}
		}
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
		return mapHeight;
	}

	/**
	 * @return the map width
	 */
	public int getMapWidth()
	{
		return mapWidth;
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

	public Optional<Tile> getTileAtWorldPos(int worldX, int worldY)
	{
		double w = tileSet.getTileWidth();
		double h = tileSet.getTileHeight();

		// Origin is at (0, h/2)
		worldY -= h / 2; 

		//     ( w/2 )        ( w/2 )
		// r = (     )    c = (     ) 
		//     ( h/2 )        (-h/2 )
		
		// x = r * w + c * w
		// y = r * h - c * h
		
		// solving for r gives r = y/h + c
		
		// Math.floor() rounds negative values down whereas casting to int rounds them up
		int r = (int) Math.floor(worldX / w + worldY / h); 
		int c = (int) Math.floor(worldX / w - worldY / h);

		// r and c are only one tile edge long
		int x = (int)Math.floor((r + c) / 2.0);
		
		// r - c always >= 0
		int y = r - c;
		
		if (x >= 0 && x < mapWidth &&
			y >= 0 && y < mapHeight)

		{
			return Optional.of(getTile(x, y));
		}
		
		return Optional.absent();
	}

	public void updateIndex(int x, int y) 
	{
		int index = getTile(x, y).getIndex();
		List<Integer> indices = new ArrayList<Integer>(computeIndices(x, y));

		// exclude old index from the set of possible new indices
		int rand = r.nextInt(indices.size() - 1) + 1;		

		// oldPos can be -1 if the old index was not set before
		int oldPos = indices.indexOf(index);
		
		// but rand is always in [1..size] so the sum is at least 0
		index = indices.get((oldPos + rand) % indices.size());
		
		getTile(x, y).setIndex(index);
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

	/**
	 * @param x the x coord
	 * @param y the y coord
	 * @return the type or UNDEFINED if x or y are invalid
	 */
	private TerrainType getTerrain(int x, int y)
	{
		// x and y are often invalid map coords.
		if (x < 0 || x >= getMapWidth())
			return UNDEFINED;
	
		if (y < 0 || y >= getMapHeight())
			return UNDEFINED;
		
		return getTile(x,y).getTerrain();
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

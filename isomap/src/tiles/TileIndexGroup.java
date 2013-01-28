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

import static dirs.OctDirection.EAST;
import static dirs.OctDirection.NORTH;
import static dirs.OctDirection.NORTH_EAST;
import static dirs.OctDirection.NORTH_WEST;
import static dirs.OctDirection.SOUTH;
import static dirs.OctDirection.SOUTH_EAST;
import static dirs.OctDirection.SOUTH_WEST;
import static dirs.OctDirection.WEST;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import common.DefaultValueMap;

import terrain.TerrainType;
import dirs.OctDirection;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileIndexGroup
{
	private Map<OctDirection, TerrainType> map = 
			new DefaultValueMap<OctDirection, TerrainType>(TerrainType.UNDEFINED, 
					new EnumMap<OctDirection, TerrainType>(OctDirection.class));

	private TerrainType terrain;

	private Set<Integer> indices = new HashSet<Integer>();
	
	public TileIndexGroup()
	{
		for (OctDirection dir : OctDirection.values())
		{
			map.put(dir, TerrainType.UNDEFINED);
		}
	}
	
	/**
	 * @param tuple
	 */
	public TileIndexGroup(TerrainType nw, TerrainType ne, TerrainType se, TerrainType sw)
	{
		map.put(NORTH_WEST, nw);
		map.put(SOUTH_WEST, sw);
		map.put(SOUTH_EAST, se);
		map.put(NORTH_EAST, ne);
	}
	
	public void setTerrain(TerrainType terrain)
	{
		this.terrain = terrain;
	}

	/**
	 * @return the terrain
	 */
	public TerrainType getTerrain()
	{
		return terrain;
	}

	public void setBorder(OctDirection dir, TerrainType t)
	{
		map.put(dir, t);
	}
	
	public TerrainType getBorder(OctDirection dir)
	{
		return map.get(dir);
	}

	/**
	 * @param idx
	 */
	public void addIndex(int idx)
	{
		indices.add(idx);
	}
	
	public Map<OctDirection, TerrainType> getBorders()
	{
		return Collections.unmodifiableMap(map); 
	}
	
	public Set<Integer> getIndices()
	{
		return Collections.unmodifiableSet(indices);
	}
}

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import terrain.TerrainType;
import dirs.OctDirection;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileIndexResolver
{
	private List<TileIndexGroup> tigs = new ArrayList<TileIndexGroup>();

	
	public TileIndexResolver(TileSet ts)
	{
		this.tigs = ts.getIndexGroups();
	}
	
	public Set<TileIndex> getOverlaysFor(TerrainType type, Map<OctDirection, TerrainType> pattern)
	{
		Set<TileIndex> set = new HashSet<>();
		
//		Set<TerrainType> differentTypes = new HashSet<>(pattern.values());	// reduce collection to set -> remove duplicates 
		
		for (TileIndexGroup tig : tigs)
		{
			if (tig.getTerrain() == TerrainType.EMPTY)
			{
				if (isOverlayOk(type, pattern, tig))
					return tig.getIndices();
			}
		}
		
		return set;
	}

	/**
	 * @param type
	 * @param pattern
	 * @param tig
	 */
	private boolean isOverlayOk(TerrainType type, Map<OctDirection, TerrainType> pattern, TileIndexGroup tig)
	{
		if (type != TerrainType.SAND)
			return false;
	
		for (OctDirection dir : pattern.keySet())
		{
			TerrainType border = tig.getBorder(dir);

			if (border == TerrainType.UNDEFINED)
				continue;

			if (border == TerrainType.EMPTY && type == pattern.get(dir))
				continue;
			
			if (border != pattern.get(dir))
				return false;
		}

		return true;
	}
	
	public Set<TileIndex> getIndicesFor(TerrainType type, Map<OctDirection, TerrainType> pattern)
	{
		for (TileIndexGroup tig : tigs)
		{
			if (tig.getTerrain() == type)
			{
				if (doesMatch(tig, pattern))
					return tig.getIndices();
			}
		}

		for (TileIndexGroup tig : tigs)
		{
			if (isDefault(tig, type))
				return tig.getIndices();
		}
		
		return Collections.emptySet();
	}

	/**
	 * @param type
	 * @return
	 */
	private boolean isDefault(TileIndexGroup supp, TerrainType type)
	{
		if (supp.getTerrain() != type)
			return false;

		for (OctDirection dir : OctDirection.values())
		{
			TerrainType ava = supp.getBorder(dir);

			if (ava == TerrainType.UNDEFINED)
				continue;

			if (type != ava)
				return false;
		}
		
		return true;
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
			
			if (ava != needed.get(dir)) 
				return false;
		}
		
		return true;
	}
	
}

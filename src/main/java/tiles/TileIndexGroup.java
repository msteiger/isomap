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

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import terrain.TerrainType;

import common.DefaultValueMap;
import common.OctDirection;


/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileIndexGroup
{
    private final Map<OctDirection, TerrainType> map = 
            new DefaultValueMap<OctDirection, TerrainType>(TerrainType.UNDEFINED, 
                    new EnumMap<OctDirection, TerrainType>(OctDirection.class));

    private final TerrainType terrain;

    private final Set<TileIndex> indices = new HashSet<TileIndex>();
    
    /**
     * @param tuple
     */
    public TileIndexGroup(TerrainType terrain, Map<OctDirection, TerrainType> borders)
    {
        map.putAll(borders);
        this.terrain = terrain;
    }

    /**
     * @return the terrain
     */
    public TerrainType getTerrain()
    {
        return terrain;
    }

    public TerrainType getBorder(OctDirection dir)
    {
        return map.get(dir);
    }

    /**
     * @param idx
     */
    public void addIndex(TileIndex idx)
    {
        indices.add(idx);
    }
    
    public Map<OctDirection, TerrainType> getBorders()
    {
        return Collections.unmodifiableMap(map); 
    }
    
    public Set<TileIndex> getIndices()
    {
        return Collections.unmodifiableSet(indices);
    }

    @Override
    public String toString()
    {
        return "TileIndexGroup [" + terrain + ", " + map + ", " + indices + "]";
    }
}

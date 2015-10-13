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

package isomap.tile.image;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import isomap.common.OctDirection;
import isomap.terrain.TerrainType;

/**
 * TODO Type description
 */
public class TileImageGroup {
    private final Map<OctDirection, TerrainType> map = new EnumMap<OctDirection, TerrainType>(OctDirection.class);

    private final TerrainType terrain;

    private final Set<TileImage> indices = new HashSet<TileImage>();

    public TileImageGroup(TerrainType terrain, Map<OctDirection, TerrainType> borders) {
        map.putAll(borders);
        this.terrain = terrain;
    }

    /**
     * @return the terrain
     */
    public TerrainType getTerrain() {
        return terrain;
    }

    public TerrainType getBorder(OctDirection dir) {
        return map.getOrDefault(dir, TerrainType.UNDEFINED);
    }

    /**
     * @param image
     */
    public void addImage(TileImage image) {
        indices.add(image);
    }

    public Map<OctDirection, TerrainType> getBorders() {
        return Collections.unmodifiableMap(map);
    }

    public Collection<TileImage> getImages() {
        return Collections.unmodifiableSet(indices);
    }

    @Override
    public String toString() {
        return "TileIndexGroup [" + terrain + ", " + map + ", " + indices + "]";
    }
}

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import isomap.common.OctDirection;
import isomap.terrain.SimpleTerrainType;
import isomap.terrain.TerrainType;

/**
 * TODO Type description
 */
public class TileIndexResolver {
    private List<TileImageGroup> tigs = new ArrayList<TileImageGroup>();

    public TileIndexResolver(TileSet ts) {
        this.tigs = ts.getIndexGroups();
    }

    public Collection<TileImage> getOverlaysFor(TerrainType type, Map<OctDirection, TerrainType> pattern) {
        Set<TileImage> set = new HashSet<>();

        // Set<TerrainType> differentTypes = new HashSet<>(pattern.values()); //
        // reduce collection to set -> remove duplicates

        for (TileImageGroup tig : tigs) {
            if (isOverlayOk(type, pattern, tig))
                return tig.getImages();
        }

        return set;
    }

    /**
     * @param type
     * @param pattern
     * @param tig
     */
    private boolean isOverlayOk(TerrainType type, Map<OctDirection, TerrainType> pattern, TileImageGroup tig) {
        if (type != SimpleTerrainType.SAND)
            return false;

        for (OctDirection dir : pattern.keySet()) {
            TerrainType border = tig.getBorder(dir);

            if (border == TerrainType.UNDEFINED)
                continue;

            if (type == pattern.get(dir))
                continue;

            if (border != pattern.get(dir))
                return false;
        }

        return true;
    }

    public Collection<TileImage> getIndicesFor(TerrainType type, Map<OctDirection, TerrainType> pattern) {
        for (TileImageGroup tig : tigs) {
            if (tig.getTerrain() == type) {
                if (doesMatch(tig, pattern)) {
                    return tig.getImages();
                }
            }
        }

        for (TileImageGroup tig : tigs) {
            if (isDefault(tig, type))
                return tig.getImages();
        }

        return Collections.emptySet();
    }

    /**
     * @param type
     * @return
     */
    private boolean isDefault(TileImageGroup supp, TerrainType type) {
        if (supp.getTerrain() != type)
            return false;

        for (OctDirection dir : OctDirection.values()) {
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
    private boolean doesMatch(TileImageGroup supp, Map<OctDirection, TerrainType> needed) {
        for (OctDirection dir : needed.keySet()) {
            TerrainType ava = supp.getBorder(dir);

            if (ava == TerrainType.UNDEFINED)
                continue;

            if (ava != needed.get(dir))
                return false;
        }

        return true;
    }

}

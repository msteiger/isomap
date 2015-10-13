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

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import isomap.common.OctDirection;
import isomap.terrain.TerrainType;

/**
 * TODO Type description
 */
public class TileSet {
    private final int tileWidth;
    private final int tileHeight;

    private final Map<TileIndex, TileImagePage> images = new HashMap<>();
    private final Map<Integer, TileIndex> indices = new HashMap<>();
    private final List<TileIndexGroup> tigs = new ArrayList<TileIndexGroup>();

    private TileIndex cursorTileIndex;
    private TileIndex invalidTileIndex;
    private TileIndex gridTileIndex;

    public TileSet(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void addImage(BufferedImage img, int overlapLeft, int overlapTop, int overlapRight, int overlapBottom) {
        TileImagePage tileImage = new TileImagePage(img, tileWidth, tileHeight, overlapLeft, overlapTop, overlapRight,
                overlapBottom);

        int tileCount = tileImage.getTileCount();
        int firstIndex = indices.size();

        for (int i = 0; i < tileCount; i++) {
            TileIndex index = new TileIndex(i + firstIndex, i);
            indices.put(i + firstIndex, index);
            images.put(index, tileImage);
        }
    }

    /**
     * @param tileIndex
     * @return
     */
    public TileImagePage getTileImage(TileIndex tileIndex) {
        return images.get(tileIndex);
    }

    public void defineTerrain(Set<Integer> set, TerrainType type, Map<OctDirection, TerrainType> borders) {
        TileIndexGroup tig = getIndexGroup(type, borders);

        for (Integer idx : set) {
            TileIndex tileIndex = indices.get(idx);
            tig.addIndex(tileIndex);
        }
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public List<TileIndexGroup> getIndexGroups() {
        return Collections.unmodifiableList(tigs);
    }

    private TileIndexGroup getIndexGroup(TerrainType type, Map<OctDirection, TerrainType> borders) {
        for (TileIndexGroup tig : tigs) {
            if (tig.getTerrain() == type) {
                if (tig.getBorders().equals(borders))
                    return tig;
            }
        }

        TileIndexGroup tig = new TileIndexGroup(type, borders);

        tigs.add(tig);

        return tig;
    }

    /**
     * @return
     */
    public TileIndex getCursorTileIndex() {
        return cursorTileIndex;
    }

    /**
     * @return
     */
    public TileIndex getInvalidTileIndex() {
        return invalidTileIndex;
    }

    public void setCursorTileIndex(int idx) {
        cursorTileIndex = indices.get(idx);
    }

    public void setInvalidTileIndex(int idx) {
        invalidTileIndex = indices.get(idx);
    }

    /**
     * @return the gridTileIndex
     */
    public TileIndex getGridTileIndex() {
        return gridTileIndex;
    }

    public void setGridTileIndex(int index) {
        this.gridTileIndex = indices.get(index);
    }

}

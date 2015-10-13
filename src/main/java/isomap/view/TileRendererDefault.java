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

package isomap.view;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import isomap.common.GridData;
import isomap.common.OctDirection;
import isomap.terrain.TerrainType;
import isomap.terrain.Tile;
import isomap.tile.image.IndexProvider;
import isomap.tile.image.TileImage;
import isomap.tile.image.TileIndex;
import isomap.tile.image.TileSet;
import isomap.tile.model.TileModel;

/**
 * TODO Type description
 */
public class TileRendererDefault extends AbstractTileRenderer {
    private IndexProvider indexProvider;

    /**
     * @param terrainModel
     * @param view2
     */
    public TileRendererDefault(GridData<TerrainType> terrainData, TileModel terrainModel, TileSet tileset) {
        super(terrainData, terrainModel, tileset);

        this.indexProvider = new IndexProvider(terrainData, terrainModel, tileset);
    }

    public void drawTiles(Graphics2D g, List<Tile> visibleTiles) {
        for (Tile tile : visibleTiles) {
            int mapY = tile.getMapY();
            int mapX = tile.getMapX();

            TileIndex currIndex = indexProvider.getCurrentIndex(mapX, mapY);
            TileImage img = tileset.getTileImage(currIndex).getImage(currIndex);
            drawTile(g, img, mapX, mapY);

            Map<OctDirection, Tile> neighbors = getTerrainModel().getNeighbors(mapX, mapY);
            Map<OctDirection, TerrainType> terrains = new HashMap<>();
            for (OctDirection dir : neighbors.keySet()) {
                Tile n = neighbors.get(dir);
                TerrainType type = terrainData.getData(n.getMapX(), n.getMapY());
                terrains.put(dir, type);
            }
        }
    }

}

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

package view;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.GridData;
import common.OctDirection;
import terrain.TileModel;
import terrain.TerrainType;
import terrain.Tile;
import tiles.IndexProvider;
import tiles.TileIndex;
import tiles.TileSet;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileRendererDefault extends AbstractTileRenderer
{
    private IndexProvider indexProvider;

    /**
     * @param terrainModel
     * @param view2
     */
    public TileRendererDefault(GridData<TerrainType> terrainData, TileModel terrainModel, TileSet tileset)
    {
        super(terrainData, terrainModel, tileset);

        this.indexProvider = new IndexProvider(terrainData, terrainModel, tileset);
    }

    public void drawTiles(Graphics2D g, List<Tile> visibleTiles)
    {
        for (Tile tile : visibleTiles)
        {
            int mapY = tile.getMapY();
            int mapX = tile.getMapX();

            TileIndex currIndex = indexProvider.getCurrentIndex(mapX, mapY);
            drawTile(g, currIndex, mapX, mapY);

            Map<OctDirection, Tile> neighbors = getTerrainModel().getNeighbors(mapX, mapY);
            Map<OctDirection, TerrainType> terrains = new HashMap<>();
            for (OctDirection dir : neighbors.keySet()) {
                Tile n = neighbors.get(dir);
                TerrainType type = terrainData.getData(n.getMapX(), n.getMapY());
                terrains.put(dir, type);
            }
            Set<TileIndex> indices = indexProvider.getOverlaysFor(terrainData.getData(tile.getMapX(), tile.getMapY()), terrains);

            for (TileIndex overlay : indices)
            {
                drawTile(g, overlay, mapX, mapY);
            }
        }
    }

}

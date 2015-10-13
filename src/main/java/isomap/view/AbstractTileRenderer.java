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

import isomap.common.GridData;
import isomap.terrain.TerrainType;
import isomap.tile.image.TileImage;
import isomap.tile.image.TileImagePage;
import isomap.tile.image.TileIndex;
import isomap.tile.image.TileSet;
import isomap.tile.model.TileModel;

/**
 * TODO Type description
 */
public abstract class AbstractTileRenderer {
    protected final TileModel terrainModel;
    protected final TileSet tileset;
    protected final GridData<TerrainType> terrainData;

    public AbstractTileRenderer(GridData<TerrainType> terrainData, TileModel terrainModel, TileSet tileset) {
        this.terrainData = terrainData;
        this.terrainModel = terrainModel;
        this.tileset = tileset;
    }

    protected void drawTile(Graphics2D g, TileImage simg, int mapX, int mapY) {

        int worldX = terrainModel.getWorldX(mapX, mapY) + simg.getOffsetX();
        int worldY = terrainModel.getWorldY(mapX, mapY) + simg.getOffsetY();

        g.drawImage(simg.getImage(), worldX, worldY, null);
    }

    /**
     * @return the terrainModel
     */
    protected TileModel getTerrainModel() {
        return terrainModel;
    }

    /**
     * @return the tileset
     */
    protected TileSet getTileset() {
        return tileset;
    }

    /**
     * @return the terrainData
     */
    public GridData<TerrainType> getTerrainData() {
        return terrainData;
    }
}

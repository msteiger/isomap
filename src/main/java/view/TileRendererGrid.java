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
import java.util.List;

import terrain.TileModel;
import terrain.Tile;
import tiles.TileSet;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileRendererGrid extends AbstractTileRenderer
{
    /**
     * @param gridTile the grid tile index
     */
    public TileRendererGrid(TileModel terrainModel, TileSet tileset)
    {
        super(null, terrainModel, tileset);
    }

    public void drawTiles(Graphics2D g, List<Tile> visibleTiles)
    {
        for (Tile tile : visibleTiles)
        {
            int mapY = tile.getMapY();
            int mapX = tile.getMapX();

            drawTile(g, getTileset().getGridTileIndex(), mapX, mapY);
        }
    }

}

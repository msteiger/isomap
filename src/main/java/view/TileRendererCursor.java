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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Collection;

import terrain.TerrainModel;
import terrain.Tile;
import tiles.TileSet;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileRendererCursor extends AbstractTileRenderer
{
    /**
     * @param gridTile the grid tile index
     */
    public TileRendererCursor(TerrainModel terrainModel, TileSet tileset)
    {
        super(terrainModel, tileset);
    }

    public void drawTiles(Graphics2D g, Collection<Tile> hoveredTiles)
    {
        TileSet tileset = getTileset();
        TerrainModel terrainModel = getTerrainModel();
        
        int imgWidth = tileset.getTileWidth();
        int imgHeight = tileset.getTileHeight();

        float size = 12.0f;
        g.setFont(g.getFont().deriveFont(size));
        for (Tile tile : hoveredTiles)
        {
            int mapX = tile.getMapX();
            int mapY = tile.getMapY();

            drawTile(g, tileset.getCursorTileIndex(), mapX, mapY);
            
            int worldX = terrainModel.getWorldX(mapX, mapY);
            int worldY = terrainModel.getWorldY(mapX, mapY);

            g.setColor(Color.WHITE);
            FontMetrics fm = g.getFontMetrics();
            
            String str = String.format("%d / %d", mapX, mapY);
        
            int tx = worldX + (imgWidth - fm.stringWidth(str)) / 2;
            int ty = worldY + (imgHeight + fm.getAscent()) / 2;
            g.drawString(str, tx, ty);
        }

    }
}

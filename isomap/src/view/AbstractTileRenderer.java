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

import terrain.TerrainModel;
import tiles.TileImage;
import tiles.TileIndex;
import tiles.TileSet;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public abstract class AbstractTileRenderer
{
	private final TerrainModel terrainModel;
	private final TileSet tileset;

	/**
	 * @param terrainModel
	 * @param tileset
	 */
	public AbstractTileRenderer(TerrainModel terrainModel, TileSet tileset)
	{
		this.terrainModel = terrainModel;
		this.tileset = tileset;
	}

	protected void drawTile(Graphics2D g, TileIndex tileIndex, int mapX, int mapY)
	{
		TileImage img = tileset.getTileImage(tileIndex);
		
		int imgWidth = img.getTileImageWidth();
		int imgHeight = img.getTileImageHeight();

		int sx1 = img.getTileImageX(tileIndex);
		int sy1 = img.getTileImageY(tileIndex);
		int sx2 = sx1 + imgWidth;
		int sy2 = sy1 + imgHeight;
		
		int offX = img.getOverlapLeft();
		int offY = img.getOverlapTop();

		int worldX = terrainModel.getWorldX(mapX, mapY) - offX;
		int worldY = terrainModel.getWorldY(mapX, mapY) - offY;

		g.drawImage(img.getImage(), worldX, worldY, worldX + imgWidth, worldY + imgHeight, sx1, sy1, sx2, sy2, null);
	}

	/**
	 * @return the terrainModel
	 */
	protected TerrainModel getTerrainModel()
	{
		return terrainModel;
	}

	/**
	 * @return the tileset
	 */
	protected TileSet getTileset()
	{
		return tileset;
	}

	
}

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

package view.drools;

import java.awt.Graphics2D;
import java.util.List;

import terrain.TerrainModel;
import terrain.Tile;
import tiles.TileIndex;
import view.AbstractTileRenderer;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileRendererDrools extends AbstractTileRenderer
{
	private IndexProviderDrools indexProvider;

	/**
	 * @param terrainModel
	 * @param view2
	 */
	public TileRendererDrools(TerrainModel terrainModel, TileSetBuilderWesnoth tb)
	{
		super(terrainModel, tb.getTileSet());
		
		this.indexProvider = new IndexProviderDrools(terrainModel, tb);
	}
	
	public void drawTiles(Graphics2D g, List<Tile> visibleTiles)
	{
		for (Tile tile : visibleTiles)
		{
			int mapY = tile.getMapY();
			int mapX = tile.getMapX();

			List<TileIndex> indices = indexProvider.getIndices(mapX, mapY); 
			
			for (TileIndex overlay : indices)
			{
				drawTile(g, overlay, mapX, mapY);
			}
		}
	}

}

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

package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Set;

import pack.Viewport;

import terrain.TerrainModelDiamond;
import terrain.Tile;

/**
 * TODO Type description
 */
public class TilemapMouseAdapter implements MouseMotionListener
{
	private final Viewport view;
	private final TerrainModelDiamond terrainModel;
	private final Set<Tile> selectionModel;

	/**
	 * @param view
	 * @param terrainModel
	 * @param selectionModel
	 */
	public TilemapMouseAdapter(Viewport view, TerrainModelDiamond terrainModel, Set<Tile> selectionModel)
	{
		this.view = view;
		this.terrainModel = terrainModel;
		this.selectionModel = selectionModel;
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		selectionModel.clear();

		int worldX = view.screenXToWorldX(e.getX());
		int worldY = view.screenYToWorldY(e.getY());
		
		int mapX = terrainModel.getMapX(worldX, worldY);
		int mapY = terrainModel.getMapY(worldX, worldY);
	
		if (mapX >= 0 && mapX < terrainModel.getMapWidth() &&
			mapY >= 0 && mapY < terrainModel.getMapHeight())
		{
			Tile tile = terrainModel.getTile(mapX, mapY);
			selectionModel.add(tile);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	
}
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

import com.google.common.base.Optional;

import pack.Viewport;

import terrain.TerrainModel;
import terrain.Tile;

/**
 * TODO Type description
 */
public class TilemapMouseAdapter implements MouseMotionListener
{
	private final Viewport view;
	private final TerrainModel terrainModel;
	private final Set<Tile> selectionModel;

	/**
	 * @param view
	 * @param terrainModel
	 * @param selectionModel
	 */
	public TilemapMouseAdapter(Viewport view, TerrainModel terrainModel, Set<Tile> selectionModel)
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
		
		Optional<Tile> opTile = terrainModel.getTileAtWorldPos(worldX, worldY);

		if (opTile.isPresent())
		{
			selectionModel.add(opTile.get());
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	
}
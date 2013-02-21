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

package main;

import input.TilemapMouseAdapter;
import input.ViewportMouseAdapter;
import io.TerrainLoader;
import io.TileSetBuilderWesnoth;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;

import terrain.HexTerrainModel;
import terrain.TerrainType;
import terrain.Tile;
import tiles.HexTileSet;
import view.TileRenderer;
import view.Viewport;

import common.GridData;
import common.RepaintingObserver;
import common.SelectionModel;

/**
 * A simple component that loads the terrain and the tileset,
 * creates a viewport and renderer and attaches mouse selection listener
 * @author Martin Steiger
 */
public class ExampleComponent extends JComponent
{
	private static final long serialVersionUID = 6701940511292511047L;

	private TileRenderer tileRenderer;

	private HexTerrainModel terrainModel;
	private HexTileSet tileset;

	private Viewport view = new Viewport();
	private SelectionModel<Tile> selectionModel = new SelectionModel<Tile>();

	/**
	 * Setup everything
	 * @throws IOException if some data cannot be read
	 */
	public ExampleComponent() throws IOException
	{
		InputStream terrainDataStream = new FileInputStream("data/example.txt");
		TerrainLoader terrainLoader = new TerrainLoader();
		GridData<TerrainType> terrainData = terrainLoader.load(terrainDataStream);

//		TileSetBuilder tileSetBuilder = new TileSetBuilder();
//		tileset = tileSetBuilder.readFromStream(new FileInputStream("data/treasurefleet.tsd"));

		TileSetBuilderWesnoth tb = new TileSetBuilderWesnoth();
		tileset = tb.read();

		terrainModel = new HexTerrainModel(terrainData, tileset);

		MouseAdapter ma = new ViewportMouseAdapter(view);
		view.addObserver(new RepaintingObserver(this));

		addMouseListener(ma);
		addMouseMotionListener(ma);
		addMouseWheelListener(ma);

		selectionModel.addObserver(new RepaintingObserver(this));
		
		tileRenderer = new TileRenderer(terrainModel, tileset, view);

		TilemapMouseAdapter tma = new TilemapMouseAdapter(view, terrainModel, selectionModel);
		addMouseMotionListener(tma);
	}

	/**
	 * Update tile animation and repaint
	 */
	public void animate()
	{
		tileRenderer.nextFrame();

		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		tileRenderer.drawTileset(g2d, getVisibleRect());
		tileRenderer.drawHoveredTiles(g2d, selectionModel.getSelection());
	}
}

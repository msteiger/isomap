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

package pack;

import input.TilemapMouseAdapter;
import input.ViewportMouseAdapter;
import io.TerrainLoader;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import javax.swing.JComponent;

import terrain.GridData;
import terrain.TerrainModelDiamond;
import terrain.TerrainType;
import terrain.Tile;
import tiles.TileSet;
import tiles.TileSetBuilder;

import common.CollectionListener;
import common.ObservableSet;


/**
 * TODO Type description
 * @author Martin Steiger
 */
public class MyComponent extends JComponent
{
	private static final long serialVersionUID = 6701940511292511047L;
	
	private TileRenderer tileRenderer;
	
	private TerrainModelDiamond terrainModel;
	private TileSet tileset;
	private Viewport view = new Viewport();
	private ObservableSet<Tile> hoveredTiles = new ObservableSet<Tile>(new HashSet<Tile>()); 
	
	/**
	 * 
	 */
	public MyComponent() throws IOException
	{
		InputStream terrainDataStream = new FileInputStream("data/example.txt");
		TerrainLoader terrainLoader = new TerrainLoader();
		GridData<TerrainType> terrainData = terrainLoader.load(terrainDataStream);
		
		TileSetBuilder tileSetBuilder = new TileSetBuilder();
		tileset = tileSetBuilder.readFromStream(new FileInputStream("data/treasurefleet.tsd"));

		terrainModel = new TerrainModelDiamond(terrainData, tileset);
		
		MouseAdapter ma = new ViewportMouseAdapter(view);
		view.addObserver(new RepaintingObserver(this));
		
		addMouseListener(ma);
		addMouseMotionListener(ma);
		addMouseWheelListener(ma);
		
		hoveredTiles.addListener(new CollectionListener<Tile>()
		{
			@Override
			public void added(Tile object)
			{
				repaint();
			}

			@Override
			public void removed(Tile object)
			{
				repaint();
			}
		});
		
		tileRenderer = new TileRenderer(terrainModel, tileset, view);
		
		TilemapMouseAdapter tma = new TilemapMouseAdapter(view, terrainModel, hoveredTiles);
		addMouseMotionListener(tma);
	}

	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		
		tileRenderer.drawTileset(g2d, getVisibleRect());
		tileRenderer.drawHoveredTiles(g2d, hoveredTiles);
	}

	/**
	 * 
	 */
	public void animate()
	{
		tileRenderer.nextFrame();
		
		repaint();
	}

}

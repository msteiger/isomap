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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import javax.swing.JComponent;

import terrain.GridData;
import terrain.TerrainLoader;
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
		
		TilemapMouseAdapter tma = new TilemapMouseAdapter(view, terrainModel, hoveredTiles);
		addMouseMotionListener(tma);
	}

	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		drawTileset(g);
		drawHoveredTile(g);
	}

	/**
	 * @param g
	 */
	private void drawTileset(Graphics g)
	{
		// convert screen to world coordinates
		int worldX0 = view.screenXToWorldX(0);
		int worldY0 = view.screenYToWorldY(0);
		int worldX1 = view.screenXToWorldX(getWidth());
		int worldY1 = view.screenYToWorldY(getHeight());

		// TODO: this part is probably dependent on the TerrainModel, so convert it
		int mapX0 = terrainModel.getMapX(worldX0, worldY0) - 1;
		int mapY0 = terrainModel.getMapY(worldX0, worldY0) - 1;
		int mapX1 = terrainModel.getMapX(worldX1, worldY1) + 1;
		int mapY1 = terrainModel.getMapY(worldX1, worldY1);

		// Restrict to map bounds
		int minX = Math.max(mapX0, 0);
		int minY = Math.max(mapY0, 0);
		int maxX = Math.min(mapX1, terrainModel.getMapWidth() - 1);
		int maxY = Math.min(mapY1, terrainModel.getMapHeight() - 1);

//		System.out.println(minX + "-" + minY + "-" + maxX + "-" + maxY);

		for (int y = minY; y <= maxY; y++)
		{
			for (int x = minX; x <= maxX; x++)
			{
				int source_index = terrainModel.getTile(x, y).getIndex();
				drawTile(g, source_index, x, y);
			}
		}
	}
	
	private void drawHoveredTile(Graphics g)
	{
		g.setFont(g.getFont().deriveFont(9.0f).deriveFont(Font.BOLD));
		for (Tile t : hoveredTiles)
		{
			drawTile(g, 0, t.getMapX(), t.getMapY());

			int mapX = t.getMapX();
			int mapY = t.getMapY();
			
			int worldX = terrainModel.getWorldX(mapX, mapY);
			int worldY = terrainModel.getWorldY(mapX, mapY);

			int dx1 = view.worldXToScreenX(worldX);
			int dy1 = view.worldYToScreenY(worldY);

			g.setColor(Color.WHITE);
			String str = String.format("%d / %d", mapX, mapY);
			int tx = dx1 + 27 - (g.getFontMetrics().stringWidth(str)) / 2;
			int ty = dy1 + 18;
			g.drawString(str, tx, ty);
		}

	}

	private void drawTile(Graphics g, int tileIndex, int x, int y)
	{
		int imgWidth = tileset.getTileImageWidth();
		int imgHeight = tileset.getTileImageHeight();

		Image image = tileset.getImage(tileIndex);

		int sx1 = tileset.getImageX(tileIndex);
		int sy1 = tileset.getImageY(tileIndex);
		int sx2 = sx1 + imgWidth;
		int sy2 = sy1 + imgHeight;
		
		int worldX = terrainModel.getWorldImageX(x, y);
		int worldY = terrainModel.getWorldImageY(x, y);

		int dx1 = view.worldXToScreenX(worldX);
		int dy1 = view.worldYToScreenY(worldY);
		int dx2 = view.worldXToScreenX(worldX + imgWidth);
		int dy2 = view.worldYToScreenY(worldY + imgHeight);

		g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	
	/**
	 * 
	 */
	public void animate()
	{
		for (int y = 0; y < terrainModel.getMapHeight(); y++)
		{
			for (int x = 0; x < terrainModel.getMapWidth(); x++)
			{
				Tile tile = terrainModel.getTile(x, y);
				
				if (tile.getTerrain() == TerrainType.WATER)
				{
					terrainModel.updateIndex(x, y);
				}
			}
		}

		repaint();
	}

}

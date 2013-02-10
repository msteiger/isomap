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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

import javax.swing.JComponent;

import terrain.GridData;
import terrain.TerrainLoader;
import terrain.TerrainModelDiamond;
import terrain.TerrainType;
import terrain.Tile;
import tiles.TileImage;
import tiles.TileIndex;
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
	private GridData<TileIndex> oldIndices;
	private GridData<Integer> animSteps;

	private final static int maxSteps = 8;
	
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
		
		
		int mapWidth = terrainModel.getMapWidth();
		int mapHeight = terrainModel.getMapHeight();
		oldIndices = new GridData<TileIndex>(mapWidth, mapHeight, null);
		animSteps = new GridData<Integer>(mapWidth, mapHeight, 0);
		
		for (int y = 0; y < terrainModel.getMapHeight(); y++)
		{
			for (int x = 0; x < terrainModel.getMapWidth(); x++)
			{
				oldIndices.setData(x, y, terrainModel.getTile(x, y).getIndex());
				terrainModel.updateIndex(x, y);
				animSteps.setData(x, y, (int) (Math.random() * maxSteps));
			}
		}
		
		animate();
		
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

		Graphics2D g2d = (Graphics2D) g;
		drawTileset(g2d);
		drawHoveredTile(g2d);
	}

	/**
	 * @param g
	 */
	private void drawTileset(Graphics2D g)
	{
		// convert screen to world coordinates
		int worldX0 = view.screenXToWorldX(0);
		int worldY0 = view.screenYToWorldY(0);
		int worldX1 = view.screenXToWorldX(getWidth());
		int worldY1 = view.screenYToWorldY(getHeight());

		List<Tile> visibleTiles = terrainModel.getTilesInRect(worldX0, worldY0, worldX1, worldY1);

		// sort?
		
		for (Tile tile : visibleTiles)
		{
			int mapY = tile.getMapY();
			int mapX = tile.getMapX();

			TileIndex oldIndex = oldIndices.getData(mapX, mapY); 
			TileIndex newIndex = tile.getIndex();

			if (oldIndex != null && oldIndex != newIndex)
			{
				drawTile(g, oldIndex, mapX, mapY);
	
	//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	
				float alpha = (float)animSteps.getData(mapX, mapY) / maxSteps;
				Composite oldComp = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				drawTile(g, newIndex, mapX, mapY);
				g.setComposite(oldComp);
			}
			else
			{
				drawTile(g, newIndex, mapX, mapY);
			}
		}
	}
	
	private void drawHoveredTile(Graphics2D g)
	{
		int imgWidth = tileset.getTileWidth();
		int imgHeight = tileset.getTileHeight();

		float size = (float) (9.0 * view.getZoom());
		g.setFont(g.getFont().deriveFont(size));
		for (Tile tile : hoveredTiles)
		{
			int mapX = tile.getMapX();
			int mapY = tile.getMapY();

			drawTile(g, tileset.getCursor(), mapX, mapY);
			
			int worldX = terrainModel.getWorldX(mapX, mapY);
			int worldY = terrainModel.getWorldY(mapX, mapY);

			int dx1 = view.worldXToScreenX(worldX);
			int dy1 = view.worldYToScreenY(worldY);
			int dx2 = view.worldXToScreenX(worldX + imgWidth);
			int dy2 = view.worldYToScreenY(worldY + imgHeight);

			g.setColor(Color.WHITE);
			FontMetrics fm = g.getFontMetrics();
			
			String str = String.format("%d / %d", mapX, mapY);
		
			int tx = (dx2+dx1 - fm.stringWidth(str)) / 2;
			int ty = (dy2+dy1 + fm.getAscent()) / 2;
			g.drawString(str, tx, ty);
		}

	}

	private void drawTile(Graphics2D g, TileIndex tileIndex, int x, int y)
	{
		TileImage img = tileIndex.getTileImage();
		
		int imgWidth = img.getTileImageWidth();
		int imgHeight = img.getTileImageHeight();

		int sx1 = img.getTileImageX(tileIndex);
		int sy1 = img.getTileImageY(tileIndex);
		int sx2 = sx1 + imgWidth;
		int sy2 = sy1 + imgHeight;
		
		int offX = img.getOverlapLeft();
		int offY = img.getOverlapTop();

		int worldX = terrainModel.getWorldX(x, y) - offX;
		int worldY = terrainModel.getWorldY(x, y) - offY;

		int dx1 = view.worldXToScreenX(worldX);
		int dy1 = view.worldYToScreenY(worldY);
		int dx2 = view.worldXToScreenX(worldX + imgWidth);
		int dy2 = view.worldYToScreenY(worldY + imgHeight);

		g.drawImage(img.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
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
					Integer step = animSteps.getData(x, y);
					int nextStep = step + 1;

					if (nextStep >= maxSteps)
					{
						nextStep = 0;
						
						oldIndices.setData(x, y, tile.getIndex());
						terrainModel.updateIndex(x, y);
					}

					animSteps.setData(x, y, Integer.valueOf(nextStep));
				}
			}
		}
		
		repaint();
	}

}

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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import terrain.TerrainModel;
import terrain.TerrainType;
import terrain.Tile;
import tiles.TileImage;
import tiles.TileIndex;
import tiles.TileIndexResolver;
import tiles.TileSet;

import common.DefaultValueMap;
import common.GridData;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileRenderer
{
	private Viewport view;
	private TerrainModel terrainModel;
	private TileSet tileset;
	private TileIndexResolver indexResolver;
	
	private GridData<TileIndex> currIndices;
	private GridData<TileIndex> nextIndices;
	private GridData<Integer> animSteps;

	private final Random random = new Random(12345);

	private Map<TerrainType, Integer> maxSteps = new DefaultValueMap<>(0);

	/**
	 * @param terrainModel
	 * @param view2
	 */
	public TileRenderer(TerrainModel terrainModel, TileSet tileset, Viewport view)
	{
		this.terrainModel = terrainModel;
		this.tileset = tileset;
		this.view = view;
		this.indexResolver = new TileIndexResolver(tileset);

		// TODO: put somewhere else
		maxSteps.put(TerrainType.WATER, 8);
		
		int mapWidth = terrainModel.getMapWidth();
		int mapHeight = terrainModel.getMapHeight();
		
		TileIndex invalidTile = tileset.getInvalidTileIndex();
		
		currIndices = new GridData<TileIndex>(mapWidth, mapHeight, invalidTile);
		nextIndices = new GridData<TileIndex>(mapWidth, mapHeight, invalidTile);
		animSteps = new GridData<Integer>(mapWidth, mapHeight, 0);
		
		for (int y = 0; y < terrainModel.getMapHeight(); y++)
		{
			for (int x = 0; x < terrainModel.getMapWidth(); x++)
			{
				Tile tile = terrainModel.getTile(x, y);
				
				Set<TileIndex> indices = indexResolver.getIndicesFor(tile.getTerrain(), terrainModel.getNeighbors(x, y));
				
				currIndices.setData(x, y, getRandom(indices));
				
				Integer steps = maxSteps.get(tile.getTerrain());
				
				if (steps > 0)
				{
					nextIndices.setData(x, y, getRandomOtherThan(indices, nextIndices.getData(x, y)));
					animSteps.setData(x, y, random.nextInt(steps));
				}
			}
		}
	}
	

	public void updateIndex(int mapX, int mapY) 
	{
		TileIndex index = nextIndices.getData(mapX, mapY);
		
		TerrainType terrain = terrainModel.getTile(mapX, mapY).getTerrain();
		Set<TileIndex> indices = indexResolver.getIndicesFor(terrain, terrainModel.getNeighbors(mapX, mapY));
		
		if (indices.size() <= 1)
			return;
		
		index = getRandomOtherThan(indices, index);
		
		nextIndices.setData(mapX, mapY, index);
	}

	private TileIndex getRandom(Set<TileIndex> indices)
	{
		int rand = random.nextInt(indices.size());
		
		Iterator<TileIndex> it = indices.iterator();
		for (int i = 0; i < rand; i++)
		{
			it.next();
		}
		
		return it.next();
	}
	
	/**
	 * @param indices
	 * @param index
	 */
	private TileIndex getRandomOtherThan(Set<TileIndex> indices, TileIndex index)
	{
		List<TileIndex> list = new ArrayList<TileIndex>(indices);
		
		// exclude old index from the set of possible new indices
		int rand = random.nextInt(list.size() - 1) + 1;		

		// oldPos can be -1 if the old index was not set before
		int oldPos = list.indexOf(index);
		
		// but rand is always in [1..size] so the sum is at least 0
		index = list.get((oldPos + rand) % list.size());
		
		return index;
	}


	/**
	 * @param g
	 */
	public void drawTileset(Graphics2D g, Rectangle screenRect)
	{
		// convert screen to world coordinates
		int worldX0 = view.screenXToWorldX(screenRect.x);
		int worldY0 = view.screenYToWorldY(screenRect.y);
		int worldX1 = view.screenXToWorldX(screenRect.x + screenRect.width);
		int worldY1 = view.screenYToWorldY(screenRect.y + screenRect.height);

		List<Tile> visibleTiles = terrainModel.getTilesInRect(worldX0, worldY0, worldX1, worldY1);

		// sort?
		
		for (Tile tile : visibleTiles)
		{
			int mapY = tile.getMapY();
			int mapX = tile.getMapX();

			TileIndex currIndex = currIndices.getData(mapX, mapY); 
			TileIndex nextIndex = nextIndices.getData(mapX, mapY);

			TileIndex invalid = tileset.getInvalidTileIndex();

			drawTile(g, currIndex, mapX, mapY);
			
			if (nextIndex != invalid)
			{
				Integer step = animSteps.getData(mapX, mapY);
				Integer maxStep = maxSteps.get(tile.getTerrain());
				
				float alpha = (float)step / maxStep;
				
				Composite oldComp = g.getComposite();
				
				// TODO: Cache alpha composite
				AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
				
				g.setComposite(ac);
				drawTile(g, nextIndex, mapX, mapY);
				g.setComposite(oldComp);
			}
			
			Set<TileIndex> indices = indexResolver.getOverlaysFor(tile.getTerrain(), terrainModel.getNeighbors(mapX, mapY));
			
			for (TileIndex overlay : indices)
			{
				drawTile(g, overlay, mapX, mapY);
			}
			
			drawTile(g, tileset.getGridTileIndex(), mapX, mapY);
		}
	}
	
	public void drawHoveredTiles(Graphics2D g, Set<Tile> hoveredTiles)
	{
		int imgWidth = tileset.getTileWidth();
		int imgHeight = tileset.getTileHeight();

		float size = (float) (9.0 * view.getZoom());
		g.setFont(g.getFont().deriveFont(size));
		for (Tile tile : hoveredTiles)
		{
			int mapX = tile.getMapX();
			int mapY = tile.getMapY();

			drawTile(g, tileset.getCursorTileIndex(), mapX, mapY);
			
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

	public void drawTile(Graphics2D g, TileIndex tileIndex, int x, int y)
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

		int worldX = terrainModel.getWorldX(x, y) - offX;
		int worldY = terrainModel.getWorldY(x, y) - offY;

		int dx1 = view.worldXToScreenX(worldX);
		int dy1 = view.worldYToScreenY(worldY);
		int dx2 = view.worldXToScreenX(worldX + imgWidth);
		int dy2 = view.worldYToScreenY(worldY + imgHeight);

		g.drawImage(img.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

//		AffineTransform at = new AffineTransform();
//		at.translate(view.worldXToScreenX(0), view.worldYToScreenY(0));
//		at.scale(view.getZoom(), view.getZoom());
//		
//		g.setTransform(at);
//		
//		g.drawImage(img.getImage(), worldX, worldY, worldX + imgWidth, worldY + imgHeight, sx1, sy1, sx2, sy2, null);
//		
//		g.setTransform(old);

	}

	/**
	 * 
	 */
	public void nextFrame()
	{
		for (int y = 0; y < terrainModel.getMapHeight(); y++)
		{
			for (int x = 0; x < terrainModel.getMapWidth(); x++)
			{
				Tile tile = terrainModel.getTile(x, y);
				Integer maxStep = maxSteps.get(tile.getTerrain());

				if (maxStep > 0)
				{
					Integer step = animSteps.getData(x, y);
					int nextStep = step + 1;

					if (nextStep >= maxStep)
					{
						nextStep = 0;
						
						TileIndex index = nextIndices.getData(x, y);
						currIndices.setData(x, y, index);
						updateIndex(x, y);
					}

					animSteps.setData(x, y, Integer.valueOf(nextStep));
				}
			}
		}
		
	}

}

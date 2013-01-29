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

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.swing.JComponent;

import terrain.GridData;
import terrain.TerrainLoader;
import terrain.TerrainModelDiamond;
import terrain.TerrainType;
import tiles.TileSet;
import tiles.TileSetBuilder;


/**
 * TODO Type description
 * @author Martin Steiger
 */
public class MyComponent extends JComponent
{
	private static final long serialVersionUID = 6701940511292511047L;
	
	private TerrainModelDiamond terrainModel;
	private TileSet tileset;

	/**
	 * 
	 */
	public MyComponent() throws IOException
	{
		InputStream terrainDataStream = new FileInputStream("data/example.txt");
		TerrainLoader terrainLoader = new TerrainLoader();
		GridData terrainData = terrainLoader.load(terrainDataStream);
		
		InputStream tilesetImageStream = new FileInputStream("data/tileset.png");
		TileSetBuilder tileSetBuilder = new TileSetBuilder();
		tileset = tileSetBuilder.build(tilesetImageStream);

		terrainModel = new TerrainModelDiamond(terrainData, tileset);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		int width = tileset.getTileWidth();
		int height = tileset.getTileHeight();

		int imgWidth = tileset.getTileImageWidth();
		int imgHeight = tileset.getTileImageHeight();
		
		int offX = tileset.getOverlapX();
		int offY = tileset.getOverlapY();

		g.setFont(g.getFont().deriveFont(9.0f).deriveFont(Font.BOLD));

		for (int y = 0; y < terrainModel.getMapHeight(); y++)
		{
			for (int x = 0; x < terrainModel.getMapWidth(); x++)
			{
				int source_index = terrainModel.getIndex(x, y);

				Image image = tileset.getImage(source_index);

				int sx1 = tileset.getTileImageX(source_index);
				int sy1 = tileset.getTileImageY(source_index);
				int sx2 = sx1 + imgWidth;
				int sy2 = sy1 + imgHeight;
				
				int dx1 = x * width + (y % 2) * width / 2 - offX;
				int dy1 = y * height / 2 - offY;
				int dx2 = dx1 + imgWidth;
				int dy2 = dy1 + imgHeight;

				g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
				
//				g.setColor(Color.WHITE);
//				g.drawString(String.format("%d / %d", x, y), dx1 + 14, dy1 + 18);
			}
		}
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
				if (terrainModel.getTerrain(x, y) == TerrainType.WATER)
				{
					terrainModel.updateIndex(x, y);
				}
			}
		}

		repaint();
	}

}

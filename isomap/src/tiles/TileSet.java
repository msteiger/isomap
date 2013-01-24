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

package tiles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import terrain.TerrainType;



/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileSet
{
	private BufferedImage image;
	private Map<TerrainType,List<TileTypeGroup>> map;
	
	private int tileWidth = 54;
	private int tileHeight = 28;

	private int offX = tileWidth;
	private int offY = tileHeight + 10;

	public TileSet(BufferedImage image, HashMap<TerrainType,List<TileTypeGroup>> map)
	{
		this.image = image;
		this.map = map;
	}
	
	public Image getImage(int tile_index)
	{
		return image;
	}

	public int getTileImageX(int tile_index)
	{
		int six = tile_index % 8;
		return six * offX;
	}

	public int getTileImageY(int tile_index)
	{
		int siy = tile_index / 8;
		return siy * offY;
	}
	
	public int getTileImageWidth()
	{
		return tileWidth;
	}
	
	public int getTileImageHeight()
	{
		return tileHeight + 20;
	}

	public int getTileWidth()
	{
		return tileWidth;
	}
	
	public int getTileHeight()
	{
		return tileHeight;
	}

	public int getIndexFor(TerrainType type, Pattern pattern)
	{
		TerrainType[] needPattern = pattern.getTuple();

		try
		{
			for (TileTypeGroup ttg : map.get(type))
			{
				TerrainType[] tilePattern = ttg.getPattern().getTuple();

				if (doesMatch(needPattern, tilePattern))
				{
					return ttg.getIndex();
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		return 0;
	}

	/**
	 * @param needPattern
	 * @param tilePattern
	 * @return
	 */
	private boolean doesMatch(TerrainType[] needPattern, TerrainType[] tilePattern)
	{
		for (int i = 0; i < tilePattern.length; i++)
		{
			if (tilePattern[i].compareTo(needPattern[i]) < 0)
			{
				return false;
			}
		}
		
		return true;
	}
}

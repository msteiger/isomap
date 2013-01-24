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
package terrain;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TerrainData
{
	private int mapWidth;
	private int mapHeight;
	private TerrainType[][] data;
	
	public TerrainData(int width, int height)
	{
		mapHeight = height;
		mapWidth = width;
		
		data = new TerrainType[mapHeight][mapWidth];
	}
	
	public void setTerrain(int x, int y, TerrainType type)
	{
		data[y][x] = type;  
	}

	/**
	 * @return the mapWidth
	 */
	public int getMapWidth()
	{
		return mapWidth;
	}

	/**
	 * @return the mapHeight
	 */
	public int getMapHeight()
	{
		return mapHeight;
	}

	public TerrainType getTerrain(int x, int y)
	{
//		if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight)
//			throw exception?
//			return UNDEFINED?
		
		return data[y][x];
	}
	
}

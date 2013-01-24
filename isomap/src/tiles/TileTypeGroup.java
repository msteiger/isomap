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
import java.util.Random;


import terrain.TerrainType;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileTypeGroup
{
	private int startIdx;
	private int endIdx;
	private Pattern pattern;
	private TerrainType tile;

	/**
	 * @param i
	 * @param j
	 * @param pattern
	 */
	public TileTypeGroup(int i, int j, TerrainType tile, Pattern pattern)
	{
		this.startIdx = i;
		this.endIdx = j;
		this.tile = tile;
		
		this.pattern = pattern;
	}

	public int getIndex()
	{
		return new Random().nextInt(endIdx - startIdx + 1) + startIdx;
	}
	
	/**
	 * @return the pattern
	 */
	public Pattern getPattern()
	{
		return pattern;
	}

	/**
	 * @return the tile
	 */
	public TerrainType getType()
	{
		return tile;
	}

	
}

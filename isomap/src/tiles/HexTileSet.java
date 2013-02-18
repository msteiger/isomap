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

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class HexTileSet extends TileSet
{
	private final int topLength;

	/**
	 * @param tileWidth
	 * @param tileHeight
	 */
	public HexTileSet(int tileWidth, int tileHeight, int topLength)
	{
		super(tileWidth, tileHeight);
		
		this.topLength = topLength;
	}

	/**
	 * @return the topLength
	 */
	public int getTopLength()
	{
		return topLength;
	}
	
}

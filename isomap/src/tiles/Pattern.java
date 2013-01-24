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

import java.util.Arrays;

import terrain.TerrainType;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class Pattern
{
	private TerrainType[] tuple;

	/**
	 * @param tuple
	 */
	public Pattern(TerrainType[] tuple)
	{
		this.tuple = tuple;
	}

	public TerrainType[] getTuple()
	{
		return tuple;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tuple);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pattern other = (Pattern) obj;
		if (!Arrays.equals(tuple, other.tuple))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Pattern [tuple=" + Arrays.toString(tuple) + "]";
	}
}

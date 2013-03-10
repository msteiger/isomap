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

package io.wml;

public class WmlTile 
{
	private Integer pos;
	private String type;
	private Integer layer;

	public void setType(String type) 
	{
		this.type = type;
	}

	public void setPos(Integer pos) 
	{
		this.pos = pos;
	}

	public void removeFlag(String value) {
		// TODO Auto-generated method stub
		
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

}

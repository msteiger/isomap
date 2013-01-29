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

public class Viewport 
{
	private int transX = 0;
	private int transY = 0;
	
	public int worldXToScreenX(int worldX) 
	{
		return worldX - transX;
	}
	
	public int worldYToScreenY(int worldY) 
	{
		return worldY - transY;
	}
	
	public void translate(int dx, int dy)
	{
		transX += dx;
		transY += dy;
	}
}

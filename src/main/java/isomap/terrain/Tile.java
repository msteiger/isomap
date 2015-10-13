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

package isomap.terrain;


/**
 * TODO Type description
 */
public class Tile {

    private final int x, y;

    /**
     * @param x the x-coordinate on the map
     * @param y the y-coordinate on the map
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getMapX() {
        return x;
    }

    public int getMapY() {
        return y;
    }
}

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

package isomap.common;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A regular 2D array, backed by an ArrayList
 * @param <T> the data type
 */
public class GridData<T> {
    private final int width;
    private final int height;
    private final ArrayList<T> data;

    public GridData(int width, int height, T defaultValue) {
        this.height = height;
        this.width = width;

        this.data = new ArrayList<T>(Collections.nCopies(height * width, defaultValue));
    }

    public void setData(int x, int y, T item) {
        data.set(indexOf(x, y), item);
    }

    /**
     * @return the mapWidth
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the mapHeight
     */
    public int getHeight() {
        return height;
    }

    public T getData(int x, int y) {
        return data.get(indexOf(x, y));
    }

    private int indexOf(int x, int y) {
        if (x < 0 || x >= width)
            throw new IllegalArgumentException("X-Coord. " + x + " not in range [0.." + (width - 1) + "]");

        if (y < 0 || y >= height)
            throw new IllegalArgumentException("Y-Coord. " + y + " not in range [0.." + (height - 1) + "]");

        return y * width + x;
    }

}

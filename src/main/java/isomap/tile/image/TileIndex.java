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

package isomap.tile.image;

/**
 * TODO Type description
 */
public class TileIndex {

    private final int globalIndex;
    private final int localIndex;

    /**
     * @param globalIndex
     * @param localIndex
     * @param tileImage
     */
    public TileIndex(int globalIndex, int localIndex) {
        this.globalIndex = globalIndex;
        this.localIndex = localIndex;
    }

    /**
     * @return the globalIndex
     */
    public int getGlobalIndex() {
        return globalIndex;
    }

    /**
     * @return the localIndex
     */
    public int getLocalIndex() {
        return localIndex;
    }

    @Override
    public String toString() {
        return "TileIndex [" + globalIndex + ", " + localIndex + "]";
    }

}

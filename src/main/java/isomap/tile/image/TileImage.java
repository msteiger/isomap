/*
 * Copyright (C) 2015 Martin Steiger
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

import java.awt.image.BufferedImage;

/**
 *
 */
public class TileImage {

    private final BufferedImage image;
    private final int offsetX;
    private final int offsetY;

    public TileImage(BufferedImage image, int offsetX, int offsetY) {
        this.image = image;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * @return
     */
    public BufferedImage getImage() {
        return image;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }


}

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

package isomap.view;

import java.util.Observable;

/**
 * Defines the viewport transformation for the scene
 */
public class Viewport extends Observable {
    private double transX = 0;
    private double transY = 0;
    private double zoom = 1;
    private int zoomLevel = 0;

    public int worldXToScreenX(int worldX) {
        return (int) (worldX * zoom - transX);
    }

    public int worldYToScreenY(int worldY) {
        return (int) (worldY * zoom - transY);
    }

    public int screenXToWorldX(int screenX) {
        return (int) ((screenX + transX) / zoom);
    }

    public int screenYToWorldY(int screenY) {
        return (int) ((screenY + transY) / zoom);
    }

    public void translate(int dx, int dy) {
        transX += dx;
        transY += dy;

        setChanged();
        notifyObservers();
    }

    /**
     * @return the zoom
     */
    public double getZoom() {
        return zoom;
    }

    public void zoom(int dz, int screenX, int screenY) {
        int worldX = screenXToWorldX(screenX);
        int worldY = screenYToWorldY(screenY);

        transX -= worldX * zoom;
        transY -= worldY * zoom;

        zoomLevel += dz;

        // this doesn't make any sense,
        // but irrational numbers seem to work best
        // with AffineTransform
        zoom = Math.exp(zoomLevel / Math.E);

        transX += worldX * zoom;
        transY += worldY * zoom;

        setChanged();
        notifyObservers();
    }
}

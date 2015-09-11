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

package common;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

/**
 * Triggers a repaint of the component
 * if notified
 * @author Martin Steiger
 */
public class RepaintingObserver implements Observer
{
    private final Component component;

    /**
     * @param component the component to repaint
     */
    public RepaintingObserver(Component component)
    {
        this.component = component;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        component.repaint();
    }
}

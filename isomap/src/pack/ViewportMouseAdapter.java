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

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Converts mouse events to viewport changes
 * @author Isometric God
 */
final class ViewportMouseAdapter extends MouseAdapter
{
	private final Viewport view;

	private Point oldPt = null;

	/**
	 * @param view
	 */
	public ViewportMouseAdapter(Viewport view)
	{
		this.view = view;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		oldPt = e.getPoint();
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
//		oldPt = null;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (oldPt == null)
			return;

		int dx = e.getX() - oldPt.x;
		int dy = e.getY() - oldPt.y;

		oldPt.x = e.getX();
		oldPt.y = e.getY();

		view.translate(-dx, -dy);
	}
}

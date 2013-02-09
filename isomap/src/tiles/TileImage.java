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

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileImage
{
	private final BufferedImage image;
	private final int overlapLeft;
	private final int overlapTop;
	private final int overlapRight;
	private final int overlapBottom;
	private final int tileWidth;
	private final int tileHeight;
	
	/**
	 * @param image
	 * @param tileWidth
	 * @param tileHeight
	 */
	public TileImage(BufferedImage image, int tileWidth, int tileHeight)
	{
		this(image, tileWidth, tileHeight, 0, 0, 0, 0);
	}

	/**
	 * @param image
	 * @param tileWidth
	 * @param tileHeight
	 * @param overlapLeft
	 * @param overlapTop
	 */
	public TileImage(BufferedImage image, int tileWidth, int tileHeight, int overlapLeft, int overlapTop)
	{
		this(image, tileWidth, tileHeight, overlapLeft, overlapTop, 0, 0);
	}

	/**
	 * @param image
	 * @param overlapLeft
	 * @param overlapTop
	 * @param overlapRight
	 * @param overlapBottom
	 */
	public TileImage(BufferedImage image, int tileWidth, int tileHeight, int overlapLeft, int overlapTop, int overlapRight, int overlapBottom)
	{
		this.image = image;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.overlapLeft = overlapLeft;
		this.overlapTop = overlapTop;
		this.overlapRight = overlapRight;
		this.overlapBottom = overlapBottom;
	}

	/**
	 * @return the image
	 */
	public Image getImage()
	{
		return image;
	}

	public int getImageX(int index)
	{
		int six = index % getTilesPerCol();
		return six * getTileImageWidth();
	}

	public int getImageY(int tile_index)
	{
		int siy = tile_index / getTilesPerCol();
		return siy * getTileImageHeight();
	}
	
	public int getTileImageWidth()
	{
		return tileWidth + overlapLeft + overlapRight;
	}
	
	public int getTileImageHeight()
	{
		return tileHeight + overlapTop + overlapBottom;
	}

	public int getTilesPerCol()
	{
		return image.getHeight() / getTileImageHeight();
	}

	public int getTilesPerRow()
	{
		return image.getWidth() / getTileImageWidth();
	}

	public int getTileCount()
	{
		return getTilesPerRow() * getTilesPerCol();
	}

	/**
	 * @return the overlapLeft
	 */
	public final int getOverlapLeft()
	{
		return overlapLeft;
	}

	/**
	 * @return the overlapTop
	 */
	public final int getOverlapTop()
	{
		return overlapTop;
	}

	/**
	 * @return the overlapRight
	 */
	public final int getOverlapRight()
	{
		return overlapRight;
	}

	/**
	 * @return the overlapBottom
	 */
	public final int getOverlapBottom()
	{
		return overlapBottom;
	}

	/**
	 * @return the tileWidth
	 */
	public final int getTileWidth()
	{
		return tileWidth;
	}

	/**
	 * @return the tileHeight
	 */
	public final int getTileHeight()
	{
		return tileHeight;
	}
}

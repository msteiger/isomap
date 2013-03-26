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

package view.drools;


import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tiles.TileImage;
import tiles.TileIndex;
import tiles.TileSet;
import datastores.DataStore;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class ImageProviderWesnoth
{
	private static final Logger log = LoggerFactory.getLogger(ImageProviderWesnoth.class);
	
	private TileSet tileset;
	
	private Map<String, TileIndex> imageMap = new LinkedHashMap<>();

	private DataStore datastore;

	private int tileHeight;

	private int tileWidth;
	
	private int idxCountHack = 0;

	private TileSetBuilderWesnoth tb;
	
	/**
	 * @param tileset
	 */
	public ImageProviderWesnoth(TileSetBuilderWesnoth tb)
	{
		this.tb = tb;
		this.tileset = tb.getTileSet();
	}


	public TileIndex getImage(String imgPath)
	{
		TileIndex idx = imageMap.get(imgPath);
		
		if (idx == null)
		{
			BufferedImage bi = tb.loadImage(imgPath);
			tileset.addImage(bi);
			
			idxCountHack++;
			
			idx = new TileIndex(idxCountHack, 0);
			imageMap.put(imgPath, idx);
		}
		
		return idx;
	}
	
}

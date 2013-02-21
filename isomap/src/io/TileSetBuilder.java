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

package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import common.OctDirection;

import terrain.TerrainType;
import tiles.TileIndexGroup;
import tiles.TileSet;



/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileSetBuilder
{
	public TileSetBuilder()
	{
	}
	

	/**
	 * @param string
	 */
	public void writeToStream(TileSet ts, OutputStream fos) throws IOException
	{
		OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
		PrintWriter bw = new PrintWriter(writer);

//		bw.println("# Tileset Definition File");
//		bw.println("# ver. 0.1");
//		bw.println("image = data/tileset.png");
//		bw.println("tileWidth = " + ts.getTileWidth());
//		bw.println("tileHeight = " + ts.getTileHeight());
//		bw.println("tileImageWidth = " + ts.getTileImageWidth());
//		bw.println("tileImageWidth = " + ts.getTileImageWidth());
//		bw.println("tileOverlapX = " + ts.getOverlapX());
//		bw.println("tileOverlapY = " + ts.getOverlapY());

		for (TileIndexGroup tig : ts.getIndexGroups())
		{
			bw.println(tig.getIndices() + " - " + tig.getTerrain() + " - " + tig.getBorders());
		}
		
		bw.close();
	}
	

	/**
	 * @param string
	 */
	public TileSet readFromStream(InputStream is) throws IOException
	{
		InputStreamReader reader = new InputStreamReader(is, "UTF-8");
		BufferedReader bw = new BufferedReader(reader);

		TileSet ts = new TileSet(54, 28);

		ts.addImage(new File("data/tileset.png"), 0, 10, 0, 0);
		ts.setCursorTileIndex(0);
		ts.setInvalidTileIndex(33 * 8);
		
		String line;
		while ((line = bw.readLine()) != null)
		{
			line = line.trim();

			int comm = line.indexOf('#');

			if (comm >= 0)
				line = line.substring(0, comm);			
			
			int tileWidth;
			int tileHeight;
			int tileImageWidth;
			int tileImageHeight;
			int tileOverlapX;
			int tileOverlapY;
			
			String[] parts = line.split("=");
			if (parts.length == 2)
			{
				String key = parts[0].trim().toLowerCase();
				String value = parts[1].trim().toLowerCase();
				
				if (key.equals("tileWidth"))
					tileWidth = Integer.valueOf(value);
				
				if (key.equals("tileHeight"))
					tileHeight = Integer.valueOf(value);
				
				if (key.equals("tileImageWidth"))
					tileImageWidth = Integer.valueOf(value);
				
				if (key.equals("tileImageHeight"))
					tileImageHeight = Integer.valueOf(value);
				
				if (key.equals("tileOverlapX"))
					tileOverlapX = Integer.valueOf(value);
				
				if (key.equals("tileOverlapY"))
					tileOverlapY = Integer.valueOf(value);
			}

			parts = line.split("-");
			
			if (parts.length == 3)
			{
				Set<Integer> idSet = new HashSet<Integer>();
				TerrainType terr;
				Map<OctDirection, TerrainType> dirMap = new HashMap<OctDirection, TerrainType>();
				
				{
					int start = parts[0].indexOf('[') + 1;
					int end = parts[0].indexOf(']');
					String[] idxList = parts[0].substring(start, end).split(","); 
					
					for (String ids : idxList)
						idSet.add(Integer.valueOf(ids.trim()));
				}
				
				{
					terr = TerrainType.valueOf(parts[1].trim());
				}
				
				{
					int start = parts[2].indexOf('{') + 1;
					int end = parts[2].indexOf('}');
					String[] idxList = parts[2].substring(start, end).split(","); 
					
					for (String p : idxList)
					{
						String[] kvp = p.split("=");
						if (kvp.length == 2)
						{
							dirMap.put(OctDirection.valueOf(kvp[0].trim()), TerrainType.valueOf(kvp[1].trim()));
						}
					}
				}
				
				ts.defineTerrain(idSet, terr, dirMap);
			}
		}
		
		bw.close();
		
		return ts;
	}
	
}

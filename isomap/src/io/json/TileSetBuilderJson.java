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

package io.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import tiles.TileSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileSetBuilderJson
{
	public TileSetBuilderJson()
	{
	}

	/**
	 * @param string
	 */
	public static void writeToStream(TileSet ts, File file) throws IOException
	{
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new TileSetModule());
		
		ObjectWriter writer = om.writerWithDefaultPrettyPrinter();
		
		try (OutputStream os = new FileOutputStream(file))
		{
			writer.writeValue(os, ts.getIndexGroups().iterator().next());
		}
	}
	
	/**
	 * @param string
	 */
	public static TileSet readFromStream(File file) throws IOException
	{
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new TileSetModule());

		try (InputStream is = new FileInputStream(file))
		{
			return om.readValue(file, TileSet.class);
		}
	}
	
}

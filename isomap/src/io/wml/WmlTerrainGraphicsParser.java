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

package io.wml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WmlTerrainGraphicsParser 
{
	private static final Logger log = LoggerFactory.getLogger(WmlTerrainGraphicsParser.class);

	public static List<WmlTerrainGraphics> parseWml(InputStream stream) throws IOException 
	{
		WmlReader wmlParser = new WmlReader(stream);
		List<WmlTerrainGraphics> results = new ArrayList<>();
		
		WmlTerrainGraphics terrain = null;
		WmlTile tile = null;
		WmlImage image = null;
		String key = null;

		while (wmlParser.hasNext())
		{
			String token = wmlParser.getToken();
			
			switch (wmlParser.getTokenType())
			{
			case COMMENT:
				break;
				
			case KEY:
				key = token;
				break;
				
			case START_TAG:
				if (Objects.equals(token, "terrain_graphics"))
					terrain = new WmlTerrainGraphics();
				
				if (Objects.equals(token, "tile"))
					tile = new WmlTile();
				
				break;
				
			case END_TAG:
				if (Objects.equals(token, "terrain_type"))
				{
					results.add(terrain);
					terrain = null;
				}
				
				if (Objects.equals(token, "tile"))
				{
					terrain.addTile(tile);
					tile = null;
				}
				break;

			case VALUE:
				if (tile != null)
					assign(tile, key, token); else
					assign(terrain, key, token);
				break;
			}
			
			wmlParser.next();
		}
		
		return results;
	}

	private static void assign(WmlTile tile, String key, String value) 
	{
		switch (key)
		{
			
		case "layer":
			tile.setLayer(Integer.valueOf(value));
			break;

		case "type":
			tile.setType(value);
			break;

		case "pos":
			tile.setPos(Integer.valueOf(value));
			break;
			
		case "set_no_flag":
			tile.removeFlag(value);
			break;

		default:
			log.warn("Unparsed tile attribute: " + key + " = " + value);

		}
	}

	private static void assign(WmlTerrainGraphics terrain, String key, String value) 
	{
		switch (key)
		{
		case "probability":
			terrain.setProbability(Integer.valueOf(value));
			break;
			
		case "name":
			terrain.setName(value);
			break;
			
		case "layer":
			terrain.setLayer(Integer.valueOf(value));
			break;
		
		case "type":
			terrain.setType(value);
			break;
			
		case "pos":
			terrain.setPos(Integer.valueOf(value));
			break;
			
		case "rotations":
			terrain.setRotations(value.split(","));
			
		case "variations":
			terrain.setVariations(value.split(";"));
			
		case "map":
			terrain.setMap(parseMap(value));
			break;
			
		case "center":
		case "base":
	//		String[] strs = value.split(",");
	//		terrain.setCenter(Integer.valueOf(strs[0], Integer.valueOf(strs[1])));
			break;
			
		default:
			log.warn("Unparsed terrain attribute: " + key + " = " + value);

		}
		
	}

	private static Map<Object, Object> parseMap(String value) 
	{
		return Collections.emptyMap();
	}
		

}

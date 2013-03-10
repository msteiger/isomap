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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WmlTerrainParser 
{
	private static final Logger log = LoggerFactory.getLogger(WmlTerrainParser.class);

	public static List<WmlTerrain> parseWmlTerrain(InputStream stream) throws IOException 
	{
		WmlReader wmlParser = new WmlReader(stream);
		List<WmlTerrain> results = new ArrayList<>();
		
		WmlTerrain terrain = null;
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
				if (!Objects.equals(token, "terrain_type"))
					continue;
				
				if (terrain != null)
					log.warn("Missing end tag - rejecting terrain data");
				
				terrain = new WmlTerrain();
				break;
				
			case END_TAG:
				if (!Objects.equals(token, "terrain_type"))
					continue;

				if (terrain != null)
					results.add(terrain); else
					log.warn("Missing start tag - rejecting terrain data");

				terrain = null;
				break;

			case VALUE:
				assign(terrain, key, token);
				break;
			}
			
			wmlParser.next();
		}
		
		return results;
	}

	private static void assign(WmlTerrain terrain, String key, String value) 
	{
		switch (key)
		{
		case "id":
			terrain.setId(value);
			break;
			
		case "name":
			terrain.setName(value);
			break;
			
		case "editor_name":
			terrain.setEditorName(value);
			break;
			
		case "editor_group":
			terrain.setEditorGroups(value.split(","));
			break;
			
		case "editor_image":
			// string
			break;
		
		case "default_base":
			terrain.setDefaultBase(value);
			break;
			
		case "symbol_image":
			terrain.setSymbolImage(value);
			break;
			
		case "string":
			terrain.setString(value);
			break;
			
		case "mvt_alias":
			terrain.setMvtAlias(value);
			break;
			
		case "aliasof":
			terrain.setAliases(value.split(","));
			break;
		
		case "light":
			// int value
			break;
			
		case "gives_income":
			// boolean
			break;
		case "heals":
			// int value;
			break;
			
		case "unit_height_adjust":
			// int value
			break;
		
		case "submerge":
			// int value
			break;
			
		case "recruit_from":
			// boolean
			break;
			
		case "recruit_to":
			// boolean
			break;
			
		case "recruit_onto":
			// boolean
			break;
			
		case "hidden":
			// boolean
			break;
			
		default:
			log.warn("Unparsed terrain attribute: " + key + " = " + value);
		}
	}

}

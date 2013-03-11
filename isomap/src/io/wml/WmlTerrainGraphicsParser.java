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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.OctDirection;

/**
 * Parses terrain-graphics.cfg files
 * @author Martin Steiger
 */
public class WmlTerrainGraphicsParser
{
	private static final Logger log = LoggerFactory.getLogger(WmlTerrainGraphicsParser.class);

	public static List<WmlTerrainGraphics> parseWml(InputStream stream) throws IOException
	{
		WmlReader wmlParser = new WmlReader(stream);
		List<WmlTerrainGraphics> results = new ArrayList<>();

		String key = null;

		LinkedList<Object> stack = new LinkedList<>();

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
					stack.push(new WmlTerrainGraphics());
				else

				if (Objects.equals(token, "image"))
					stack.push(new WmlImage());
				else

				if (Objects.equals(token, "tile"))
					stack.push(new WmlTile());
				else

					log.warn("Unrecognized tag " + token);
				break;

			case END_TAG:
				Object child = stack.pop();
				// TODO: verify token matches the head of the stack

				Object parent = stack.peek();

				if (parent == null && child instanceof WmlTerrainGraphics)
				{
					results.add((WmlTerrainGraphics) child);
				} 
				else if (parent instanceof WmlTerrainGraphics && child instanceof WmlImage)
				{
					((WmlTerrainGraphics)parent).addImage((WmlImage)child);
				}
				else if (parent instanceof WmlTile && child instanceof WmlImage)
				{
					((WmlTile)parent).setImage((WmlImage)child);
				}
				else if (parent instanceof WmlTerrainGraphics && child instanceof WmlTile)
				{
					((WmlTerrainGraphics) parent).addTile((WmlTile) child);
				}
				else
					log.warn("Stack broken " + child + " - " + parent);
				break;

			case VALUE:
				Object head = stack.peek();

				if (head instanceof WmlImage)
					assignImage((WmlImage) head, key, token);
				else

				if (head instanceof WmlTile)
					assignTile((WmlTile) head, key, token);
				else

				if (head instanceof WmlTerrainGraphics)
					assignGraphics((WmlTerrainGraphics) head, key, token);
				else

					log.warn("Unresolved head : " + head);
				break;
			}

			wmlParser.next();
		}

		return results;
	}

	/**
	 * @param image
	 * @param key
	 * @param token
	 */
	private static void assignImage(WmlImage image, String key, String value)
	{
		switch (key)
		{
		case "name":
			image.setName(value);
			break;

		case "layer":
			image.setLayer(Integer.valueOf(value));
			break;

		case "variations":
			image.setVariations(parseInts(value, ";"));
			break;

		case "center":
			List<Integer> cstrs = parseInts(value, ",");
			image.setCenter(cstrs.get(0), cstrs.get(1));
			break;
			
		case "base":
			List<Integer> bstrs = parseInts(value, ",");
			image.setBase(bstrs.get(0), bstrs.get(1));
			break;
			
		case "random_start":
			break;
			
		default:
			log.warn("Unparsed image tag: " + key + " = " + value);
				
		}
	}

	private static void assignTile(WmlTile tile, String key, String value)
	{
		switch (key)
		{
		case "x":
			tile.setX(Integer.valueOf(value));
			break;
			
		case "y":
			tile.setY(Integer.valueOf(value));
			break;
			
		case "layer":
			tile.setLayer(Integer.valueOf(value));
			break;

		case "type":
			tile.setType(value);
			break;

		case "pos":
			tile.setPos(Integer.valueOf(value));
			break;

		case "has_flag":
			tile.addRequiredFlags(splitString(value, ","));
			break;

		case "no_flag":
			tile.addExcludingFlags(splitString(value, ","));
			break;
			
		case "set_flag":
			tile.addSetFlags(splitString(value, ","));
			break;

		case "set_no_flag":
			tile.addExcludingFlags(splitString(value, ","));
			tile.addSetFlags(splitString(value, ","));
			break;

		default:
			log.warn("Unparsed tile attribute: " + key + " = " + value);

		}
	}

	private static void assignGraphics(WmlTerrainGraphics terrain, String key, String value) throws IOException
	{
		switch (key)
		{
		case "probability":
			terrain.setProbability(Integer.valueOf(value));
			break;

		case "rotations":
			terrain.setRotations(parseRotations(value, ","));

		case "map":
			terrain.setMap(parseMap(value));
			break;

		default:
			log.warn("Unparsed terrain attribute: " + key + " = " + value);

		}

	}

	private static Map<Object, Object> parseMap(String value)
	{
		return Collections.emptyMap();
	}


	private static List<String> splitString(String str, String regex)
	{
		if (str == null)
			return Collections.emptyList();
		
		return Arrays.asList(str.split(regex));
	}
	
	private static List<Integer> parseInts(String value, String regex)
	{
		List<String> strs = splitString(value, regex);
		List<Integer> result = new ArrayList<>(strs.size());
		
		for (String str : strs)
		{
			if (str != null && !str.isEmpty())
			{
				Integer val = Integer.valueOf(str);	// throws NumberFormatException
				result.add(val);
			}
		}
		
		return result;
	}

	private static List<OctDirection> parseRotations(String value, String regex) throws IOException
	{
		List<String> strs = splitString(value, regex);
		List<OctDirection> result = new ArrayList<>(strs.size());
		
		Map<String, OctDirection> dirMap = new HashMap<>();
		dirMap.put("n", OctDirection.NORTH);
		dirMap.put("ne", OctDirection.NORTH_EAST);
		dirMap.put("e", OctDirection.EAST);
		dirMap.put("se", OctDirection.SOUTH_EAST);
		dirMap.put("s", OctDirection.SOUTH);
		dirMap.put("sw", OctDirection.SOUTH_WEST);
		dirMap.put("w", OctDirection.WEST);
		dirMap.put("nw", OctDirection.NORTH_WEST);
		
		dirMap.put("t", OctDirection.NORTH);
		dirMap.put("tr", OctDirection.NORTH_EAST);
		dirMap.put("r", OctDirection.EAST);
		dirMap.put("br", OctDirection.SOUTH_EAST);
		dirMap.put("b", OctDirection.SOUTH);
		dirMap.put("bl", OctDirection.SOUTH_WEST);
		dirMap.put("l", OctDirection.WEST);
		dirMap.put("tl", OctDirection.NORTH_WEST);

		for (String str : strs)
		{
			OctDirection dir = dirMap.get(str.toLowerCase());
			
			if (dir == null)
				throw new IOException("Direction \" + str + \" not recognized");
			
			result.add(dir);
		}
		
		return result;
	}

}

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

import static terrain.TerrainType.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import terrain.TerrainType;


/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileSetBuilder
{
	public TileSetBuilder()
	{
	}
	
	public TileSet build(InputStream stream) throws IOException
	{
		BufferedImage image = ImageIO.read(stream);
		
		TileTypeGroup land = new TileTypeGroup(8, 11, GRASS, new Pattern(new TerrainType[] { GRASS, GRASS, GRASS, GRASS }));

		TileTypeGroup wwww = new TileTypeGroup(2, 7, WATER, new Pattern(new TerrainType[] { WATER, WATER, WATER, WATER }));
		TileTypeGroup wlww = new TileTypeGroup(12, 13, WATER, new Pattern(new TerrainType[] { WATER, GRASS, WATER, WATER }));
		TileTypeGroup wwlw = new TileTypeGroup(14, 15, WATER, new Pattern(new TerrainType[] { WATER, WATER, GRASS, WATER }));
		TileTypeGroup wllw = new TileTypeGroup(16, 17, WATER, new Pattern(new TerrainType[] { WATER, GRASS, GRASS, WATER }));
		TileTypeGroup wwwl = new TileTypeGroup(18, 19, WATER, new Pattern(new TerrainType[] { WATER, WATER, WATER, GRASS }));
		TileTypeGroup wlwl = new TileTypeGroup(20, 21, WATER, new Pattern(new TerrainType[] { WATER, GRASS, WATER, GRASS }));
		TileTypeGroup wwll = new TileTypeGroup(22, 23, WATER, new Pattern(new TerrainType[] { WATER, WATER, GRASS, GRASS }));
		TileTypeGroup wlll = new TileTypeGroup(24, 25, WATER, new Pattern(new TerrainType[] { WATER, GRASS, GRASS, GRASS }));
		TileTypeGroup lwww = new TileTypeGroup(26, 27, WATER, new Pattern(new TerrainType[] { GRASS, WATER, WATER, WATER }));
		TileTypeGroup llww = new TileTypeGroup(28, 29, WATER, new Pattern(new TerrainType[] { GRASS, GRASS, WATER, WATER }));
		TileTypeGroup lwlw = new TileTypeGroup(30, 31, WATER, new Pattern(new TerrainType[] { GRASS, WATER, GRASS, WATER }));
		TileTypeGroup lllw = new TileTypeGroup(32, 33, WATER, new Pattern(new TerrainType[] { GRASS, GRASS, GRASS, WATER }));
		TileTypeGroup lwwl = new TileTypeGroup(34, 35, WATER, new Pattern(new TerrainType[] { GRASS, WATER, WATER, GRASS }));
		TileTypeGroup llwl = new TileTypeGroup(36, 37, WATER, new Pattern(new TerrainType[] { GRASS, GRASS, WATER, GRASS }));
		TileTypeGroup lwll = new TileTypeGroup(38, 39, WATER, new Pattern(new TerrainType[] { GRASS, WATER, GRASS, GRASS }));
		TileTypeGroup llll = new TileTypeGroup(40, 41, WATER, new Pattern(new TerrainType[] { GRASS, GRASS, GRASS, GRASS }));

		TileTypeGroup moun = new TileTypeGroup(48, 55, MOUNTAIN, new Pattern(new TerrainType[] { GRASS, GRASS, GRASS, GRASS }));		
		TileTypeGroup fore = new TileTypeGroup(72, 79, FOREST, new Pattern(new TerrainType[] { GRASS, GRASS, GRASS, GRASS }));		
		
		
		List<TileTypeGroup> list = new ArrayList<TileTypeGroup>();

		list.add(fore);
		list.add(moun);
		list.add(land);
		
		list.add(wwww);
		list.add(wwwl);
		list.add(wwlw);
		list.add(wlww);
		list.add(lwww);

		list.add(wwll);
		list.add(wllw);
		list.add(llww);

		list.add(lwwl);
		list.add(wllw);
		list.add(wlwl);
		list.add(lwlw);

		list.add(wlll);
		list.add(lwll);
		list.add(llwl);
		list.add(lllw);
		
		list.add(llll);
		
		HashMap<TerrainType, List<TileTypeGroup>> map = new HashMap<TerrainType, List<TileTypeGroup>>();
		
		for (TerrainType t : TerrainType.values())
			map.put(t, new ArrayList<TileTypeGroup>());

		for (TileTypeGroup b : list)
		{
			map.get(b.getType()).add(b);
		}
		
		TileSet ts = new TileSet(image, map);
		
		return ts;
	}
	
}

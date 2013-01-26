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

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Ranges;
import com.google.common.collect.Sets;

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

		TileSet ts = new TileSet(image);

		ts.defineTerrain(Ranges.closed(8, 11), GRASS, new Pattern(GRASS, GRASS, GRASS, GRASS ));

		ts.defineTerrain(Ranges.closed(2, 7), WATER, new Pattern(WATER, WATER, WATER, WATER ));
		ts.defineTerrain(Ranges.closed(12, 13), WATER, new Pattern(WATER, GRASS, WATER, WATER ));
		ts.defineTerrain(Ranges.closed(14, 15), WATER, new Pattern(WATER, WATER, GRASS, WATER ));
		ts.defineTerrain(Ranges.closed(16, 17), WATER, new Pattern(WATER, GRASS, GRASS, WATER ));
		ts.defineTerrain(Ranges.closed(18, 19), WATER, new Pattern(WATER, WATER, WATER, GRASS ));
		ts.defineTerrain(Ranges.closed(20, 21), WATER, new Pattern(WATER, GRASS, WATER, GRASS ));
		ts.defineTerrain(Ranges.closed(22, 23), WATER, new Pattern(WATER, WATER, GRASS, GRASS ));
		ts.defineTerrain(Ranges.closed(24, 25), WATER, new Pattern(WATER, GRASS, GRASS, GRASS ));
		ts.defineTerrain(Ranges.closed(26, 27), WATER, new Pattern(GRASS, WATER, WATER, WATER ));
		ts.defineTerrain(Ranges.closed(28, 29), WATER, new Pattern(GRASS, GRASS, WATER, WATER ));
		ts.defineTerrain(Ranges.closed(30, 31), WATER, new Pattern(GRASS, WATER, GRASS, WATER ));
		ts.defineTerrain(Ranges.closed(32, 33), WATER, new Pattern(GRASS, GRASS, GRASS, WATER ));
		ts.defineTerrain(Ranges.closed(34, 35), WATER, new Pattern(GRASS, WATER, WATER, GRASS ));
		ts.defineTerrain(Ranges.closed(36, 37), WATER, new Pattern(GRASS, GRASS, WATER, GRASS ));
		ts.defineTerrain(Ranges.closed(38, 39), WATER, new Pattern(GRASS, WATER, GRASS, GRASS ));
		ts.defineTerrain(Ranges.closed(40, 41), WATER, new Pattern(GRASS, GRASS, GRASS, GRASS ));

		ts.defineTerrain(Ranges.closed(48, 55), MOUNTAIN, new Pattern(GRASS, GRASS, GRASS, GRASS ));		
		ts.defineTerrain(Ranges.closed(72, 79), FOREST, new Pattern(GRASS, GRASS, GRASS, GRASS ));		
		
		
		return ts;
	}
	
}

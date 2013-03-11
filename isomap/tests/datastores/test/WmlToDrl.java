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

package datastores.test;

import io.wml.WmlTerrain;
import io.wml.WmlTerrainGraphics;
import io.wml.WmlTerrainGraphicsParser;
import io.wml.WmlTerrainParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class WmlToDrl
{
	public static void main(String[] args) throws IOException
	{
		InputStream terStream = new FileInputStream("data/wesnoth/terrain.cfg");
		InputStream tgStream = new FileInputStream("data/wesnoth/terrain-graphics_unpacked.cfg");
		List<WmlTerrain> terrains = WmlTerrainParser.parseWmlTerrain(terStream);
		List<WmlTerrainGraphics> tg = WmlTerrainGraphicsParser.parseWml(tgStream);

//		WmlTerrainModel model = new WmlTerrainModel(terrains, tg);

	}
}

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
package isomap.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import isomap.common.GridData;
import isomap.terrain.SimpleTerrainType;
import isomap.terrain.TerrainType;

/**
 * TODO Type description
 */
public class SimpleTerrainLoader implements TerrainLoader {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTerrainLoader.class);

    private final Map<Character, TerrainType> convert;

    public SimpleTerrainLoader() {
        convert = new HashMap<>();
        convert.put('~', SimpleTerrainType.WATER);
        convert.put('#', SimpleTerrainType.GRASS);
        convert.put('M', SimpleTerrainType.MOUNTAIN);
        convert.put('T', SimpleTerrainType.FOREST);
        convert.put('S', SimpleTerrainType.SAND);
    }

    public SimpleTerrainLoader(Map<Character, TerrainType> map) {
        convert = new HashMap<>(map);
    }

    public GridData<TerrainType> load(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

        ArrayList<String> rows = new ArrayList<String>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            rows.add(line);
        }

        int mapHeight = rows.size();
        int mapWidth = rows.get(0).length();

        GridData<TerrainType> terrain = new GridData<TerrainType>(mapWidth, mapHeight, TerrainType.UNDEFINED);

        for (int y = 0; y < mapHeight; y++) {
            if (rows.get(y).length() < mapWidth) {
                throw new IOException("Row too short: " + y);
            }

            for (int x = 0; x < mapWidth; x++) {
                char ch = rows.get(y).charAt(x);
                TerrainType type = convert.get(ch);
                if (type == null) {
                    type = TerrainType.UNDEFINED;
                    logger.warn("Character at {}/{} not recognized: {}", y, x, ch);
                }

                terrain.setData(x, y, type);
            }
        }

        return terrain;
    }
}

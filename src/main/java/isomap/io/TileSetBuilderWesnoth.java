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

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Ranges;

import isomap.common.OctDirection;
import isomap.datastores.DataStore;
import isomap.terrain.SimpleTerrainType;
import isomap.terrain.TerrainType;
import isomap.tile.image.HexTileSet;

/**
 * TODO Type description
 */
public class TileSetBuilderWesnoth {
    private static final Logger log = LoggerFactory.getLogger(TileSetBuilderWesnoth.class);

    private DataStore datastore;

    private BufferedImage defaultImage;

    /**
     * @param dao
     */
    public TileSetBuilderWesnoth(DataStore dao) {
        this.datastore = dao;
    }

    public HexTileSet read() {
        Map<OctDirection, TerrainType> borders = new HashMap<>();

        HexTileSet ts = new HexTileSet(72, 72, 36);
        ts.addImage(loadImage("foreground.png"), 0, 0, 0, 0);
        ts.setCursorTileIndex(0);
        ts.setInvalidTileIndex(0);

        ts.addImage(loadImage("grass", "green.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("grass", "green2.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("grass", "green3.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("grass", "green4.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("grass", "green5.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("grass", "green6.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("grass", "green7.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("grass", "green8.png"), 0, 0, 0, 0);

        ts.defineTerrain(Ranges.closed(1, 8).asSet(DiscreteDomains.integers()), SimpleTerrainType.GRASS, borders);

        ts.addImage(loadImage("water", "coast-tropical-A01.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A02.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A03.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A04.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A05.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A06.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A07.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A08.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A09.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A10.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A11.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A12.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A13.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A14.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("water", "coast-tropical-A15.png"), 0, 0, 0, 0);

        ts.defineTerrain(Ranges.closed(9, 23).asSet(DiscreteDomains.integers()), SimpleTerrainType.WATER, borders);

        ts.addImage(loadImage("sand", "desert.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("sand", "desert2.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("sand", "desert3.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("sand", "desert4.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("sand", "desert5.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("sand", "desert6.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("sand", "desert7.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("sand", "desert8.png"), 0, 0, 0, 0);

        ts.defineTerrain(Ranges.closed(24, 31).asSet(DiscreteDomains.integers()), SimpleTerrainType.SAND, borders);

        ts.addImage(loadImage("forest", "forested-deciduous-summer-hills-tile.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("forest", "forested-hills-tile.png"), 0, 0, 0, 0);

        ts.defineTerrain(Ranges.closed(32, 33).asSet(DiscreteDomains.integers()), SimpleTerrainType.FOREST, borders);

        ts.addImage(loadImage("hills", "regular.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("hills", "regular2.png"), 0, 0, 0, 0);
        ts.addImage(loadImage("hills", "regular3.png"), 0, 0, 0, 0);

        ts.defineTerrain(Ranges.closed(34, 36).asSet(DiscreteDomains.integers()), SimpleTerrainType.MOUNTAIN, borders);

        ts.addImage(loadImage("grid.png"), 0, 0, 0, 0);

        ts.setGridTileIndex(37);

        return ts;
    }

    /**
     * @return
     * @throws IOException
     */
    private BufferedImage loadImage(String path, String... more) {
        try (InputStream stream = datastore.getInputStream(path, more)) {
            return ImageIO.read(stream);
        } catch (IOException e) {
            log.error("Error loading " + Paths.get(path, more), e);

            return getDefaultImage();
        }
    }

    /**
     * @return
     */
    private BufferedImage getDefaultImage() {
        if (defaultImage != null)
            return defaultImage;

        String defImagePath = "data/wesnoth/images/terrain/impassable-editor.png";

        try {
            defaultImage = ImageIO.read(new FileInputStream(defImagePath));
            return defaultImage;
        } catch (IOException e) {
            log.error("Error loading default image " + defImagePath, e);
            defaultImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
            return defaultImage;
        }
    }
}

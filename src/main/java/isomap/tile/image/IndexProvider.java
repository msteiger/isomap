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

package isomap.tile.image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import isomap.common.GridData;
import isomap.common.OctDirection;
import isomap.terrain.SimpleTerrainType;
import isomap.terrain.TerrainType;
import isomap.terrain.Tile;
import isomap.tile.model.TileModel;

/**
 * TODO Type description
 */
public class IndexProvider {
    private TileIndexResolver indexResolver;

    private GridData<TileImage> currIndices;
    private GridData<TileImage> nextIndices;
    private GridData<Integer> animSteps;

    private final Random random = new Random(12345);

    private final Map<TerrainType, Integer> maxSteps = new HashMap<>(0);
    private final TileModel terrainModel;
    private final GridData<TerrainType> terrainData;

    /**
     * @param resolver
     */
    public IndexProvider(GridData<TerrainType> terrainData, TileModel terrainModel, TileSet tileset) {
        this.indexResolver = new TileIndexResolver(tileset);
        this.terrainModel = terrainModel;
        this.terrainData = terrainData;

        int mapWidth = terrainModel.getMapWidth();
        int mapHeight = terrainModel.getMapHeight();

        // TODO: move somewhere else
        maxSteps.put(SimpleTerrainType.WATER, 8);

        TileImage invalidTile = tileset.getInvalidTileImage();

        currIndices = new GridData<TileImage>(mapWidth, mapHeight, invalidTile);
        nextIndices = new GridData<TileImage>(mapWidth, mapHeight, invalidTile);
        animSteps = new GridData<Integer>(mapWidth, mapHeight, 0);

        for (int y = 0; y < terrainModel.getMapHeight(); y++) {
            for (int x = 0; x < terrainModel.getMapWidth(); x++) {
                TerrainType terrain = terrainData.getData(x, y);

                Map<OctDirection, Tile> neighbors = terrainModel.getNeighbors(x, y);
                Map<OctDirection, TerrainType> terrains = new HashMap<>();
                for (OctDirection dir : neighbors.keySet()) {
                    Tile n = neighbors.get(dir);
                    TerrainType type = terrainData.getData(n.getMapX(), n.getMapY());
                    terrains.put(dir, type);
                }
                Collection<TileImage> indices = indexResolver.getIndicesFor(terrainData.getData(x, y), terrains);

                currIndices.setData(x, y, getRandom(indices));

                Integer steps = maxSteps.getOrDefault(terrain, Integer.valueOf(0));

                if (steps > 0) {
                    nextIndices.setData(x, y, getRandomOtherThan(indices, currIndices.getData(x, y)));
                    animSteps.setData(x, y, random.nextInt(steps));
                }
            }
        }

    }

    private void updateIndex(int mapX, int mapY) {
        TileImage index = nextIndices.getData(mapX, mapY);

        TerrainType terrain = terrainData.getData(mapX, mapY);
        Map<OctDirection, Tile> neighbors = terrainModel.getNeighbors(mapX, mapY);
        Map<OctDirection, TerrainType> terrains = new HashMap<>();
        for (OctDirection dir : neighbors.keySet()) {
            Tile n = neighbors.get(dir);
            TerrainType type = terrainData.getData(n.getMapX(), n.getMapY());
            terrains.put(dir, type);
        }
        Collection<TileImage> indices = indexResolver.getIndicesFor(terrain, terrains);

        if (indices.size() <= 1)
            return;

        index = getRandomOtherThan(indices, index);

        nextIndices.setData(mapX, mapY, index);
    }

    private TileImage getRandom(Collection<TileImage> indices) {
        int rand = random.nextInt(indices.size());

        Iterator<TileImage> it = indices.iterator();
        for (int i = 0; i < rand; i++)
        {
            it.next();
        }

        return it.next();
    }

    /**
     * @param indices
     * @param index
     */
    private TileImage getRandomOtherThan(Collection<TileImage> indices, TileImage index) {
        List<TileImage> list = new ArrayList<TileImage>(indices);

        // exclude old index from the set of possible new indices
        int rand = random.nextInt(list.size() - 1) + 1;

        // oldPos can be -1 if the old index was not set before
        int oldPos = list.indexOf(index);

        // but rand is always in [1..size] so the sum is at least 0
        return list.get((oldPos + rand) % list.size());
    }

    public TileImage getCurrentIndex(int mapX, int mapY) {
        return currIndices.getData(mapX, mapY);
    }

    public TileImage getNextIndex(int mapX, int mapY) {
        return nextIndices.getData(mapX, mapY);
    }

    public Integer getAnimStep(int mapX, int mapY) {
        return animSteps.getData(mapX, mapY);
    }

    public Integer getAnimSteps(TerrainType terrain) {
        return maxSteps.getOrDefault(terrain, Integer.valueOf(0));
    }

    public void nextFrame() {
        for (int y = 0; y < terrainModel.getMapHeight(); y++) {
            for (int x = 0; x < terrainModel.getMapWidth(); x++) {
                Integer maxStep = maxSteps.getOrDefault(terrainData.getData(x, y), Integer.valueOf(0));

                if (maxStep > 0) {
                    Integer step = animSteps.getData(x, y);
                    int nextStep = step + 1;

                    if (nextStep >= maxStep) {
                        nextStep = 0;

                        TileImage index = nextIndices.getData(x, y);
                        currIndices.setData(x, y, index);
                        updateIndex(x, y);
                    }

                    animSteps.setData(x, y, Integer.valueOf(nextStep));
                }
            }
        }
    }

    public Collection<TileImage> getOverlaysFor(TerrainType terrain, Map<OctDirection, TerrainType> neighbors) {
        return indexResolver.getOverlaysFor(terrain, neighbors);
    }


}

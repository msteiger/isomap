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

package view.drools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import terrain.TerrainModel;
import tiles.TileIndex;
import tiles.TileSet;

import common.GridData;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class IndexProviderDrools
{
	private static final Logger log = LoggerFactory.getLogger(IndexProviderDrools.class);

	private TileSet tileset;
	private TerrainModel terrainModel;

	private GridData<List<TileIndex>> indexData;
	
	/**
	 * @param terrainModel
	 * @param tileset
	 */
	public IndexProviderDrools(TerrainModel terrainModel, TileSetBuilderWesnoth tb)
	{
		this.terrainModel = terrainModel;
		this.tileset = tb.getTileSet();

		Resource drlResource = ResourceFactory.newClassPathResource("rules.drl", this.getClass());

		KnowledgeBase kbase = createKnowledgeBase(drlResource);
		StatefulKnowledgeSession session = kbase.newStatefulKnowledgeSession();

		ImageProviderWesnoth imgProvider = new ImageProviderWesnoth(tb);

		indexData = new GridData<>(terrainModel.getMapWidth(), terrainModel.getMapHeight(), null);
		
		session.setGlobal("indexData", indexData);
		session.setGlobal("invalidTile", tileset.getInvalidTileIndex());
		session.setGlobal("imageProvider", imgProvider);

		for (int y=0; y<terrainModel.getMapHeight(); y++)
		{
			for (int x=0; x<terrainModel.getMapWidth(); x++)
			{
				indexData.setData(x, y, new ArrayList<TileIndex>());
				session.insert(terrainModel.getTile(x, y));
			}
		}

		session.fireAllRules();
	
		session.dispose();  
	}
	
	private KnowledgeBase createKnowledgeBase(Resource drlResource)
	{
		final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		// this will parse and compile in one step
		kbuilder.add(drlResource, ResourceType.DRL);

		// Check the builder for errors
		if (kbuilder.hasErrors())
		{
        	KnowledgeBuilderErrors errors = kbuilder.getErrors();
        	for (KnowledgeBuilderError error : errors)
        	{
        		log.error(error.getSeverity() + " " + Arrays.toString(error.getLines()) + error.getMessage());
        	}

			throw new RuntimeException("Unable to compile \"" + drlResource.toString() + ".");
		}

		// get the compiled packages (which are serializable)
		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();

		// add the packages to a knowledge base (deploy the knowledge packages).
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

		kbase.addKnowledgePackages(pkgs);
		
		return kbase;
	}

	/**
	 * @param mapX
	 * @param mapY
	 * @return
	 */
	public List<TileIndex> getIndices(int mapX, int mapY)
	{
		return indexData.getData(mapX, mapY);
	}

}

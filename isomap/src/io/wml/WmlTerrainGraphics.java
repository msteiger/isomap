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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WmlTerrainGraphics 
{
	private final List<WmlTile> tiles = new ArrayList<>();

	private int probability;
	private String name;
	private String type;
	private int pos;
	private int layer;

	private String[] variations;

	private Object map;

	private Object rotations;
	
	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void addTile(WmlTile tile) 
	{
		tiles.add(tile);
	}

	public List<WmlTile> getTiles() 
	{
		return Collections.unmodifiableList(tiles);
	}

	public void setVariations(String[] variations) {
		this.variations = variations;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}

	public void setRotations(String[] rotations) {
		this.rotations = rotations;
	}

	
	

}

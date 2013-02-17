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

package io.json;

import java.io.IOException;

import tiles.TileIndexGroup;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

/**
 * TODO Type description
 * @author Martin Steiger
 */
class TileIndexGroupDeserializer extends StdScalarDeserializer<TileIndexGroup>
{
	private static final long serialVersionUID = 6578184052471462238L;

	/**
	 * @param clazz
	 */
	public TileIndexGroupDeserializer()
	{
		super(TileIndexGroup.class);
	}


	@Override
	public TileIndexGroup deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
	{
//		@JsonProperty("terrain") TerrainType terrain, 
//		@JsonProperty("borders") Map<OctDirection, TerrainType> borders)

		return null;
	}
}

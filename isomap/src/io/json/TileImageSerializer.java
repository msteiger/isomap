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

import tiles.TileImage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TileImageSerializer extends JsonSerializer<TileImage>
{
	@Override
	public void serialize(TileImage value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
	{
		jgen.writeStartObject();
		provider.defaultSerializeField("tileWidth", value.getTileWidth(), jgen);
		provider.defaultSerializeField("tileHeight", value.getTileHeight(), jgen);
//		BufferedImage image;
//		int overlapLeft, 
//		int overlapTop, 
//		int overlapRight, 
//		int overlapBottom
		jgen.writeEndObject();
	}

}

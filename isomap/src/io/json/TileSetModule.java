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

import com.fasterxml.jackson.databind.module.SimpleModule;

import tiles.TileImage;
import tiles.TileIndexGroup;

/**
 * TODO Type description
 * @author Martin Steiger
 */

public final class TileSetModule extends SimpleModule
{
	public TileSetModule()
	{
		addDeserializer(TileIndexGroup.class, new TileIndexGroupDeserializer());
		addSerializer(TileIndexGroup.class, new TileIndexGroupSerializer());

		addSerializer(TileImage.class, new TileImageSerializer());
	}
}

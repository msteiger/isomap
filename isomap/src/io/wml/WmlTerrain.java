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

public class WmlTerrain 
{
	private String id;
	private String name;
	private String editorName;
	private String[] editorGroups;
	private String[] aliases;
	private String str;
	private String symbolImage;
	private String defaultBase;
	private String mvtAlias;
	
	public String getDefaultBase() 
	{
		return defaultBase;
	}

	public void setDefaultBase(String defaultBase) 
	{
		this.defaultBase = defaultBase;
	}

	public String getId() 
	{
		return id;
	}

	public String getName() 
	{
		return name;
	}

	public String getEditorName() 
	{
		return editorName;
	}

	public String[] getEditorGroups() 
	{
		return editorGroups;
	}

	public String[] getAliases() 
	{
		return aliases;
	}

	public String getString() 
	{
		return str;
	}

	public String getSymbolImage() 
	{
		return symbolImage;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public void setEditorName(String editorName) 
	{
		this.editorName = editorName;
	}

	public void setEditorGroups(String[] groups) 
	{
		this.editorGroups = groups;
	}

	public void setSymbolImage(String image) 
	{
		this.symbolImage = image;
	}

	public void setString(String str) 
	{
		this.str = str;
	}

	public void setAliases(String[] aliases) 
	{
		this.aliases = aliases;
	}

	public void setMvtAlias(String value) 
	{
		this.mvtAlias = value;
	}

	public String getMvtAlias() 
	{
		return mvtAlias;
	}
	
	 

}

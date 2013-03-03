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

import io.wml.WmlReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

/**
 * Tests the {@link io.wml.WmlParser} class.
 * @author Martin Steiger
 */
public class WmlParserTest
{
	public static String testComments = "#sdfsfdsdf\n asd = cvb#\n  [kkk] cvb=bbb [/kkk]\n";
	
	public static void main(String[] args)
	{
		try
		{
			formatContent(System.out);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("DONE");
	}

	/**
	 * @throws IOException
	 */
	private static void formatContent(PrintStream w) throws IOException
	{
		File name = new File("data/wesnoth/terrain-graphics_unpacked.cfg");
		FileInputStream stream = new FileInputStream(name);
		WmlReader wmlParser = new WmlReader(stream);

		int indent = 0;
		while (wmlParser.hasNext())
		{
			String token = wmlParser.getToken();
			
			switch (wmlParser.getTokenType())
			{
			case COMMENT:
				w.println(getIndent(indent) +  "#" + token);
				break;
				
			case START_TAG:
				w.println(getIndent(indent) +  "[" + token + "]");
				indent++;
				break;

			case END_TAG:
				indent--;
				w.println(getIndent(indent) +  "[/" + token + "]");
				break;
				
			case KEY:
				w.print(getIndent(indent) + token + "=");
				break;
				
			case VALUE:
				if (token.equals("castle/orcish/keep-tile"))
					System.out.println("ND");
				
				w.println(token);
				break;
				
			default:
				break;
			
			}
			
			wmlParser.next();
		}
	}

	/**
	 * @param indent
	 * @return
	 */
	private static String getIndent(int indent)
	{
		String indentStr = "";
		for (int i=0; i<indent; i++)
			indentStr += "\t";
		return indentStr;
	}
}

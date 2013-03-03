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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class WmlReader
{
	private BufferedReader reader;

	private Queue<String> tokens = new LinkedList<>();
	private Queue<WmlTokenType> tokenTypes = new LinkedList<>();
	
	/**
	 * @param stream the input stream
	 */
	public WmlReader(InputStream stream) throws IOException
	{
		this(new InputStreamReader(stream, Charset.defaultCharset()));
	}

	/**
	 * @param reader
	 */
	public WmlReader(Reader reader) throws IOException
	{
		this.reader = new BufferedReader(reader);
		
		next();
	}

	/**
	 * @return
	 */
	public boolean hasNext()
	{
		return getToken() != null;
	}

	/**
	 * @return
	 */
	public WmlTokenType getTokenType()
	{
		return tokenTypes.peek();
	}
	
	public String getToken()
	{
		return tokens.peek();
	}

	/**
	 * 
	 */
	public void next() throws IOException
	{
		tokens.poll();
		tokenTypes.poll();

		while (tokens.isEmpty())
		{
			String line = reader.readLine();
			
			// test for end of stream
			if (line == null)
				return;

			int len = line.length();

			do
			{
				len = line.length();
				String comment = null;
				int commentIndex = line.indexOf('#');
				if (commentIndex != -1)
				{
					comment = line.substring(commentIndex + 1).trim();
					line = line.substring(0, commentIndex);
				}

				Pattern startTagPattern = Pattern.compile("\\s*\\[(\\w+)\\]");
				Pattern endTagPattern = Pattern.compile("\\s*\\[/(\\w+)\\]");

				line = processSingleToken(line, startTagPattern, WmlTokenType.START_TAG);
				line = processSingleToken(line, endTagPattern, WmlTokenType.END_TAG);
				line = processAttributeToken(line);

				if (comment != null && !comment.isEmpty())
				{
					tokens.offer(comment);
					tokenTypes.offer(WmlTokenType.COMMENT);
				}
				
				line = line.trim();
			}
			while (line.length() < len && line.length() > 0);	// line is shorter than before and contains more text
			
			if (line.length() > 0)
			{
				System.out.println("ERROR parsing " + line);
			}
		}
	}

	/**
	 * @param line
	 * @param kvpTagPattern
	 * @return
	 */
	private String processAttributeToken(String text)
	{
		final Pattern pattern = Pattern.compile("\\s*(\\w+)\\s*=\\s*(.+)");

		Matcher mTag = pattern.matcher(text);
		
		if (mTag.find())
		{
			// group indices start at 1 - group 0 contains the entire match
			String keyToken = mTag.group(1);
			String valToken = mTag.group(2);

			tokens.offer(keyToken);
			tokenTypes.offer(WmlTokenType.KEY);
			
			tokens.offer(valToken);
			tokenTypes.offer(WmlTokenType.VALUE);

			return text.substring(mTag.end());
		}
		
		return text;
	}

	/**
	 * @param line
	 * @param pattern the regex-pattern - returns the first group or the full match
	 * @return
	 */
	private String processSingleToken(String text, Pattern pattern, WmlTokenType tokenType)
	{
		Matcher mTag = pattern.matcher(text);
		
		if (mTag.find())
		{
			int st = mTag.start();
			if (st != 0)
				return text;
			
			// group indices start at 1 - group 0 contains the entire match
			String token = mTag.group(1);

			tokens.offer(token);
			tokenTypes.offer(tokenType);

			return text.substring(mTag.end());
		}
		
		return text;
	}

	
}

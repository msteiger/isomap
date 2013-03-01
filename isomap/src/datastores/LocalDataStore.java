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

package datastores;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides access to the default filesystem
 * @author Martin Steiger
 */
public class LocalDataStore implements DataStore
{
	private static final Logger log = LoggerFactory.getLogger(LocalDataStore.class);
	
	private final Path root;

	/**
	 * @param root the root folder (will be created if "create" is set)
	 * @param create whether the root folder should be created or not
	 */
	public LocalDataStore(Path root, boolean create) throws IOException
	{
		if (create)
		{
			// Calling createDirs() on a root folder 
			// leads to an IOException "Root directory does not exist"
			if (!root.equals(root.getRoot()))
			{		
				Files.createDirectories(root);
			}
		}
		
		if (!Files.exists(root, LinkOption.NOFOLLOW_LINKS))
			throw new IOException("Invalid root path");
		
		this.root = root.toAbsolutePath();
	}

	@Override
	public InputStream getInputStream(String first, String... more) throws IOException
	{
		Path relPath = FileSystems.getDefault().getPath(first, more);
		Path absPath = root.resolve(relPath);
		
		log.info("Reading " + absPath.toString());
		InputStream stream = Files.newInputStream(absPath, READ);
		
		return stream;
	}

	@Override
	public OutputStream getOutputStream(String first, String... more) throws IOException
	{
		Path relPath = FileSystems.getDefault().getPath(first, more);
		Path absPath = root.resolve(relPath);

		log.info("Writing to " + absPath.toString());
		
		Path folder = absPath.getParent();
		
		// Calling createDirs() on a root folder 
		// leads to an IOException "Root directory does not exist"
		if (!folder.equals(folder.getRoot()))
		{		
			Files.createDirectories(folder);
		}
		
		// opens the file for writing, creating the file if it doesn't exist, 
		// or initially truncating an existing regular-file to a size of 0 if it exists. 
		OutputStream stream = Files.newOutputStream(absPath, CREATE, TRUNCATE_EXISTING, WRITE);
		
		return stream;
	}

	@Override
	public void close() throws IOException
	{
		// nothing to do
	}
	
}

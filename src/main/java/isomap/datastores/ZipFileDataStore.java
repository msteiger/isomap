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

package isomap.datastores;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides access to a ZIP filesystem
 */
public class ZipFileDataStore implements DataStore {

    private static final Logger log = LoggerFactory.getLogger(ZipFileDataStore.class);

    private final URI zipFile;
    private final Path root;
    private final FileSystem fileSystem;

    /**
     * Creates/opens a zip file at the specified location. Does <b>not</b> create parent folders.
     * No specific root folder is used.
     * @param zipFile the path to the zip file
     * @param create true if the file should be created, if non-existant
     */
    public ZipFileDataStore(Path zipFile, boolean create) throws IOException {
        this(zipFile, create, "");
    }

    /**
     * Creates/opens a zip file at the specified location. Does <b>not</b> create parent folders.
     * @param zipFile the path to the zip file
     * @param create true if the file should be created, if non-existent
     * @param rootFirst the root folder
     * @param rootMore the rest of the path
     * @throws IOException
     */
    public ZipFileDataStore(Path zipFile, boolean create, String rootFirst, String... rootMore) throws IOException {
        URI zipUri = zipFile.toUri();
        URI uri = URI.create("jar:file:" + zipUri.getPath());

        FileSystem fileSys;

        try {
            fileSys = FileSystems.getFileSystem(uri);
            log.info("Opened existing file " + uri);
        } catch (FileSystemNotFoundException e) {
            // create must always be set:
            // if not a FileSystem without "create" could be created
            // and would be used ever after - there is no way a FileSystem could be removed
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            fileSys = FileSystems.newFileSystem(uri, env);

            log.info("Created file " + uri);
        }

        this.zipFile = uri;
        this.fileSystem = fileSys;
        this.root = fileSys.getPath(rootFirst, rootMore).toAbsolutePath();

        // checking if the root already exists is done in createDirs()
        if (create) {
            Files.createDirectories(root);
        }
    }

    @Override
    public void close() throws IOException {
        fileSystem.close();
    }

    @Override
    public InputStream getInputStream(String first, String... more) throws IOException {
        Path relPath = fileSystem.getPath(first, more);
        Path absPath = root.resolve(relPath);

        log.info("Reading " + zipFile + absPath.toString());
        InputStream stream = Files.newInputStream(absPath, StandardOpenOption.READ);

        return stream;
    }

    @Override
    public OutputStream getOutputStream(String first, String... more) throws IOException {
        Path relPath = fileSystem.getPath(first, more);
        Path absPath = root.resolve(relPath);

        log.info("Writing to " + zipFile + absPath.toString());
        Files.createDirectories(absPath.getParent());

        // opens the file for writing, creating the file if it doesn't exist,
        // or initially truncating an existing regular-file to a size of 0 if it exists.
        OutputStream stream = Files.newOutputStream(absPath, CREATE, TRUNCATE_EXISTING, WRITE);

        return stream;
    }
}

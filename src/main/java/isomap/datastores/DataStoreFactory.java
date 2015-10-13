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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Provides different data stores
 */
public class DataStoreFactory {

    public static DataStore openZippedDao(Path zipFile) throws IOException {
        return new ZipFileDataStore(zipFile, false);
    }

    /**
     * @param path
     * @return
     */
    public static DataStore createZippedDao(Path zipFile) throws IOException {
        return new ZipFileDataStore(zipFile, true);
    }

    public static DataStore openLocalDao(Path root) throws IOException {
        return new LocalDataStore(root, false);
    }

    public static DataStore createLocalDao(Path root) throws IOException {
        return new LocalDataStore(root, true);
    }

    public static DataStore getWebDao(URL root) {
        return new WebDataStore(root);
    }
}

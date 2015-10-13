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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;

import isomap.datastores.DataStore;
import isomap.datastores.DataStoreFactory;

/**
 * TODO Type description
 */
public class DataStoreTest {
    public static void main(String[] args) throws IOException {
        DataStore dao = DataStoreFactory.getWebDao(new URL("http://msteiger.bplaced.net/bfw/data/core"));

        String root = "images";
        String[] path = { "terrain", "bridge", "stonebridge-dock-n.png" };

        try (InputStream is = dao.getInputStream(root, path)) {
            DataStore zipDao = DataStoreFactory.createZippedDao(Paths.get("data", "bfw_images.zip"));

            try (OutputStream os = zipDao.getOutputStream(root, path)) {
                byte[] array = new byte[4096];

                int length;
                while ((length = is.read(array)) != -1) {
                    os.write(array, 0, length);
                }
            }

            zipDao.close();
        }

        DataStore dao2 = DataStoreFactory.openZippedDao(Paths.get("data", "bfw_images.zip"));

        try (InputStream is = dao2.getInputStream(root, path)) {
            DataStore dao42 = DataStoreFactory.createZippedDao(Paths.get("data", "bfw_images.zip"));

            try (OutputStream os = dao42.getOutputStream("test.png")) {
                byte[] array = new byte[4096];

                int length;
                while ((length = is.read(array)) != -1) {
                    os.write(array, 0, length);
                }
            }

            dao42.close();
        }

    }
}

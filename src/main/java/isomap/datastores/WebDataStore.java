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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides access to web resources.
 * Writing is not supported and will throw an {@link UnsupportedOperationException}
 */
public class WebDataStore implements DataStore {
    private static final Logger log = LoggerFactory.getLogger(WebDataStore.class);

    private URL root;

    /**
     * @param root the root address
     */
    public WebDataStore(URL root) {
        this.root = root;
    }

    @Override
    public InputStream getInputStream(String first, String... more) throws IOException {

        String url = root.toExternalForm();

        if (!url.endsWith("/"))
            url += "/";

        url += first;

        for (String ele : more) {
            url += "/" + ele;
        }

        log.info("Reading " + url);
        return new URL(url).openStream();
    }


    @Override
    public void close() throws IOException {
        // nothing to do
    }

    @Override
    public OutputStream getOutputStream(String first, String... more) throws IOException {
        // Does it even work with HTTP ?
        throw new UnsupportedOperationException();
    }
}

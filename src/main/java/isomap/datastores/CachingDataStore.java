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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * TODO Type description
 */
public class CachingDataStore implements DataStore {

    private DataStore source;
    private DataStore cache;

    /**
     * @param source
     * @param cache
     */
    public CachingDataStore(DataStore source, DataStore cache) {
        this.source = source;
        this.cache = cache;
    }

    @Override
    public void close() throws IOException {
        source.close();
        cache.close();
    }

    @Override
    public InputStream getInputStream(String first, String... more) throws IOException {
        try {
            return cache.getInputStream(first, more);
        } catch (IOException e) {
            try (InputStream is = source.getInputStream(first, more);
                OutputStream os = cache.getOutputStream(first, more);
                ByteArrayOutputStream copy = new ByteArrayOutputStream()) {

                writeToTwoStreams(is, os, copy);
                return new ByteArrayInputStream(copy.toByteArray());
            }
        }
    }

    private void writeToTwoStreams(InputStream is, OutputStream os, ByteArrayOutputStream copy) throws IOException {
        final int bufferSize = 64 * 1024;    // 64kB
        byte[] array = new byte[bufferSize];

        int length;
        while ((length = is.read(array)) != -1) {
            os.write(array, 0, length);
            copy.write(array, 0, length);
        }
    }

    @Override
    public OutputStream getOutputStream(String first, String... more) throws IOException     {
        final OutputStream cacheOut = cache.getOutputStream(first, more);
        final OutputStream sourceOut = source.getOutputStream(first, more);

        return new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                cacheOut.write(b);
                sourceOut.write(b);
            }

            @Override
            public void write(byte[] b) throws IOException {
                cacheOut.write(b);
                sourceOut.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                cacheOut.write(b, off, len);
                sourceOut.write(b, off, len);
            }

            @Override
            public void flush() throws IOException {
                cacheOut.flush();
                sourceOut.flush();
            }

            @Override
            public void close() throws IOException {
                cacheOut.close();
                sourceOut.close();
            }
        };
    }
}

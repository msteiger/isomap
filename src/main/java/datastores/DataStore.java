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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides read/write access to different data stores. <b>Never forget</b> to call {@link Closeable#close()}.
 * @author Martin Steiger
 */
public interface DataStore extends Closeable
{
    /**
     * @param first the first path token
     * @param more the remaining path tokens
     * @return an input stream that contains the data
     */
    public InputStream getInputStream(String first, String... more) throws IOException;

    /**
     * @param first the first path token
     * @param more the remaining path tokens
     * @return an output stream for the data
     */
    public OutputStream getOutputStream(String first, String... more) throws IOException;
}

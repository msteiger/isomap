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

package common;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Map} decorator that returns 
 * a default value if no value exists in the delegate map
 * for a given key.
 * This class is similar to apache.commons.collections.map.DefaultedMap.
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values 
 * @author Martin Steiger
 */
public class DefaultValueMap<K, V> extends MapAdapter<K, V>
{
    private final V defValue;

    /**
     * Uses an empty {@link HashMap} as target
     * @param defValue the default value
     */
    public DefaultValueMap(V defValue)
    {
        this(defValue, new HashMap<K, V>());
    }

    /**
     * @param map the original map
     * @param defValue the default value (not <code>null</code>)
     */
    public DefaultValueMap(V defValue, Map<K, V> map)
    {
        super(map);

        if (defValue == null) 
            throw new NullPointerException("Default value cannot be null - use a regular map instead");

        this.defValue = defValue;
    }

    @Override
    public V get(Object key)
    {
        V value = super.get(key);
        return (value == null) ? defValue : value;
    }

    @Override
    public V put(K key, V value)
    {
        if (defValue.equals(value))
            return remove(key);
        
        return super.put(key, value);
    }
}

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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Delegates all method calls to the given map
 * @author Martin Steiger
 */
public class MapAdapter<K, V> implements Map<K, V>
{
    private Map<K, V> delegate;

    /**
     * @param delegate
     */
    public MapAdapter(Map<K, V> delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public int size()
    {
        return delegate.size();
    }

    @Override
    public boolean isEmpty()
    {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return delegate.containsValue(value);
    }

    @Override
    public V get(Object key)
    {
        return delegate.get(key);
    }

    @Override
    public V put(K key, V value)
    {
        return delegate.put(key, value);
    }

    @Override
    public V remove(Object key)
    {
        return delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        delegate.putAll(m);
    }

    @Override
    public void clear()
    {
        delegate.clear();
    }

    @Override
    public Set<K> keySet()
    {
        return delegate.keySet();
    }

    @Override
    public Collection<V> values()
    {
        return delegate.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return delegate.entrySet();
    }

    @Override
    public boolean equals(Object o)
    {
        return delegate.equals(o);
    }

    @Override
    public int hashCode()
    {
        return delegate.hashCode();
    }

    @Override
    public String toString() 
    {
        return delegate.toString();
    }
    
    
}


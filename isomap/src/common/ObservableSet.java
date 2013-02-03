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

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class ObservableSet<T> extends AbstractSet<T>
{
	private final Set<T> back;
	
	private final Collection<CollectionListener<T>> listeners = new ArrayList<CollectionListener<T>>();
	
	/**
	 * @param back the backing list implementation
	 */
	public ObservableSet(Set<T> backingList)
	{
		this.back = backingList;
	}
	
	public void addListener(CollectionListener<T> listener)
	{
		listeners.add(listener);
	}

	public void removeListener(CollectionListener<T> listener)
	{
		listeners.remove(listener);
	}

	@Override
	public boolean add(T t)
	{
		boolean changed = back.add(t);
		
		if (changed)
		{
			for (CollectionListener<T> obs : listeners)
			{
				obs.added(t);
			}
		}
		
		return changed;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object t)
	{
		boolean changed = back.remove(t);
		
		if (changed)
		{
			for (CollectionListener<T> obs : listeners)
			{
				obs.removed((T)t);
			}
		}
		
		return changed;		
	}
	
	@Override
	public int size()
	{
		return back.size();
	}

	@Override
	public Iterator<T> iterator()
	{
		return back.iterator();
	}
}

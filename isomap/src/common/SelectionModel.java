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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Observable;
import java.util.Set;

/**
 * Contains a set of selected elements and notifies attached
 * {@link Observer}s whenever the content changes 
 * @author Martin Steiger
 */
public class SelectionModel<T> extends Observable
{
	private final Set<T> selection = new LinkedHashSet<>();

	/**
	 * Notifies only if selection is changed
	 * @param elements a set of elements that will become the new selection
	 */
	public void set(Set<T> elements)
	{
		if (selection.equals(elements))	// slow?
			return;
		
		selection.clear();
		selection.addAll(elements);
		
		setChanged();
		notifyObservers();
	}

	/**
	 * Notifies only if selection is changed
	 * @param element the element to add
	 */
	public void add(T element)
	{
		if (selection.add(element))
		{
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Notifies only if selection is changed
	 * @param element the element to remove
	 */
	public void remove(T element)
	{
		if (selection.remove(element))
		{
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Clear the selection.
	 * Notifies only if selection is changed
	 */
	public void clear()
	{
		if (!selection.isEmpty())
		{
			selection.clear();
			
			setChanged();
			notifyObservers();
		}
	}
	
	public Set<T> getSelection()
	{
		return Collections.unmodifiableSet(selection);
	}
}

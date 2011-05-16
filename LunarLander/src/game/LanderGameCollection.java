package game;

import java.util.*;

import domain.IMoveable;
import domain.LandingPad;
import domain.Meteor;

/**
 * Implements a generic collection of objects for the Lunar Lander game.
 *
 * @param <E> The type of object to store in the collection.
 */
public class LanderGameCollection<E> implements java.util.Collection<E>
{
	/**
	 * The internal collection of items.
	 */
	ArrayList<E> gameObjects;
	
	public LanderGameCollection()
	{
		gameObjects = new ArrayList<E>();
	}
	
	/**
	 * Implements the Iterator interface for the LanderGameCollection.
	 *
	 * @param <T> The type of object that will be iterated over.
	 */
	private class LanderGameIterator<T> implements Iterator<T>
	{
		/**
		 * A reference to the internal list of objects from the GameObjectCollection.
		 */
		private ArrayList<T> myList;
		/**
		 * Keeps track of the current place within the ArrayList.
		 */
		private int currentIndex;
		
		/**
		 * @param list A list of objects from the GameObjectCollection.
		 */
		public LanderGameIterator(ArrayList<T> list)
		{
			myList = list;
			currentIndex = -1;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext()
		{
			return (currentIndex + 1) < myList.size();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public T next()
		{
			if (hasNext())
			{
				++currentIndex;
				return myList.get(currentIndex);
			}
			else
				throw new NoSuchElementException();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove()
		{
			if (currentIndex > -1)
				myList.remove(currentIndex);
			else
				throw new NoSuchElementException();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(E o)
	{
		return gameObjects.add(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends E> c)
	{
		return gameObjects.addAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	public void clear()
	{
		gameObjects.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o)
	{
		return gameObjects.contains(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c)
	{
		return gameObjects.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty()
	{
		return gameObjects.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	public Iterator<E> iterator()
	{
		return new LanderGameIterator<E>(gameObjects);
	}
	
	public LanderGameCollection<Meteor> meteors()
	{
		LanderGameCollection<Meteor> result = new LanderGameCollection<Meteor>();
		for (E gameObject : gameObjects)
		{
			if (gameObject instanceof Meteor)
				result.add((Meteor)gameObject);
		}
		return result;
	}
	
	public LanderGameCollection<LandingPad> landingPads()
	{
		LanderGameCollection<LandingPad> result = new LanderGameCollection<LandingPad>();
		for (E gameObject : gameObjects)
		{
			if (gameObject instanceof LandingPad)
				result.add((LandingPad)gameObject);
		}
		return result;
	}
	
	public LanderGameCollection<IMoveable> movingObjects()
	{
		LanderGameCollection<IMoveable> result = new LanderGameCollection<IMoveable>();
		for (E gameObject : gameObjects)
		{
			if (gameObject instanceof IMoveable)
				result.add((IMoveable)gameObject);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o)
	{
		return gameObjects.remove(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c)
	{
		return gameObjects.removeAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c)
	{
		return gameObjects.retainAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	public int size()
	{
		return gameObjects.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray()
	{
		return gameObjects.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(T[])
	 */
	public <T> T[] toArray(T[] a)
	{
		return gameObjects.toArray(a);
	}

}

import java.util.Comparator;
import java.util.Random;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.ArrayList;

//BinarySearchTree extends LinkedBinaryTree
public class BinarySearchTree<K, V> extends LinkedBinaryTree<Entry<K,V>>
{
	//Instance variables
	protected Comparator<K> C;
	protected Position<Entry<K, V>> actionPos;
	protected int numEntries = 0;

	//Consructor intializes comparator and creates a root with a value of null
	public BinarySearchTree()
	{
		C = new DefaultComparator();
		addRoot(null);
	}
	//Static class BSTEntry implements Entry
	protected static class BSTEntry<K, V> implements Entry<K, V>
	{
		//Instance variables for class
		protected K key;
		protected V value;
		protected Position<Entry<K, V>> position;

		//Constructor intializes key, value and position
		BSTEntry(K k, V v, Position<Entry<K, V>> p)
		{
			key = k;
			value = v;
			position = p;
		}
		//Returns key
		public K getKey() { return key; }
		//Return value
		public V getValue() { return value; }
		//Returns position
		public Position<Entry<K, V>> position() { return position; }
	}
	//Returns key of given position
	protected K key (Position<Entry<K, V>> position)
	{
		return position.element().getKey();
	}
	//Returns value of given position
	protected V value (Position<Entry<K, V>> position)
	{
		return position.element().getValue();
	}
	//Returns key and value of given position
	protected Entry<K, V> entry (Position<Entry<K, V>> position)
	{
		return position.element();
	}
	//Replaces entry at given position
	protected void replaceEntry (Position<Entry<K, V>> pos, Entry<K, V> entry)
	{
		((BSTEntry<K, V>) entry).position = pos;
		replace(pos, entry);
	}
	//Checks that key is valid
	protected void checkKey(K key) throws InvalidKeyException
	{
		if(key == null)
			throw new InvalidKeyException("Null key");
	}
	//Checks that entry is valid
	protected void checkEntry(Entry<K, V> ent) throws InvalidKeyException
	{
		if (ent == null || !(ent instanceof BSTEntry))
			throw new InvalidEntryException("Invalid entry");
	}
	protected Entry<K, V> insertAtExternal(Position<Entry<K, V>> v, Entry<K, V> e)
	{
		if(isExternal (v))
		{
			BTNode<Entry<K, V>> lv = new BTNode (null, (BTNode<Entry<K, V>>) v, null, null);
			BTNode<Entry<K, V>> rv = new BTNode (null, (BTNode<Entry<K, V>>) v, null, null);
		 	((BTNode<Entry<K, V>>) v).setLeft(lv);
		 	((BTNode<Entry<K, V>>) v).setRight(rv);
		}
		replaceEntry(v, e);
		numEntries++;
		return e;
	}
	//Searches tree for key
	protected Position<Entry<K, V>> treeSearch(K key, Position<Entry<K, V>> pos)
	{
		if (isExternal(pos))
			return pos;
		else
		{
			K curKey = key(pos);
			int comp = C.compare(key, curKey);
			if (comp < 0)
				return treeSearch(key, right(pos));
		}
		return pos;
	}
	/**Method not needed for sorting purposes

	protected void addAll(PositionList<Entry<K, V>> L, Position<Entry<K, V>> v, K k)
	{
		if (isExternal(v))
			return;
		Position<Entry<K, V>> pos = treeSearch(k, v);
		if (!isExternal(pos))
		{
			addAll(L, left(pos), k);
			L.addLast(pos.element());
			addAll(L, right(pos), k);
		}
	}*/

	// public int size (){ return numEntries;} Method not needed for sorting purposes

	//public boolean isEmpty() { return size == 0; } Method not needed for sorting purposes

	/** Method not needed for sorting purposes

	public Entry<K, V> find (K key)throws InvalidKeyException
	{
		checkKey(key);
		Position<Entry<K, V>> curPos = treeSearch(key, root());
		if (isInternal(curPos))
			return entry(curPos);
		return null;
	}*/

	/** Method not needed for sorting purposes

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException
	{
		checkKey(key);
		PositionList<Entry<K, V>> L = new LinkedPositionList<Entry<K, V>>();
		addAll(L, root(), key);
		return (Iterable) L;
	} */

	public Entry<K, V> insert (K k, V v)
	{
		checkKey(k);
		Position<Entry<K, V>> insPos = treeSearch(k, root());
		while (!isExternal(insPos))
			insPos = treeSearch(k, left(insPos));
		actionPos = insPos;
		return insertAtExternal(insPos, new BSTEntry<K, V>(k, v, insPos));
	}
	//Main method to test sorting abilities
	public static void main (String [] args)
	{
		Random random = new Random();
		//Integer to test with
		BinarySearchTree<Integer, Integer> intTree = new BinarySearchTree<Integer, Integer>();

		//Using 10 integers but could increase to more
		int n = 10;

		//View the unsorted integers
		System.out.println("Unsorted integers are: ");
		for (int i = 0; i < n; i++)
		{
			int m = random.nextInt(100);
			intTree.insert(m, m);
			System.out.print(m + " ");
		}
		System.out.println("Sorting integers.....");
		//View the sorted integers
		System.out.println("\nSorted integers are: ");
		int count = 0;
		for (Position<Entry<Integer, Integer>> in: intTree.positions())
		{
			if (intTree.isInternal(in))
				System.out.print((in.element()).getKey()+ " ");
		}

	}
}

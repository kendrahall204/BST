import java.util.ArrayList;
import java.util.Random;

//LinkedBinary tree implements the BinaryTree ADT
public class LinkedBinaryTree<E> implements BinaryTree<E>
{
	//Instance variables
	protected BTPosition<E> root;
	protected int size;

	//Constructor initializes root to null and size to 0
	public LinkedBinaryTree()
	{
		root = null;
		size = 0;
	}

	public int size() { return size; }
	public boolean isEmpty() { return size() == 0; }

	//Returns true if the position given is internal
	public boolean isInternal(Position<E> v) throws InvalidPositionException
	{
		checkPosition(v);
		return (hasLeft(v) || hasRight(v));
	}
	//Returns true if the position given is external
	public boolean isExternal(Position<E> v) throws InvalidPositionException
	{
		checkPosition(v);
		return !isInternal(v);
	}
	//Returns true if position equals the root
	public boolean isRoot(Position<E> v) throws InvalidPositionException
	{
		checkPosition(v);
		return (v == root());
	}
	//Returns true if the position has a left child
	public boolean hasLeft(Position<E> v) throws InvalidPositionException
	{
		BTPosition<E> vv = checkPosition(v);
		return (vv.getLeft() != null);
	}
	//Returns true if position has a right child
	public boolean hasRight(Position<E> v) throws InvalidPositionException
	{
		BTPosition<E> vv = checkPosition(v);
		return (vv.getRight() != null);
	}
	//Returns the root
	public Position<E> root() throws EmptyTreeException
	{
		if (root == null)
			throw new EmptyTreeException("Tree is empty"); //Exception if tree is empty
		return root;
	}
	//Returns the left child of a given position
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException
	{
		BTPosition<E> vv = checkPosition(v);
		Position<E> leftPos = vv.getLeft();
		if (leftPos == null)
			throw new BoundaryViolationException("No left child"); //Exception if there is no left child
		return leftPos;
	}
	//Return the right child of a given position
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException
	{
		BTPosition<E> vv = checkPosition(v);
		Position<E> rightPos = vv.getRight();
		if (rightPos == null)
			throw new BoundaryViolationException("No right child");
		return rightPos;
	}
	//Returns the parent of a given positoin
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException
	{
		BTPosition<E> vv = checkPosition(v);
		Position<E> parentPos = vv.getParent();
		if (parentPos == null)
			throw new BoundaryViolationException("No parent");
		return parentPos;
	}
	//Returns an Iterable collection of children
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException
	{
		ArrayList<Position<E>> children = new ArrayList<Position<E>>();
		if (hasLeft(v))
			children.add(left(v));
		if (hasRight(v))
			children.add(right(v));
		return children;
	}
	//Returns an Iterable collection of positions
	public Iterable<Position<E>> positions()
	{
		ArrayList<Position<E>> positions = new ArrayList<Position<E>>();
		if (size != 0)
			inorderPositions(root(), positions);
		return positions;
	}
	public Iterable<E> iterator()
	{
		Iterable<Position<E>> positions = positions();
		ArrayList<E> elements = new ArrayList<E>();
		for (Position<E> pos: positions)
			elements.add(pos.element());
		return elements;
	}
	//Replaces the element of the given position with the new given element
	public E replace(Position<E> v, E o) throws InvalidPositionException
	{
		BTPosition<E> vv = checkPosition(v);
		E temp = v.element();
		vv.setElement(o);
		return temp;
	}
	//Returns the sibling of a given position
	public Position<E> sibling(Position<E> v) throws InvalidPositionException, BoundaryViolationException
	{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> parentPos = vv.getParent();
		if (parentPos != null)
		{
			BTPosition<E> sibPos;
			BTPosition<E> leftPos = parentPos.getLeft();
				if (leftPos == vv)
					sibPos = parentPos.getRight();
				else sibPos = parentPos.getLeft();
				if (sibPos != null)
					return (Position<E>) sibPos;
		}
		throw new BoundaryViolationException ("No sibling");
	}
	//Creates root from given element
	public Position<E> addRoot(E e) throws TreeNotEmptyException
	{
		if(!isEmpty())
			throw new TreeNotEmptyException("Tree already has a root"); //Exception if tree already has a root
		size = 1;
		root = createNode(e, null, null, null);
		return root;
	}
	//Inserts the given element as the left child of the given position
	public Position<E> insertLeft(Position<E> v, E e) throws InvalidPositionException
	{
		BTPosition<E> vv = checkPosition(v);
		Position<E> leftPos = vv.getLeft();
		if(leftPos != null)
			throw new InvalidPositionException("Node already has a left child");
		BTPosition<E> ww = createNode(e, vv, null, null);
		vv.setLeft(ww);
		size++;
		return ww;
	}
	//Inserts the given element as the right child of the given position
	public Position<E> insertRight(Position<E> v, E e) throws InvalidPositionException
	{
		BTPosition<E> vv = checkPosition(v);
		Position<E> rightPos = vv.getRight();
		if (rightPos != null)
			throw new InvalidPositionException("Node already has a right child");
		BTPosition<E> ww = createNode(e, vv, null, null);
		vv.setRight(ww);
		size++;
		return ww;
	}
	//Removes given position
	public E remove(Position<E> v, E e) throws InvalidPositionException
	{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		BTPosition<E> rightPos = vv.getRight();
		if (leftPos != null && rightPos != null)
			throw new InvalidPositionException("Cannot remove node with two children");
		BTPosition<E> ww;
		if (leftPos != null)
			ww = leftPos;
		else if (rightPos != null)
			ww = rightPos;
		else
			ww = null;
		if (vv == root)
		{
			if (ww != null)
				ww.setParent(null);
			root = ww;
		}
		else
		{
			BTPosition<E> uu = vv.getParent();
			if (vv == uu.getLeft())
				uu.setLeft(ww);
			else
				uu.setRight(ww);
			if (ww != null)
				ww.setParent(uu);
		}
		size --;
		return v.element();
	}
	//Checks that position is valid
	protected BTPosition<E> checkPosition(Position<E> v) throws InvalidPositionException
	{
		if (v == null || !(v instanceof BTPosition))
			throw new InvalidPositionException("Invalid Posiiton");
		return (BTPosition<E>) v;
	}
	//Creates new BTNode
	protected BTPosition<E> createNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right)
	{
		return new BTNode<E>(element, parent, left, right);
	}
	//In order traversal
	protected void inorderPositions(Position<E> v, ArrayList<Position<E>> pos) throws InvalidPositionException
	{
		if (hasLeft(v))
			inorderPositions(left(v), pos);
		pos.add(v);
		if (hasRight(v))
			inorderPositions(right(v), pos);
	}
	//Returns depth of tree
	public int depth (Tree<E> T, Position<E> v)
	{
		if (T.isRoot(v))
			return 0;
		else
			return 1 + depth(T, T.parent(v));
	}
	//Returns height of tree
	public int height (Tree<E> T)
	{
		int h = 0;
		for (Position<E> v : T.positions())
		{
			if (T.isExternal(v))
				h = Math.max(h, depth(T, v));
		}
		return h;
	}
}
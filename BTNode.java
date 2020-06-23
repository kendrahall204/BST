//BTNode class
public class BTNode<E> implements BTPosition<E>
{
	//Private instances
	private E element;
	private BTPosition<E> left, right, parent;
	//Constructor sets links to element, parent, and left and right nodes
	public BTNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right)
	{
		setElement(element);
		setParent(parent);
		setLeft(left);
		setRight(right);
	}
	public E element() { return element; }
	public void setElement(E o) { element = o; }
	public BTPosition<E> getLeft() { return left; }
	public BTPosition<E> getRight() { return right; }
	public BTPosition<E> getParent() { return parent; }
	public void setLeft(BTPosition<E> v) { left = v; }
	public void setRight(BTPosition<E> v) { right = v; }
	public void setParent(BTPosition<E> v) { parent = v; }
}
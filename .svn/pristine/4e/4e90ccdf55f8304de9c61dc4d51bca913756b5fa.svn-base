package excalibur.game.presentation.tools;

public class MyLinkedStack<T> implements MyStack<T> {
	/**
	 * 栈顶指针
	 */
	private Node top;
	/**
	 * 栈的长度
	 */
	private int size;
	
	public MyLinkedStack() {
		top = null;
		size = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public void clear() {
		top = null;
		size = 0;
	}
	
	@Override
	public int length() {
		return size;
	}
	
	@Override
	public boolean push(T data) {
		Node node = new Node();
		node.data = data;
		node.pre = top;
		// 改变栈顶指针
		top = node;
		size++;
		return true;
	}
	
	@Override
	public T pop() {
		if (top != null) {
			Node node = top;
			// 改变栈顶指针
			top = top.pre;
			size--;
			return node.data;
		}
		return null;
	}
	
	/**
	 * 将数据封装成结点
	 */
	private final class Node {
		private Node pre;
		private T data;
	}
}
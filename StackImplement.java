package project1;

//Name: Duong Phan
//NetID: dphan7
//Lab section: Gavett 244 TR 2-3.15pm
//Lab6: Stack

import lab5.URLinkedList;

public class StackImplement<E> implements Stack<E> {
	URLinkedList<E> stack;

	public StackImplement() {
		stack = new URLinkedList<E>();
	}

	public static void main(String[] args) {
		StackImplement<Integer> intStack = new StackImplement<Integer>();
		intStack.print();
//		intStack.push(1);
//		intStack.print();
//		intStack.push(2);
//		intStack.print();
		intStack.push(3);
//		intStack.print();
		intStack.pop();
//		intStack.print();
		System.out.println(intStack.peek());
		intStack.push(1);
		intStack.print();

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return stack.size() == 0;
	}

	@Override
	// Insert the given object into the front of stack
	public void push(E x) {
		stack.addFirst(x);
	}

	@Override
	// Removes and returns the top (most recently inserted) item from the stack
	public E pop() {
		if (stack.isEmpty())
			return null;
		if (stack.size() == 1) {
			E value = stack.pollFirst();
			stack = new URLinkedList<E>();
			return value;
		}
		return stack.pollFirst();
	}

	@Override
	// Return the value of the top of the stack
	public E peek() {
		return stack.peekFirst();
	}

	// Print the stack
	public void print() {
		if (stack == null)
			return;
		stack.print();
	}

}

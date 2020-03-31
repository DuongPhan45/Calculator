package project1;

//Name: Duong Phan
//NetID: dphan7
//Lab section: Gavett 244 TR 2-3.15pm
//Lab6: Stack

import lab5.URLinkedList;

public class QueueImplement<E> implements Queue<E> {
	URLinkedList<E> queue;

	public QueueImplement() {
		queue = new URLinkedList<E>();
	}

	public static void main(String[] args) {
//		QueueImplement<Integer> queue = new QueueImplement<Integer>();
//		queue.print();
//		queue.enqueue(1);
//		queue.print();
//		queue.enqueue(2);
//		queue.print();
//		queue.enqueue(3);
//		queue.print();
//		System.out.println(queue.dequeue());
//		queue.print();
//		System.out.println(queue.peek());
//		queue.print();

	}

	@Override
	public boolean isEmpty() {
		return queue.size() == 0;
	}

	@Override
	// Inserts an item into the end of your queue
	public void enqueue(E x) {
		queue.addLast(x);
	}

	@Override
	// Removes and returns the item at the front of the list
	public E dequeue() {
		if (isEmpty())
			return null;
		return queue.pollFirst();
	}

	@Override
	// Return, but does not delete, the first item in the queue
	public E peek() {
		return queue.peekFirst();
	}

	// Print the queue
	public void print() {
		if (queue == null)
			return;
		queue.print();
	}

}

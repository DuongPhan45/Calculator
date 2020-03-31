package project1;

//Name: Duong Phan
//NetID: dphan7
//Lab Section: Gavett 244, TR 2-3.15pm

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class URLinkedList<E> implements URList<E>, Iterable<E> {

	class URNode<E> { // Doubly linked list node

		private E e; // Value for this node
		private URNode<E> n; // Reference to next node in list
		private URNode<E> p; // Reference to previous node

		// Constructors
		URNode(E it, URNode<E> inp, URNode<E> inn) {
			e = it;
			p = inp;
			n = inn;
		}

		URNode(URNode<E> inp, URNode<E> inn) {
			p = inp;
			n = inn;
		}

		// Get and set methods for the data members
		public E element() {
			return e;
		} // Return the value

		public E setElement(E it) {
			return e = it;
		} // Set element value

		public URNode<E> next() {
			return n;
		} // Return next link

		public URNode<E> setNext(URNode<E> nextval) {
			return n = nextval;
		} // Set next link

		public URNode<E> prev() {
			return p;
		} // Return prev link

		public URNode<E> setPrev(URNode<E> prevval) {
			return p = prevval;
		} // Set prev link

	}

	URNode<E> first;
	URNode<E> last;
	int size = 0;

	// ****Constructer and print method
	public URLinkedList() {
		first = new URNode<E>(null, last);
		last = new URNode<E>(first, null);
	}

	public void print() {
		if (isEmpty())
			System.out.println("Empty list");
		URNode<E> pointer = first;
		while (pointer.e != null) {
			System.out.printf("%-5d", pointer.e);
			if (pointer.n != null) {
				pointer = pointer.n;
			} else {
				break;
			}
		}
		System.out.println();
	}

	@Override
	// Appends the specified element to the end of this list
	public boolean add(E e) {
		// If the list is empty, make the new URNode the first
		if (first.e == null) {
			first.e = e;
			size++;
			return true;
		}
		URNode<E> current = first;
		// Traverse to the last
		while (current.n != null)
			current = current.n;

		// Create a new end node
		URNode<E> endNode = new URNode<E>(e, current, null);
		// Change next of last node
		current.n = endNode;
		// Update the new last
		last = endNode;
		// Update size
		size++;
		return true;

	}

	@Override
	// Inserts the specified element at the specified position in this list
	// Suppose the linked list is 0-index-base
	public void add(int index, E element) {

		// Throw Out of Bound exception if the index is not smaller than the size of
		// linkedlist
		Objects.checkIndex(index, size);

		// Do not allow null value
		if (element == null) {
			System.out.println("Cannot insert null");
			return;
		}
		URNode<E> current = first;
		// traverse to the index position
		for (int i = 0; i < index - 1; i++) {
			current = current.n;
		}
		// Create a new Node
		URNode<E> newNode = new URNode<E>(element, current, current.n);
		// Make new node the previous of current.next
		current.n.p = newNode;
		// Make new node the next of current
		current.n = newNode;
		// Update size
		size++;

	}

	@Override
	// Appends all of the elements in the specified collection to the end of this
	// list,
	// in the order that they are returned by the specified collection's iterator
	public boolean addAll(Collection<? extends E> c) {
		for (E element : c) {
			add(element);
		}
		return true;
	}

	@Override
	// Inserts all of the elements in the specified collection into this list
	// at the specified position
	public boolean addAll(int index, Collection<? extends E> c) {
		// Throw Out of Bound exception if the index is not smaller than the size of
		// linkedlist
		Objects.checkIndex(index, size);

		URNode<E> current = first;
		// Traverse to the index position
		for (int i = 0; i < index - 1; i++) {
			current = current.n;
		}
		// Record the node after the index
		URNode<E> contNode = current.n;
		// Insert the collection
		for (E element : c) {
			current.n = new URNode<E>(element, current, null);
			// Update size
			size++;
			current = current.n;
		}
		// Continue the last part of the linked list
		current.n = contNode;
		return true;
	}

	@Override
	// Removes all of the elements from this list
	public void clear() {
		first = new URNode<E>(null, last);
		last = new URNode<E>(first, null);
		// Update size
		size = 0;
	}

	// Returns true if this list contains the specified element.
	public boolean contains(Object o) {
		URNode<E> current = first;
		while (current != null) {
			if (current.e.equals(o))
				return true;
			current = current.n;
		}
		return false;
	};

	// Returns true if this list contains all of the elements of the specified
	// collection
	public boolean containsAll(Collection<?> c) {
		for (Object element : c) {
			if (!contains(element))
				return false;
		}
		return true;
	}

	// Compares the specified object with this list for equality.
	// Returns true if both contain the same elements. Ignore capacity
	public boolean equals(Object o) {
		// Check if the Object is a URLinkedList
		if (!(o instanceof URLinkedList<?>))
			return false;
		Object[] array = (Object[]) o;
		for (Object element : array) {
			if (!contains(element))
				return false;
		}
		return true;
	}

	// Returns the element at the specified position in this list.
	public E get(int index) {
		// Throw Out of Bound exception if the index is not smaller than the size of
		// linkedlist
		Objects.checkIndex(index, size);
		if (index == 0)
			return first.e;
		URNode<E> current = first;

		// Traverse to the index position
		for (int i = 0; i < index - 1; i++) {
			current = current.n;
		}
		return current.n.e;
	}

	@Override
	// Returns the index of the first occurrence of the specified element in this
	// list,
	// or -1 if this list does not contain the element.
	public int indexOf(Object o) {
		if (!contains(o))
			return -1;
		URNode<E> current = first;
		int index = 0;
		while (!current.e.equals(o) && current.n != null) {
			current = current.n;
			index++;
		}
		return index;
	}

	// Returns true if this list contains no elements.
	public boolean isEmpty() {
		return size == 0;
	};

	// Returns an iterator over the elements in this list in proper sequence.
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {
			int index = 0;

			@Override
			public boolean hasNext() {
				return index < size && get(index) != null;
			}

			@Override
			public E next() {
				return get(index++);
			}
		};
		return it;
	};

	// Removes the element at the specified position in this list
	public E remove(int index) {
		if (isEmpty())
			throw new NullPointerException();
		// Throw Out of Bound exception if the index is not smaller than the size of
		// linkedlist
		Objects.checkIndex(index, size + 1);

		URNode<E> current = first;
		// Traverse to the index position
		for (int i = 0; i < index - 1; i++) {
			current = current.n;
		} // Remove the element
		if (current.n != null) {
			current.n.p = current.p;
		} else {
			last = current.p;
		}
		if (current.p != null) {
			current.p.n = current.n;
		} else {
			first = current.n;
		}
		// Update size
		size--;
		return null;
	}

	@Override
	// Removes the first occurrence of the specified element from this list,
	// if it is present
	public boolean remove(Object o) {
		if (isEmpty())
			return false;
		if (!contains(o))
			return false;
		URNode<E> current = first;
		// Traverse to the position
		while (!(current.e.equals(o))) {
			current = current.n;
		}
		// Remove the element
		if (current.n != null) {
			current.n.p = current.p;
		} else {
			last = current.p;
		}
		if (current.p != null) {
			current.p.n = current.n;
		} else {
			first = current.n;
		}
		// Update size
		size--;
		return true;
	}

	// Removes from this list all of its elements that are contained
	// in the specified collection
	public boolean removeAll(Collection<?> c) {
		for (Object element : c) {
			remove(element);
		}
		if (isEmpty()) {
			first = new URNode<E>(null, last);
			last = new URNode<E>(first, null);
		}
		return true;
	}

	// Replaces the element at the specified position in this list
	// with the specified element
	public E set(int index, E element) {
		// Throw Out of Bound exception if the index is not smaller than the size of
		// linkedlist
		Objects.checkIndex(index, size);

		URNode<E> current = first;
		// Traverse to the index position
		for (int i = 0; i < index - 1; i++) {
			current = current.n;
		}
		current.e = element;
		return element;
	}

	// Returns the number of elements in this list.
	public int size() {
		return size;
	}

	// Returns a view of the portion of this list
	// between the specified fromIndex, inclusive, and toIndex, exclusive.
	public URList<E> subList(int fromIndex, int toIndex) {
		// Check the validity of index
		Objects.checkIndex(fromIndex, size);
		Objects.checkIndex(toIndex, size);
		if (toIndex < fromIndex)
			throw new InvalidParameterException();

		// Create a new URArrayList
		URArrayList<E> subList = new URArrayList<E>();
		// Decide the capacity of the data array in new list
		subList.data = (E[]) new Object[toIndex - fromIndex + 1];
		subList.size = toIndex - fromIndex + 1;
		// Copy the data array
		int count = 0;
		for (int i = fromIndex; i <= toIndex; i++) {
			subList.data[count] = get(i);
			count++;
		}
		return subList;
	}

	// Returns an array containing all of the elements in this list
	// in proper sequence (from first to the last element).
	public Object[] toArray() {
		// Create a new array
		Object[] array = new Object[size];
		URNode<E> current = first;
		for (int i = 0; i < array.length; i++) {
			array[i] = current.e;
			current = current.n;
		}
		return array;
	};

	// Inserts the specified element at the beginning of this list.
	public void addFirst(E e) {
		// If the list is empty, make the new URNode the first
		if (first.e == null) {
			first.e = e;
			size++;
			return;
		}
		// Create a new node
		URNode<E> newFirst = new URNode<E>(e, null, first);
		first.p = newFirst;
		// Update first
		first = newFirst;
		// Update size
		size++;
	}

	// Appends the specified element to the end of this list.
	public void addLast(E e) {
		// If the list is empty, make the new URNode the first
		if (first.e == null) {
			first.e = e;
			size++;
			return;
		}
		URNode<E> current = first;
		// Traverse to the last
		while (current.n != null)
			current = current.n;

		// Create a new end node
		URNode<E> endNode = new URNode<E>(e, current, null);
		// Change next of last node
		current.n = endNode;
		// Update the new last
		last = endNode;
		// Update size
		size++;
	}

	// Retrieves, but does not remove, the first element of this list, or returns
	// null if this list is empty.
	public E peekFirst() {
		if (isEmpty())
			return null;
		return first.e;
	}

	// Retrieves, but does not remove, the last element of this list, or returns
	// null if
//	this list is empty.
	public E peekLast() {
		if (isEmpty())
			return null;
		if (size == 1)
			return first.e;
		return last.e;
	}

	// Retrieves and removes the first element of this list, or returns null if this
	// list
//	is empty.
	public E pollFirst() {
		E value = first.e;
		remove(0);
		return value;
	}

	// Retrieves and removes the last element of this list, or returns null if this
	// list
//	is empty.
	public E pollLast() {
		E value = last.e;
		remove(size);
		return value;
	}

	public static void main(String args[]) {

		// Create an URLinkedList
		URLinkedList<Integer> linkedList = new URLinkedList<Integer>();
//		linkedList.add(0, 2);

//		for (Integer i = 0; i < 10; i++) {
//
//			linkedList.add(i);
//		}

		// Create a collection with same content
		Collection<Integer> collection = new ArrayList<Integer>();
		for (int j = 0; j < 10; j++) {
			collection.add(j);
		}

//		linkedList.add(2, 3);	
//		linkedList.addAll(collection);
//		linkedList.addAll(1, collection);
//		linkedList.clear();
//		linkedList.remove((Integer) 0);
//		linkedList.remove(0);
//		linkedList.removeAll(collection);
//		linkedList.set(2, 20);
//		System.out.println(linkedList.size());
//		System.out.println(linkedList.indexOf((Integer)0));
//		System.out.println(linkedList.contains((Integer)(-1)));
//		System.out.println(linkedList.containsAll(collection));
//		System.out.println(linkedList.get(2));
//		System.out.println(linkedList.isEmpty());

		// Test subList()
//		URList<Integer> subList2 = new URArrayList<Integer>();
//		subList2=linkedList.subList(2, 5);
//		for (Integer i: subList2) {
//			System.out.println(i);
//		}
//		linkedList.addLast(5);
//		linkedList.addFirst(4);

//		System.out.println(linkedList.peekFirst());
//		System.out.println(linkedList.peekLast());
//		System.out.println(linkedList.pollFirst());
//		System.out.println(linkedList.pollLast());
//		System.out.println(linkedList.equals(linkedList1));

		// Test toArray()
//		Object[] array = (Object[]) linkedList.toArray();
//		for (Object i : array) {
//			System.out.printf("%-5d", i);
//		}

		// Test iterator()
//		Iterator<Integer> it = linkedList.iterator();
//		while (it.hasNext()) {
//			System.out.printf("%-5d", it.next());
//		}

		linkedList.print();

	}
}

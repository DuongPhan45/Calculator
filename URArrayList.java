package project1;

// Name: Duong Phan
// NetID: dphan7
// Lab Section: Gavett 244, TR 2-3.15pm

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class URArrayList<E> implements URList<E>, Iterable<E> {
	int size;
	E[] data;

//*** Constructor and print method
	public URArrayList() {
		size = 0;
		data = (E[]) new Object[10];
	}

	public void print() {
		for (E e : data)
			if (e != null) {
				System.out.printf("%5s", e);
			}
		System.out.println();
	}
	// ******

	// Appends the specified element to the end of this list
	public boolean add(E e) {
		// Check if the size is greater than the length of the array
		if (size >= data.length) {
			// Create a new larger data array
			E[] newData = (E[]) new Object[data.length + 10];

			// Copy the data to new data
			for (int i = 0; i < size; i++) {
				newData[i] = data[i];
			} // Update the data
			data = newData;
		}

		// Add the element to the end of the data array
		data[size] = e;

		// Update size
		size += 1;
		return true;
	}

	// Inserts the specified element at the specified position in this list
	public void add(int index, E element) {
		// Check the validity of index
		Objects.checkIndex(index, size);
		// Create a new data array
		E[] newData;
		// Check if the size is greater than the length of the array
		if (size >= data.length) {
			// Create a new larger data array
			newData = (E[]) new Object[data.length + 10];

		} else {
			newData = (E[]) new Object[data.length];
		}
		// Copy the front part of the old data to new data array
		for (int i = 0; i < index; i++) {
			newData[i] = data[i];
		}

		// Add the new element to new data at the right index
		newData[index] = element;
		// Add the back part of the old data to the new data array
		for (int i = index; i < size; i++) {
			newData[i + 1] = data[i];
		}
		// Update size
		size += 1;
		// Update the data
		data = newData;

	}

	// Appends all of the elements in the specified collection to the end of this
	// list, in the order that they are returned by the specified collection's
	// iterator
	public boolean addAll(Collection<? extends E> c) {

		// Appends all of the elements in the specified collection to the end of this
		// list
		for (E element : c) {
			add(element);
		}
		return true;
	}

	// Inserts all of the elements in the specified collection into this list
	// at the specified position
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E element : c) {
			add(index, element);
			index++;
		}
		return true;
	};

	// Removes all of the elements from this list
	public void clear() {
		for (int i = 0; i < size; i++) {
			data[i] = null;
		}
		size = 0;
	}

	// Returns true if this list contains the specified element.
	// Not used for Collection
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	// Returns true if this list contains all of the elements of the specified
	// collection
	public boolean containsAll(Collection<?> c) {
		for (Object element : c) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	// Compares the specified object with this list for equality.
	// Returns true if both contain the same elements. Ignore capacity
	public boolean equals(Object o) {
		// Cast the object to a list
		Object[] object = (Object[]) o;
		if (object.length != size)
			return false;
		for (Object e : object) {
			if (!contains(e))
				return false;
		}
		return true;
	}

	// Returns the element at the specified position in this list.
	public E get(int index) {
		return (E) data[index];
	};// How to return E?

	// Returns the index of the first occurrence of the specified element in this
	// list,
	// or -1 if this list does not contain the element.
	public int indexOf(Object o) {
		int count = 0;
		for (Object element : data) {
			if (element.equals(o)) {
				return count;
			} else
				count += 1;
		}
		return -1;
	}

	// Returns true if this list contains no elements.
	public boolean isEmpty() {
		return size == 0;
	}

	// Returns an iterator over the elements in this list in proper sequence.
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {
			int index = 0;

			@Override
			public boolean hasNext() {
				return index < size && data[index] != null;
			}

			@Override
			public E next() {

				return data[index++];
			}
		};
		return it;
	};

	// Removes the element at the specified position in this list
	public E remove(int index) {
		// Check the validity of index
		Objects.checkIndex(index, size);

		// Delete the element at index and mover every elements after it 1 position to
		// the front
		E value = data[index];
		for (int i = index; i < size - 1; i++) {
			data[index] = data[index + 1];
			index++;
		}
		// Delete the last redundant element of the array
		data[size - 1] = null;
		// Update the size
		size--;
		return value;
	}

	// Removes the first occurrence of the specified element from this list,
	// if it is present
	public boolean remove(Object o) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].equals(o)) {
				int index = indexOf(o);
				remove(index);
				return true;
			}
		}
		return false;
	}

	// Removes from this list all of its elements that are contained
	// in the specified collection
	public boolean removeAll(Collection<?> c) {
		for (Object element : c) {
			remove(element);
		}
		return true;
	};

	// Replaces the element at the specified position in this list
	// with the specified element
	public E set(int index, E element) {
		// Check the validity of index
		Objects.checkIndex(index, size);

		E value = data[index];
		data[index] = element;
		return value;
	};

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
		for (int i = 0; i < toIndex - fromIndex + 1; i++) {
			subList.data[i] = data[i + fromIndex];
		}
		return subList;

	}

	// Returns an array containing all of the elements in this list
	// in proper sequence (from first to the last element).
	public Object[] toArray() {
		Object[] array = new Object[size];
		for (int i = 0; i < size; i++) {
			array[i] = data[i];
		}
		return array;
	};

	// Increases the capacity of this ArrayList instance, if necessary,
	// to ensure that it can hold at least the number of elements specified
	// by the minimum capacity argument.
	void ensureCapacity(int minCapacity) {
		if (data.length < minCapacity) {
			// Create new array that can hold the number of elements specified
			E[] newArray = (E[]) new Object[minCapacity];
			// Copy old array to new one
			for (int i = 0; i < size; i++) {
				newArray[i] = data[i];
			}
			// Update the old array
			data = newArray;

		}
	}

	// Returns the current capacity of the list
	int getCapacity() {
		return data.length;
	}

	public static void main(String args[]) {
		// Create a URArrayList
		URArrayList<Integer> list = new URArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		// Create a similar URArrayList
		URArrayList<Integer> listCopy = new URArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			listCopy.add(i);
		}
		// Create a collection with same content
		Collection<Integer> list2 = new ArrayList<Integer>();
		for (int j = 0; j < 10; j++) {
			list2.add(j);
		}
//		list.add(2, 20);
//		list.addAll(list2);
//		list.addAll(4,list2);
//		list.clear();
//		System.out.println(list.containsAll(list2));
//		System.out.println(list.contains(5));
//		System.out.println(list.get(5));
//		System.out.println(list.indexOf(3));
//		System.out.println(list.isEmpty());
//		System.out.println(list.size());
//		System.out.println(list.set(2, 22));
//		System.out.println(list.remove(2));
//		System.out.println(list.remove((Integer)3));
//		System.out.println(list.removeAll(list2));
//		list.ensureCapacity(30);
//		System.out.println(list.getCapacity());

		// Test toArray()
//		Object[] toArray = list.toArray();
//		for (int i = 0; i < toArray.length; i++) {
//			System.out.printf("%-4d",toArray[i]);
//		}

		// Test subList()
//		for (Integer i : list.subList(2, 4)) {
//			System.out.printf("%-5s", i);
//		}

//		Test iterator()
//		Iterator<Integer> it = list.iterator();
//		while(it.hasNext()) {
//			System.out.printf("%-5s", it.next());
//		}

		list.print();

	}
}

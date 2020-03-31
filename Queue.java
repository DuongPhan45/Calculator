package project1;

//Name: Duong Phan
//NetID: dphan7
//Lab section: Gavett 244 TR 2-3.15pm
public interface Queue<AnyType> {
	public boolean isEmpty();

	public void enqueue(AnyType x);

	public AnyType dequeue();

	public AnyType peek();
}

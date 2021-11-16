/**
 * Defines a doubly-linked list class
 * @author Zachary Iguelmamene
 * @author Edgar Aguilar
 * CIS 22C Lab 3
 */
 
import java.util.NoSuchElementException;
 
public class List<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;
        
        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    private int length;
    private Node first;
    private Node last;
    private Node iterator;
    
    /****CONSTRUCTOR****/
    
    /**
     * Instantiates a new List with default values
     * @postcondition Default values for length, first, last, and iterator are set
     */
    public List() {
    	length = 0;
    	first = null;
    	last = null;
    	iterator = null;
    }
    
    /**
     * Instantiates a new List by copying another List
     * @param original the List to make a copy of
     * @postcondition a new List object, which is an identical
     * but separate copy of the List original
     */
    public List(List<T> original) {
        if (original == null) {
            return;
        }
        if (original.length == 0) {
            length = 0;
            first = null;
            last = null;
            iterator = null;
        } else {
            Node temp = original.first;
            while (temp != null) {
                addLast(temp.data);
                temp = temp.next;
            }
            iterator = null;
        }
    }
    
    
    /****ACCESSORS****/
    
    /**
     * Returns the value stored in the first node
     * @precondition Length != zero
     * @return the value stored at node first
     * @throws NoSuchElementException when precondition is violated
     */
    public T getFirst() throws NoSuchElementException{
    	if (length == 0) {
    		throw new NoSuchElementException("getFirst(): List is empty!");
    	}
        return first.data;
    }
    
    /**
     * Returns the value stored in the last node
     * @precondition List is not empty
     * @return the value stored in the node last
     * @throws NoSuchElementException when precondition is violated
     */
    public T getLast() throws NoSuchElementException{
    	if (length == 0) {
    		throw new NoSuchElementException("getLast(): List is empty!");
    	}
        return last.data;
    }
    
    /**
     * Returns the current length of the list
     * @return the length of the list from 0 to n
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Returns whether the list is currently empty
     * @return whether the list is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }
    
    /**
     * returns data at iterator position
     * @return data at iterator location
     * @throws NullPointerException if iterator is null
     */
    public T getIterator() throws NullPointerException{
    	if(iterator == null) {
    		throw new NullPointerException("getIterator: iterator is null.");
    	}
    	return iterator.data;
    }
    
    /**
     * 
     * @return whether iterator is null or not
     */
    public boolean offEnd() {
    	return iterator == null;
    }
    
    /**
     * Determines whether two Lists have the same data
     * in the same order
     * @param L the List to compare to this List
     * @return whether the two Lists are equal
     */
    @SuppressWarnings("unchecked")
    @Override public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if (!(o instanceof List)) {
            return false;
        } else {
            List<T> L = (List<T>) o;
            if (this.length != L.length) {
                return false;
            } else {
                Node temp1 = this.first;
                Node temp2 = L.first;
                while (temp1 != null) { //Lists are same length
                    if (!(temp1.data.equals(temp2.data))) {
                        return false;
                    }
                    temp1 = temp1.next;
                    temp2 = temp2.next;
                }
                return true;
            }
        }
    }
    
    /****MUTATORS****/
    
    /**
     * Creates a new first element
     * @param  the data to insert at the 
     * front of the list
     * @postcondition A new node is created at the front of the list
     */
    public void addFirst(T data) {
    	Node N = new Node(data);
    	if(first == null) {
    		first = last = N;
    	} else {
    		N.next = first;
    		first.prev = N;
    		first = N;
    	}
        length++;
    }
    
    /**
     * Creates a new last element
     * @param the data to insert at the 
     * end of the list
     * @postcondition A new node is created at the end of the list
     */
    public void addLast(T data) {
    	Node N = new Node(data);
    	if (length == 0) {
    		first = last = N;
    	} else {
    		last.next = N;
    		N.prev = last;
    		last = N;
    	}
        length++;
    }
    
    /**
    * removes the element at the front of the list
    * @precondition List is not empty
    * @postcondition First element is removed
    * @throws NoSuchElementException when precondition is violated
    */
    public void removeFirst() throws NoSuchElementException{
    	if (length == 0) {
    		throw new NoSuchElementException("removeFirst():  List is empty!");
    	} else if (length == 1) {
    		first = last = iterator = null; // Added iterator = null;
    	} else {
    		if(iterator == first){
    			iterator = null;
    		} 	
    		first = first.next;
    		first.prev = null;
    	}
        length--;
    }
    
    /**
     * removes the element at the end of the list
     * @precondition List is not empty
     * @postcondition Last element is removed
     * @throws NoSuchElementException when precondition is violated
     */
    public void removeLast() throws NoSuchElementException{
    	if (length == 0) {
    		throw new NoSuchElementException("removeLast():  List is empty!");
    	} else if (length == 1) {
    		first = last = iterator = null; // This iterator also needs to be set to null, yes??
    	} else {
    		if (iterator == last) {
    			iterator = null;
    		}
    		last = last.prev;
    		last.next = null;
    		// Iterator may also still be pointing to last, preventing garbage collection
    	}
        length--;
    }
    
    
    /**
     * Creates a new element after iterator position
     * @precondition iterator is not null
     * @throws NullPointerException when iterator is offEnd
     * @param the data to insert at the position of iterator
     * @postcondition A new node is created after the iterator position
     */
   public void addIterator(T data) throws NullPointerException {
	   Node N = new Node(data);
	   if (iterator == null) {
   		throw new NullPointerException("addIterator: iterator is off end.");
	   }
	   else if (iterator == last){
		   addLast(data);
	   }
	   else {
		   N.next = iterator.next;
		   N.prev = iterator;
		   iterator.next = N;
		   N.next.prev = N;
		   length++;
	   }
   }
    
   
    /**
     * removes the element iterator is pointing to
     * @precondition iterator is not null
     * @throws NullPointerException when iterator is offEnd
     * @postcondition iterator points to null
     */
    public void removeIterator() throws NullPointerException {
    	if (iterator == null) {
    		throw new NullPointerException("removeIterator: iterator is off end.");
    	}
    	else if (iterator == first) {
    		removeFirst();
    		//iterator = null;
    	}
    	else if (iterator == last) {
    		removeLast();
    		//iterator = null;
    	}
    	else {
    		iterator.next.prev = iterator.prev;
    		iterator.prev.next = iterator.next;
    		iterator = null;
    		length--;
    	}
    }
    
    /**
     * advances iterator one position
     * @precondition iterator is not null
     * @throws NullPointerException when iterator is null
     * @postcondition iterator is advanced by one position
     */
    public void advanceIterator() throws NullPointerException{
    	if (iterator == null) {
    		throw new NullPointerException("advanceIterator: iterator is off end.");
    	}
    	if (iterator == last) {
    		iterator = null;
    	}
    	else {
    		iterator = iterator.next;
    	}
    }
    
    /**
     * reverses iterator one position
     * @precondition iterator is not null
     * @throws NullPointerException when iterator is null
     * @postcondition iterator is reversed by one position
     */
    public void reverseIterator() throws NullPointerException{
    	if (iterator == null) {
    		throw new NullPointerException("reverseIterator: iterator is off end.");
    	}
    	if (iterator == first) {
    		iterator = null;
    	}
    	else {
    		iterator = iterator.prev;
    	}
    }    
    
    /**
     * iterator is placed at first element
     * @postcondition iterator is placed at first element
     */
    public void placeIterator() {
    	iterator = first;
    }
    
    /****ADDITIONAL OPERATIONS****/
    
    /**
     * List with each value on its own line
     * At the end of the List a new line
     * @return the List as a String for display
     */
    @Override public String toString() {
    	String result = "";
        Node temp = first;
        while(temp != null) {
            result += temp.data;
            temp = temp.next;
        }
        return result;
    }
    
    public void printNumberedList() throws NullPointerException{
    	if (length == 0) {
    		throw new NullPointerException ("printNumberedList(): List is empty!");
    	}
    	placeIterator();
    	int counter = 1;
    	while(iterator != null) {
    		System.out.println(counter + ": " + getIterator());
    		advanceIterator();
    		counter++;
    	}
    }
     
}

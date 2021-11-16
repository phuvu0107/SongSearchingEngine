/**
 * HashTable.java
 * @author Edgar Aguilar
 * @author Zachary Iguelmamene
 * CIS 22C, Lab 6
 */
import java.util.ArrayList;

public class HashTable<T> {
    
    private int numElements;
    private ArrayList<List<T>> Table;

    /**
     * Constructor for the Hash.java
     * class. Initializes the Table to
     * be sized according to value passed
     * in as a parameter
     * Inserts size empty Lists into the
     * table. Sets numElements to 0
     * @param size the table size
     */
    public HashTable(int size) {
    	Table = new ArrayList<List<T>>();
    	for(int i = 0; i < size; i++) {
    		List<T> l = new List<T>();
    		//System.out.println(l);	//TEST
    		Table.add(l);
    	}
    	numElements = 0;
        
    }
       
    /**Accessors*/
    // FROM YUNG'S HASHTABLE FILE (for the course project)
    public List<T> getBucket(int index){
		return Table.get(index);
	}
    
    // FROM YUNG'S HASHTABLE FILE (for the course project)
    public int getTableSize() {
		return Table.size();
	}
    
    /**
     * returns the hash value in the Table
     * for a given Object 
     * @param t the Object
     * @return the index in the Table
     */
    private int hash(T t) {
        int code = t.hashCode();
        return code % Table.size();
    }
    
    /**
     * counts the number of keys at this index
     * @param index the index in the Table
     * @precondition 0 <=  index < Table.length
     * @return the count of keys at this index
     * @throws IndexOutOfBoundsException
     */
    public int countBucket(int index) throws IndexOutOfBoundsException{
    	if(index < 0 || index >= Table.size()) {
    		throw new IndexOutOfBoundsException("countBucket(): "
    			+ "index is outside bounds of the table");
    	} 
    	return Table.get(index).getLength();
       }
    
    /**
     * returns total number of keys in the Table
     * @return total number of keys
     */
    public int getNumElements() {
        return numElements;
    }
    
    /**
     * Accesses a specified key in the Table
     * @param t the key to search for
     * @return  the value to which the specified key is mapped, 
     * or null if this table contains no mapping for the key. 
     * @precondition t != null
     * @throws NullPointerException if the specified key is null
     */
    public T get(T t) throws NullPointerException{
    	if(t==null) {
    		throw new NullPointerException("get(): key is null");
    	}
    	int bucket = hash(t);
    	Table.get(bucket).placeIterator();
    	for (int i = 0; i < Table.get(bucket).getLength(); i++) {
    	   if (Table.get(bucket).getIterator().equals(t)) {
    	      return Table.get(bucket).getIterator();
    	   }
    	   Table.get(bucket).advanceIterator();
    	}
    	return null;
    }
    
    /**
     * Determines whether a specified key is in 
     * the Table
     * @param t the key to search for
     * @return  whether the key is in the Table 
     * @precondition t != null
     * @throws NullPointerException if the specified key is null
     */
    public boolean contains(T t) throws NullPointerException{
    	if(t==null) {
    		throw new NullPointerException("contains(): key is null");
    	}
    	int bucket = hash(t);
    	Table.get(bucket).placeIterator();
    	for (int i = 0; i < Table.get(bucket).getLength(); i++) {
    	   if (Table.get(bucket).getIterator().equals(t)) {
    	      return true;
    	   }
    	   Table.get(bucket).advanceIterator();
    	}
    	return false;
    }
    
     
    /**Mutators*/
    
    /**
     * Inserts a new element in the Table
     * at the end of the chain in the bucket
     * to which the key is mapped
     * @param t the key to insert
     * @precondition t != null
     * @throws NullPointerException for a null key
     */
    public void put(T t) throws NullPointerException{  
    	   if (t == null) { //cannot hash a null
    		      throw new NullPointerException("put(): key is null!");	
    		   } else {
    		      int bucket = hash(t);
    		      Table.get(bucket).addLast(t);
    		      numElements++;
    		   }
    }  
     
     
    /**
     * removes the key t from the Table
     * calls the hash method on the key to
     * determine correct placement
     * has no effect if t is not in
     * the Table or for a null argument          
     * @param t the key to remove
     * @throws NullPointerException if the key is null
     */
    public void remove(T t) throws NullPointerException {
    	if (t == null) {
    		throw new NullPointerException("remove(): The key is null!");
    	}
    	int bucket = hash(t);
    	Table.get(bucket).placeIterator();
    	for (int i = 0; i < Table.get(bucket).getLength(); i++) {
    		if (Table.get(bucket).getIterator().equals(t)) {
    			Table.get(bucket).removeIterator();
    	        numElements--;
    	        return;
    	    } 
    	    Table.get(bucket).advanceIterator();
    	 }
    }
    
    /**
     * Clears this hash table so that it contains no keys.
     */
    public void clear() {
    	for(int i = 0; i < Table.size(); i++) {
    		List<T> l = new List<T>();
    		Table.set(i, l);
    	}
    	numElements = 0;
    }

    /**Additional Methods*/

    /**
     * Prints all the keys at a specified
     * bucket in the Table. Each key displayed
     * on its own line, with a blank line 
     * separating each key
     * Above the keys, prints the message
     * "Printing bucket #<bucket>:"
     * Note that there is no <> in the output
     * @param bucket the index in the Table
     */
    public void printBucket(int bucket) {
    	System.out.println("Printing bucket #" + bucket);
    	Table.get(bucket).placeIterator();
        for(int i = 0; i < Table.get(bucket).getLength(); i++) {
        	System.out.println(Table.get(bucket).getIterator() + "\n");
        	Table.get(bucket).advanceIterator();
        }
    }
        
    /**
     * Prints the first key at each bucket
     * along with a count of the total keys
     * with the message "+ <count> -1 more 
     * at this bucket." Each bucket separated
     * with two blank lines. When the bucket is 
     * empty, prints the message "This bucket
     * is empty." followed by two blank lines
     */
    public void printTable(){
         for(int i = 0; i < Table.size(); i++) {
        	 if(countBucket(i) == 0) {
        		 System.out.print("\nThis bucket is empty.\n\n");
        	 }
        	 else {
        		 System.out.print(Table.get(i).getFirst() + " + " + (countBucket(i)-1) + " more at this bucket.\n\n");
        	 }
         }
     }
    
    /**
     * Starting at the first bucket, and continuing
     * in order until the last bucket, concatenates
     * all elements at all buckets into one String
     */
    @Override public String toString() {
    	String result = "";
    	for(int i = 0; i < Table.size(); i++) {
			Table.get(i).placeIterator();
    		for(int x = 0; x < Table.get(i).getLength(); x++) {
    			result += Table.get(i).getIterator() + " ";
    			Table.get(i).advanceIterator();
    		}
    	} 
    	return result;
    }
    
}
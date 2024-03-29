package comp1110.lectures.A04;

import java.util.LinkedList;

// this is called hashSet because this is actually a set using hash table
// if we do not use hashTable, when applying set ADT, we need to search all the elements to find out if this set contains a certain element
// the "value" in the (key value) pair of the hashTable is the value from the set
// so now when we want to check if the set contains certain element, take the key of element and go to the corresponding bucket
// and see if this bucket contains the element

public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_SIZE = 4;
    private LinkedList<T>[] table;
    private int numElements = 0;

    public HashSet() {
        this(DEFAULT_SIZE);
    }

    public HashSet(int size) {
        table = new LinkedList[size];
    }

    @Override
    public boolean add(T element) {
        if(element == null){
            return false;
        }

        int hashcode = element.hashCode();
        int buckNum = Math.abs(hashcode%DEFAULT_SIZE);
        if(table[buckNum] == null){
            table[buckNum] = new LinkedList<>();
        }
        if(!table[buckNum].contains(element)){
            table[buckNum].add(element);
            numElements++;
            return true;
        }
        return false;

    }

    @Override
    public boolean remove(T element) {
        if(element == null){
            return false;
        }
        int hashcode = element.hashCode();
        int buckNum = Math.abs(hashcode%DEFAULT_SIZE);
        if(table[buckNum] == null){
            return  false;
        }
        else if(table[buckNum].contains(element)){
            boolean remove = table[buckNum].remove(element);
            if(remove) numElements--;
            return remove;
        }
        return false;
    }

    @Override
    public boolean contains(T element) {
       if(element == null){
           return false;
       }

        int hashcode = element.hashCode();
        int buckNum = Math.abs(hashcode%DEFAULT_SIZE);
        if(table[buckNum] == null){
            return false;
        }
        else if(table[buckNum].contains(element)){
            return true;
        }
        return false;


    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public Object[] toArray() {
        Object[] elements = new Object[numElements];
        int i = 0;
        for (LinkedList<T> bucket : table) {
            if (bucket != null) {
                for (T entry : bucket) {
                    elements[i++] = entry;
                }
            }
        }
        return elements;
    }

    @Override
    public Set<T> newInstance() {
        return new HashSet<>();
    }
}

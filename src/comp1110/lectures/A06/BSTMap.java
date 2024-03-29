package comp1110.lectures.A06;

import comp1110.lectures.A04.Set;
import comp1110.lectures.A05.BSTSet;

/**
 * An implementation of the Map abstract data type using a binary search tree.
 * @param <K> the key type for the map
 * @param <V> the value type for the map
 */
public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {
    class KVPair {
        K key;
        V value;

        KVPair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * This class represents a binary search tree
     * (or equivalently, a node of such a tree).
     */
    class BSTree {
        KVPair kv;
        BSTree left, right;

        BSTree(KVPair kv) {
            this.kv = kv;
        }

        /**
         *  Add all the keys that are stored in this tree to the given set.
         */
        void addKeys(Set<K> keys) {
            if (left != null) {
                left.addKeys(keys);
            }

            if (right != null) {
                right.addKeys(keys);
            }

            keys.add(this.kv.key);
        }

        /**
         * Put the given value into the tree at the given key.
         *
         * @param key the key to add
         * @return the value that was previously stored in the tree,
         * or null if the key was not found
         */
        V add(K key, V value) {
            KVPair newPair = new KVPair(key, value);
            BSTree newNode = new BSTree(newPair);
           if(key == this.kv.key){
               V previous = this.kv.value;
               this.kv.value = value;
               return previous;
           }
           if(key.compareTo(this.kv.key)< 0){
               if(this.left == null){
                   this.left = newNode;
               }
               else{
                   return this.left.add(key, value);
               }
           }
           if(key.compareTo(this.kv.key) > 0){
               if(this.right == null){
                   this.right = newNode;
               }
               else{
                   return this.right.add(key,value);
               }

           }
           return null;
        }

        /**
         * Add the given subtree to this tree.
         *
         * @param subtree a binary search tree (or subtree) to add to this tree
         * @return true if the tree was successfully added; false if the value
         * at the root node of 'subtree' was already contained in the tree
         */
        boolean add(BSTree subtree) {
            if (subtree == null) return false;
            if (kv.key.compareTo(subtree.kv.key) < 0) {
                // right-hand tree
                if (right != null) {
                    return right.add(subtree);
                } else {
                    right = subtree;
                    return true;
                }
            } else if (kv.key.compareTo(subtree.kv.key) > 0) {
                // left-hand tree
                if (left != null) {
                    return left.add(subtree);
                } else {
                    left = subtree;
                    return true;
                }
            } else {
                // same value
                return false;
            }
        }

        /**
         * Find the given key in the tree, and return the tree that is
         * rooted at the node containing the key.
         * If the key is found and 'remove' is true, remove the node
         * containing the key (and its subtrees) from the tree.
         *
         * @param key    the key to find
         * @param remove if true, remove the node containing the key from the tree
         * @return the node that contains the key, or null if the key is not in the tree
         */
        BSTree find(K key, boolean remove) {
            if(key == null){
                return null;
            }
            if(key.compareTo(this.kv.key)<0){
                if(this.left == null){
                    return null;
                }
                else{
                    BSTree found = this.left.find(key,remove);
                    if(found!= null && remove && found.kv.key == key){
                        this.left = null; // is there anything wrong with this?
                    }
                    return found;

                }
            }

            if(key.compareTo(this.kv.key)>0){
                if(this.right == null){
                    return null;
                }
                else{
                    BSTree found = this.right.find(key, remove);
                    if(found != null && remove && found.kv.key == key){
                        this.right = null;
                    }
                    return  found;
                }

            }
            if(key == this.kv.key){
                return this;
            }
            return null;
        }
    }

    /** The root note of the tree containing all key-value pairs of the map. */
    private BSTree tree;
    private int numElements = 0;

    @Override
    public V put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (value == null) throw new IllegalArgumentException("value is null");
        if (tree == null) {
            tree = new BSTree(new KVPair(key, value));
            numElements++;
        } else {
            V previousValue = tree.add(key, value);
            if (previousValue == null) numElements++;
            return previousValue;
        }

        return null;
    }

    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (tree != null) {
            BSTree node = tree.find(key, false);
            if (node == null) {
                return null;
            } else {
                return node.kv.value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (tree != null) {
            BSTree node;
            if (tree.kv.key.equals(key)) {
                // removing the root node - construct a new tree from either left or right subtree
                node = tree;
                if (node.left != null) {
                    tree = node.left;
                    if (node.right != null) {
                        tree.add(node.right);
                    }
                } else {
                    tree = node.right;
                }
                numElements--;
                return true;
            } else {
                node = tree.find(key, true);
                if (node != null) {
                    if (node.left != null) {
                        tree.add(node.left);
                    }
                    if (node.right != null) {
                        tree.add(node.right);
                    }
                    numElements--;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        tree = null;
        numElements = 0;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public Set<K> getKeys() {
        Set<K> keys = new BSTSet<>();
        if (tree != null) {
            tree.addKeys(keys);
        }
        return keys;
    }
}


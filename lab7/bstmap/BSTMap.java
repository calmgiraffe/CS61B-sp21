package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {
    private class BSTNode {
        private BSTNode left;
        private BSTNode right;
        private K key;
        private V value;

        BSTNode(K key, V value) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.value = value;
        }
    }
    private int size = 0;
    private BSTNode tree = null;

    @Override
    /* Removes all the mappings from this map. */
    public void clear() {
        if (tree != null) {
            tree = null;
            size = 0;
        }
    }

    private boolean containsKeyHelper(K key, BSTNode tree) {
        // if current node containsKey, return true
        // else if, containsKey on left branch
        // else, containsKey on right branch
        if (tree == null) {
            return false;
        } else if (key.equals(tree.key)) {
            return true;
        } else if (key.compareTo(tree.key) < 0) {
            return containsKeyHelper(key, tree.left);
        } else {
            return containsKeyHelper(key, tree.right);
        }
    }

    @Override
    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return containsKeyHelper(key, tree);
    }

    private V getHelper(K key, BSTNode tree) {
        if (tree == null) {
            return null;
        } else if (key.equals(tree.key)) {
            return tree.value;
        } else if (key.compareTo(tree.key) < 0) {
            return getHelper(key, tree.left);
        } else {
            return getHelper(key, tree.right);
        }
    }

    @Override
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return getHelper(key, tree);
    }

    @Override
    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    private BSTNode putHelper(K key, V value, BSTNode tree) {
        if (tree == null) {
            size += 1;
            return new BSTNode(key, value);
        } else if (key.equals(tree.key)) {
            tree.value = value;
        } else if (key.compareTo(tree.key) < 0) {
            tree.left = putHelper(key, value, tree.left);
        } else {
            tree.right = putHelper(key, value, tree. right);
        }
        return tree;
    }

    @Override
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (tree == null) {
            tree = new BSTNode(key, value);
            size += 1;
        } else {
            putHelper(key, value, tree);
        }
    }

    private void printInOrderHelper(BSTNode tree) {
        if (tree == null) {
            return;
        }
        printInOrderHelper(tree.left);
        System.out.println(tree.key);
        printInOrderHelper(tree.right);
    }

    public void printInOrder() {
        printInOrderHelper(tree);
    }

    private void keySetHelper(TreeSet set, BSTNode tree) {
        if (tree != null) {
            set.add(tree.key);
            keySetHelper(set, tree.left);
            keySetHelper(set, tree.right);
        }
    }

    @Override
    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        TreeSet<K> keySet = new TreeSet<>();
        keySetHelper(keySet, tree);
        return keySet;
    }

    private V removeHelper(K key, BSTNode tree) {
        //
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        /*
        if (tree != null) {
            return removeHelper(key, tree);
        }
        */
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}

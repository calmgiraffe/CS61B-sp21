package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author calmgiraffe */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /**
     * Instance Variables */
    private Collection<Node>[] buckets; // each Collection represents a single bucket in the HT
    private int size = 0;
    private int numBuckets = 16; // default numBuckets = 16
    private double maxLoad = 0.75; // default load factor = 0.75

    /**
     * Constructor without parameters */
    public MyHashMap() {
        this.buckets = createTable(numBuckets);
    }

    /**
     * Constructor with initialSize parameter */
    public MyHashMap(int initialSize) {
        this.buckets = createTable(initialSize);
        this.numBuckets = initialSize;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor */
    public MyHashMap(int initialSize, double maxLoad) {
        this.buckets = new Collection[initialSize];
        this.numBuckets = initialSize;
        this.maxLoad = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket.
     * <p>
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR! */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     * @param tableSize the size of the table to create */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    /**
     * Removes all the mappings from this map. */
    @Override
    public void clear() {
        for (Collection bucket: buckets) {
            if (bucket == null) {
                continue;
            }
            bucket.clear();
        }
        size = 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), numBuckets);

        if (buckets[index] != null) {
            for (Node node: buckets[index]) {
                if (node.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), numBuckets);

        if (buckets[index] != null) {
            for (Node node: buckets[index]) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    /**
     * Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Resizes the Collection array so that it holds more buckets */
    private void resize(int newNumBuckets) {
        Collection<Node>[] newBuckets = createTable(newNumBuckets);

        for (Collection<Node> bucket: buckets) { // Iterate through all Collections in this.buckets
            if (bucket == null) {
                continue;
            }
            for (Node n: bucket) { // iterate through all Nodes in Collection
                int newIndex = Math.floorMod(n.key.hashCode(), newNumBuckets);
                if (newBuckets[newIndex] == null) {
                    newBuckets[newIndex] = createBucket();
                }
                newBuckets[newIndex].add(createNode(n.key, n.value));
            }
        }
        numBuckets = newNumBuckets;
        buckets = newBuckets;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced. */
    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), numBuckets);

        if (buckets[index] == null) { // If no key matches, create a new node
            buckets[index] = createBucket();

        } else {
            for (Node node: buckets[index]) { // Iterate through bucket and update value if key matches
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }
            }
        }

        buckets[index].add(createNode(key, value));
        size += 1;
        if ((double) size/numBuckets >= maxLoad) {
            resize(2*numBuckets);
        }
    }

    /**
     * Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        for (Collection<Node> bucket: buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node n: bucket) {
                keys.add(n.key);
            }
        }
        return keys;
    }

    /**
     * Returns Iterator that iterators over the keys */
    @Override
    public Iterator<K> iterator() {
        Set<K> key = keySet();
        return key.iterator();
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
     @Override
     public V remove(K key) {
        throw new UnsupportedOperationException();
     }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException. */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}

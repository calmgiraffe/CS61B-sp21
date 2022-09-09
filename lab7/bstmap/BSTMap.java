package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {
    private class BSTNode {
        private BSTNode left;
        private BSTNode right;
        private BSTNode parent;
        private K key;
        private V value;

        BSTNode(K key, V value) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.value = value;
            this.parent = this;
        }
    }
    private int size = 0;
    private BSTNode sentinel = new BSTNode(null, null);

    @Override
    /* Removes all the mappings from this map. */
    public void clear() {
        sentinel.right = null;
        size = 0;
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
        return containsKeyHelper(key, sentinel.right);
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
        return getHelper(key, sentinel.right);
    }

    @Override
    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    private BSTNode putHelper(K key, V value, BSTNode tree) {
        if (tree == null) {
            // make new node if at null node
            return new BSTNode(key, value);
        }  else if (key.equals(tree.key)) {
            // if key already exists, update its value
            tree.value = value;
        } else if (key.compareTo(tree.key) < 0) {
            // make new left branch, make its parent the current tree
            tree.left = putHelper(key, value, tree.left);
            tree.left.parent = tree;
        } else {
            // make new right branch, make its parent the current tree
            tree.right = putHelper(key, value, tree. right);
            tree.right.parent = tree;
        }
        return tree;
    }

    @Override
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (size() == 0) {
            sentinel.right = new BSTNode(key, value);
            sentinel.right.parent = sentinel;
        } else {
            putHelper(key, value, sentinel.right);
        }
        size += 1;
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
        printInOrderHelper(sentinel.right);
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
        keySetHelper(keySet, sentinel.right);
        return keySet;
    }

    /* Helper method for removeHelper. Gets the maximum value on the left node of the current node (at the time
    * of the calling of getMaxKey.
    * If no children, the node is the maximum value. If there is a child on the node's left branch, */
    private K getMaxKey(BSTNode tree, boolean fromLeft) {
        if (tree.left == null && tree.right == null) { // if no children
            severParentLink(tree, fromLeft);
            return tree.key;

        } else if (tree.left != null && tree.right == null) { // if child on current node's left branch
            linkParentWithChild(tree, fromLeft);
            return tree.key;

        } else { // if child on current node's right branch
            return getMaxKey(tree.right, false);
        }
    }

    /* Navigates to parent node and sets its appropriate branch (left/right) to null */
    private void severParentLink(BSTNode tree, boolean fromLeft) {
        if (fromLeft) {
            tree.parent.left = null;
        } else {
            tree.parent.right = null;
        }
    }

    /* Navigates to parent node and sets its appropriate branch (left/right) to the child of the current node.
    * Then, sets the parent of the child node to the current node's parent */
    private void linkParentWithChild(BSTNode tree, boolean fromLeft) {
        if (tree.left != null) {
            if (fromLeft) { // if fromLeft and has left child
                tree.parent.left = tree.left;

            } else { // if fromRight and has left child
                tree.parent.right = tree.left;
            }
            tree.left.parent = tree.parent;

        } else if (tree.right != null) {
            if (fromLeft) { // if fromLeft and has right child
                tree.parent.left = tree.right;

            } else { // if fromRight and has right child
                tree.parent.right = tree.right;
            }
            tree.right.parent = tree.parent;
        }
    }

    /* Helper method for remove(). If the node to be removed is a match with the current node, one of three cases:
     * current node had no child, current node has exactly one child, current node has two children.
     * If key is not a match with current node's key, navigate down the appropriate branch of the current node. */
    private void removeHelper(K key, BSTNode tree, boolean fromLeft) {

        if (key == tree.key) {
            if (tree.left == null && tree.right == null) {
                severParentLink(tree, fromLeft);
            } else if (tree.left == null || tree.right == null) {
                linkParentWithChild(tree, fromLeft);
            } else {
                // Get maximum key on left branch, then update current node's key
                tree.key = getMaxKey(tree.left, true);
            }
            size -= 1;

        } else if (key.compareTo(tree.key) < 0) {
            removeHelper(key, tree.left, true);
        } else {
            removeHelper(key, tree.right, false);
        }
    }

    @Override
    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        // Initially get a copy of the value that is being searched for. Returns null if no nodes
        V value = get(key);

        // If tree has nodes, traverse through the BST and until key is found
        if (size() > 0) {
            removeHelper(key, sentinel.right, false);
        }
        return value;
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

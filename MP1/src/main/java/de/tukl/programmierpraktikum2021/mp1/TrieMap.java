package de.tukl.programmierpraktikum2021.mp1;
public class TrieMap<V> implements Map<String, V> {

    private final TreeNode<V> root = new TreeNode<>(null);               // Root node of the tree
    private final ListMap<String, String> keys = new ListMap<>();              // List to store keys of the map

    @Override
    public V get(String key) {
        return get(root, key);
    }

    /**
     * Idea: Travel recursively starting from the root to the node we need to get the value of.
     * The first character of the string will map to the child of the current node.
     */
    private V get(TreeNode<V> node, String key) {
        if (key == null)    // Handle NullPointerException
                return null;
        if (key.length() == 1 && node.getChild(key.charAt(0)) != null)         // Base case
            return node.getChild(key.charAt(0)).getValue();
        else if (key.length() > 1 && node.getChild(key.charAt(0)) != null)     // Inductive case
            return get(node.getChild(key.charAt(0)), key.substring(1));
        else
            return null;
    }

    @Override
    public void put(String key, V value) {
        if (get(key) == null)   // If there is currently no node with this key, add key to the key list.
            keys.put(key, key);
        put(root, key, value);
    }

    /**
     * Idea: Same as get, but instead of getting the value we have to either create a new node, or replace
     * the value of the existing node.
     */
    private void put(TreeNode<V> node, String key, V value) {
        if (key.length() == 1) {        // Base case
            if (node.getChild(key.charAt(0)) == null) {         // Node haven't existed yet, create new node
                node.addChild(key.charAt(0), new TreeNode<>(value));
            }
            else
                node.getChild(key.charAt(0)).setValue(value);   // Node already exists, replace node's value
        }
        else if (key.length() > 1) {   // Inductive case
            // Here we have to create empty nodes to make a path as well if the path didn't exist before
            if (node.getChild(key.charAt(0)) == null) {
                node.addChild(key.charAt(0), new TreeNode<>(null));
            }
            put(node.getChild(key.charAt(0)), key.substring(1), value);
        }
    }

    @Override
    public void remove(String key) {
        if (get(key) != null)   // If there's a node with this key, remove key from key list.
            keys.remove(key);
        remove(root, key);
    }

    /**
     * Idea: Same as get, but instead of getting the value we set the node's value to null
     */
    private void remove(TreeNode<V> node, String key) {
        if (key.length() == 1 && node.getChild(key.charAt(0)) != null) {    // Base case
                node.getChild(key.charAt(0)).setValue(null);
        }
        else if (key.length() > 1 && node.getChild(key.charAt(0)) != null)  // Inductive case
            remove(node.getChild(key.charAt(0)), key.substring(1));
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void keys(String[] array) throws IllegalArgumentException {
        if (array == null || size() > array.length)
            throw new IllegalArgumentException();
        keys.keys(array);
    }
}
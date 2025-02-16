package de.tukl.programmierpraktikum2021.mp1;

// Node class for TrieMap
public class TreeNode<V> {
    private V value;
    private final ListMap<Character, TreeNode<V>> children = new ListMap<>();

    public TreeNode(V value) {
        this.value = value;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public TreeNode<V> getChild(Character node_key) {
        return children.get(node_key);
    }

    public void addChild(Character node_key, TreeNode<V> node) {
        children.put(node_key, node);
    }
}

package de.tukl.programmierpraktikum2021.mp1;
import java.util.*;

public class TrieMap<V> implements Map<String, V> {

    private final TreeNode<V> root = new TreeNode<>("", null);

    @Override
    public V get(String key) {
        return get(root, key);
    }

    public V get(TreeNode<V> node, String key) {
        if (node == null)
            return null;

    }

    @Override
    public void put(String key, V value) {
        throw new RuntimeException("TODO");
    }

    @Override
    public void remove(String key) {
        throw new RuntimeException("TODO");
    }

    @Override
    public int size() {
        throw new RuntimeException("TODO");
    }

    @Override
    public void keys(String[] array) {
        throw new RuntimeException("TODO");
    }
}
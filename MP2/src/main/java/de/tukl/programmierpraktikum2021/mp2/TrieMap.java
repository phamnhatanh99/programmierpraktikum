package de.tukl.programmierpraktikum2021.mp2;
import com.googlecode.concurrenttrees.common.KeyValuePair;

public class TrieMap<V> implements Trie<V> {
    private com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<V> map;

    @Override
    public V get(String key) {
        throw new RuntimeException("TODO");
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
    public Iterable<KeyValuePair<V>> searchKeyPrefix(String prefix) {
        throw new RuntimeException("TODO");
    }
}

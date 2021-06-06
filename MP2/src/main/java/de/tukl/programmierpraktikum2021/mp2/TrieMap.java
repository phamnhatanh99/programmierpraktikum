package de.tukl.programmierpraktikum2021.mp2;
import com.googlecode.concurrenttrees.common.KeyValuePair;
import com.googlecode.concurrenttrees.common.PrettyPrinter;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;

public class TrieMap<V> implements Trie<V> {
    private final com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<V> map;

    public TrieMap() {
        this.map = new ConcurrentRadixTree<>(new DefaultCharArrayNodeFactory());
    }

    @Override
    public V get(String key) {
        return map.getValueForExactKey(key);
    }

    @Override
    public void put(String key, V value) {
        map.put(key, value);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Iterable<KeyValuePair<V>> searchKeyPrefix(String prefix) {
        return map.getKeyValuePairsForKeysStartingWith(prefix);
    }

    // For testing
    @Override
    public String toString() {
        return PrettyPrinter.prettyPrint(map);
    }
}

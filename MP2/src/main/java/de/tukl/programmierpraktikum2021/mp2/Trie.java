package de.tukl.programmierpraktikum2021.mp2;

import com.googlecode.concurrenttrees.common.KeyValuePair;

public interface Trie<V> {
    V get(String key);
    void put(String key, V value);
    void remove(String key);
    int size();
    Iterable<KeyValuePair<V>> searchKeyPrefix(String prefix);
}
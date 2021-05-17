package de.tukl.programmierpraktikum2021.mp1;

import java.util.ArrayList;

public class ListMap<K, V> implements Map<K, V> {
    private K key;
    private V value;
    private ListElement<K, V> list;


    public V get(K key) {
        ListElement<K, V> list2 = list;
        if (list2 == null) return null;
        while (list2.getNext() != null) {
            if (list2.getKey() == key) return list2.getValue();
            else list2 = list2.getNext();
        }
        if (list2.getKey() == key) return list2.getValue();
        return null;
    }

    @Override
    public void put(K key, V value) {
        ListElement<K, V> momentan = list;
        if (momentan == null) list = new ListElement<>(key, value);
        else {
            while (momentan.getNext() != null) {
                if (momentan.getKey() != key) {
                    momentan = momentan.getNext();
                } else {
                    break;
                }
            }
            if (momentan.getKey() == key) {
                if (list == null) list = new ListElement<K, V>(key, value);
                else {
                    momentan.setValue(value);
                }
            } else list.getLast().setNext(new ListElement(key, value));
        }
    }


    @Override
    public void remove(K key) {
        ListElement<K, V> list2 = list;
        ListElement<K, V> list3 = null;
        if (list == null) list = null;
        else {
            while (list2.getNext() != null) {
                if (list2.getKey() != key) {
                    if (list3 == null) list3 = new ListElement<>(list2.getKey(), list2.getValue());
                    else list3.getLast().setNext(new ListElement(list2.getKey(), list2.getValue()));
                    list2 = list2.getNext();
                } else {
                    if (list3 == null && list2.getNext() != null) {
                        list3 = list2.getNext();
                    } else if (list2.getNext() == null) list3.getLast().setNext(null);
                    else list3.getLast().setNext(list2.getNext());
                    list = list3;
                    break;
                }
            }

            if (list2.getKey() == key) {
                list = list3;
            }
        }
    }

    @Override
    public int size() {
        int counter = 0;
        ListElement<K, V> list2 = list;
        if (list2 == null) return 0;
        while (list2 != null) {
            counter++;
            list2 = list2.getNext();
        }
        return counter;
    }

    @Override
    public void keys(K[] array) {
        ListElement<K, V> list1 = list;
        if (array == null) throw new IllegalArgumentException();
        if (this.size() > array.length) throw new IllegalArgumentException();
        int i = 0;
        while (list1 != null) {
            array[i] = (K) list1.getKey();
            list1 = list1.getNext();
            i++;
        }
    }
}

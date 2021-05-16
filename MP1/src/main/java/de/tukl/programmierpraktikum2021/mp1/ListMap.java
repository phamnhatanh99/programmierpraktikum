package de.tukl.programmierpraktikum2021.mp1;

import java.util.ArrayList;

public class ListMap<K, V> implements Map<K, V>{
    private K key;
    private V value;
    private ListElement<K, V> list;


    public V get(K key) {
        ListElement<K,V> list2= list;
        if (list2 == null) return null;
        while (list2.getNext()!= null){
            if (list2.getKey() == key) return list2.getValue();
            else list2 = list2.getNext();
        }
        if (list2.getKey() == key) return list2.getValue();
        return null;
    }

    @Override
    public void put(K key, V value) {
        ListElement<K,V> momentan = list;
        while (momentan != null) {
            if (momentan.getKey() != key) {
                momentan = momentan.getNext();
            }
            else {
                break;
            }
        }
        if (momentan == null) {
            if (list == null) list = new ListElement<K,V>(key, value);
            else {
                ListElement i = list.getLastElem();
                if (i == null)
                    list.setNext(new ListElement<K,V>(key, value));
                else i.setNext(new ListElement<K,V>(key,value));
                }
            }
        else if (momentan.getKey() == key){
            momentan.setValue(value);
            }
    }

    @Override
    public void remove(K key) {
        ListElement<K,V> list2 = list;
        ListElement<K,V> list3 = null;
        if (list2 == null){
            return;
        }
        while (list2 != null) {
            if (list2.getKey() != key) {
                if (list3 == null) list3 = new ListElement(list2.getKey(),list2.getValue());
                else {
                    list3.setNext(list2);
                }
                list2 = list2.getNext();
            }
            else break;
        }
        if (list2 == null) {
            list3 = list;
        }

        else if (list2.getKey() == key && list2.getNext() == null) {
            if (list3 == null) list3 = list2.getNext();
            else list3.setNext(null);
        }
        else if  (list2.getKey() == key && list2.getNext() != null) {

            if (list3 == null) {
                list3 = list2.getNext();
            }
            else {
                list3.setNext(list3.getNext());
            }
        }
        else if (list2 == null)
            list3.setNext(null);
        list = list3;

    }

        @Override
    public int size() {
        int counter = 0;
        ListElement<K,V> list2 = list;
        if (list2 == null) return 0;
        while (list2 != null){
            counter ++;
            list2 = list2.getNext();

        }
        System.out.println(counter);
        return counter;

    }

    @Override
    public void keys(K[] array) {
        ListElement<K,V> list1 = list;
        if (this.size() < array.length) {
            throw new IllegalArgumentException();
        }

        int i = 0;
        while (list1 != null){
            array[i] = (K) list1.getKey();
            list1 = list1.getNext();
            i++;
        }
    }
}

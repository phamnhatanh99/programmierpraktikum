package de.tukl.programmierpraktikum2021.mp1;

import java.util.ArrayList;

public class ListMap<K, V> implements Map<K, V>{
    private K key;
    private V value;
    private ListElement<K, V> list;


    public V get(K key) {
        ListElement<K,V> list2= list;
        while (list2.getNext()!= null){
            if (list2.getKey() == key) return list2.getValue();
            else list2 = list2.getNext();

        }
        if (list2.getKey() == key) return list2.getValue();
        return null;
    }

    @Override
    public void put(K key, V value) {
        ListElement<K,V> list2 = list;
        ListElement<K,V> list3 = null;
        while (list2 != null) {
            if (list2.getKey() != key) {

                if (list3 == null) list3 = new ListElement(list2.getKey(),list2.getValue());
                else {
                    while (list3.getNext() != null)
                    list2.setNext();
                    list3.setNext(list2);
                }
                list2 = list2.getNext();

            }
            else break;
        }
        ListElement<K,V> list4 = new ListElement(key, value);
        System.out.println(list4.getKey());
        if (list3 == null) list3 = new ListElement(key,value);
        else {
            System.out.println(list4.getKey());
            list2 = list4;
            list3.setNext(list2);
        }

        list = list3;
    }

    @Override
    public void remove(K key) {
        ListElement<K,V> list2 = list;
        ListElement<K,V> list3 = null;
        while (list2 != null) {
            if (list2.getKey() != key) {
                if (list3 == null) list3 = new ListElement(list2.getKey(),list2.getValue());
                else {
                    list3.setNext(list2);

                    System.out.println(list3.getNext().getKey());
                }
                list2 = list2.getNext();
            }
            else break;
        }
        if (list2 == null) {

        }

        else if (list2.getKey() == key && list2.getNext() == null) {
            if (list3 == null) list3 = list2.getNext();
            else list3.setNext(null);
        }
        else if  (list2.getKey() == key && list2.getNext() != null) {
            if (list3 == null) {
                list3 = list2.getNext();
                System.out.println("hi");
            }

            else list3.setNext(list2.getNext());
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
            System.out.println(counter);
            System.out.println(list2.getKey());
            list2 = list2.getNext();

        }
        return counter;
    }

    @Override
    public void keys(K[] array) {

    }
}

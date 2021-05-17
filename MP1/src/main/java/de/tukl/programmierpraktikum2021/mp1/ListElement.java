package de.tukl.programmierpraktikum2021.mp1;

public class ListElement<K, V> {
    K key;
    V value;
    ListElement<K,V> pointer;

    public ListElement(K key, V value) {
        this.key = key;
        this.value = value;
        this.pointer = null;
    }


    public void setKey(K key){
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public void setValue(V value){
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setNext(ListElement next) {
        this.pointer = next;
    }

    public ListElement<K,V> getNext() {
        return pointer;
    }
    public ListElement<K,V> getLast(){
        ListElement last = this;
        while (last.getNext() != null){
            last = last.getNext();
        }
        return last;
    }
}





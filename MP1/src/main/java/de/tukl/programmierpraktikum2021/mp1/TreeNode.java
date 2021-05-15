package de.tukl.programmierpraktikum2021.mp1;
import java.util.*;

public class TreeNode<V> {
    //Node class
    String key;
    V value;
    List<TreeNode<V>> children;

    public TreeNode(String key, V value) {
        this.key = key;
        this.value = value;
        this.children = new ArrayList<>();
    }
}

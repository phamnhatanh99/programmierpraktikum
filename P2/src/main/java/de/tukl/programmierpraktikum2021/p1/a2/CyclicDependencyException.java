package de.tukl.programmierpraktikum2021.p1.a2;

import de.tukl.programmierpraktikum2021.p1.a1.GraphException;

import java.util.ArrayList;

public class CyclicDependencyException extends GraphException {
    final String cycList;
    public CyclicDependencyException(String cycle){
        super( cycle+ "formed a cyclic dependency!");
        this.cycList = cycle;
    }
}

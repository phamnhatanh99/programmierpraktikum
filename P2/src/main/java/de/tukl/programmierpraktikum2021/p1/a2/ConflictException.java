package de.tukl.programmierpraktikum2021.p1.a2;

import de.tukl.programmierpraktikum2021.p1.a1.GraphException;

public class ConflictException extends GraphException {
    final String pkg;
    public ConflictException(String pkg) {
        super("Package " + pkg + "is in conflict with installed package(s)!");
        this.pkg = pkg;
    }
}

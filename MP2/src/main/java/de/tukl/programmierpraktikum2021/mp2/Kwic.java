package de.tukl.programmierpraktikum2021.mp2;

import java.util.List;

public interface Kwic {
    void add(Book book);
    List<Book> search(String term);
}

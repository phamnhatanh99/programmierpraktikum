package de.tukl.programmierpraktikum2021.mp2;

import java.util.List;
import java.util.ArrayList;

public class KwicImpl implements Kwic{
    private Book book;
    private List<Book> books;

    @Override
    public void add(Book book) {
        if(books == null) books = new ArrayList<Book>();
        books.add(book);
    }

    @Override
    public List<Book> search(String term) {
        List<Book> res = new ArrayList<Book>();
        if(books != null) {
            for (Book item: books) {
                if(item.title.toUpperCase().contains(term.toUpperCase()) || item.author.toUpperCase().contains(term.toUpperCase()) || String.valueOf(item.year).contains(term.toUpperCase())) {
                    res.add(item);
                }
            }
        }
        return res;
    }















}

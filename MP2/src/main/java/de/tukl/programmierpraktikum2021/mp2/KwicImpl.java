package de.tukl.programmierpraktikum2021.mp2;
import com.googlecode.concurrenttrees.common.KeyValuePair;

import java.util.List;
import java.util.ArrayList;

public class KwicImpl implements Kwic{
    private Book book;
    private TrieMap<Book> books;

    @Override
    public void add(Book book) {
        if(books == null) books = new TrieMap<Book>();
        books.put(book.title, book);
    }

    @Override
    public List<Book> search(String term) {
        List<Book> list = new ArrayList<Book>();

        Iterable<KeyValuePair<Book>> res = books.searchKeyPrefix(term);

        for (KeyValuePair<Book> item : res) {
            list.add(item.getValue());

        }
        return list;
    }















}

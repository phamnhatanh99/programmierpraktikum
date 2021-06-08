package de.tukl.programmierpraktikum2021.mp2;
import com.googlecode.concurrenttrees.common.KeyValuePair;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class KwicImpl implements Kwic{
    private Book book;
    private TrieMap<Book> books;
    private List<Book> list;

    @Override
    public void add(Book book) {
        if(books == null) books = new TrieMap<Book>();

        String[] words = book.title.split(" ");
        String word = book.title;

        for (int i = 0; i < words.length; i++) {
            word = rotate(word);
            books.put(word.toLowerCase(), book);
        }
        for (String term: Util.exceptions) {
            Iterable<KeyValuePair<Book>> res = books.searchKeyPrefix( term.toLowerCase() + " ");
            for (KeyValuePair<Book> item : res) {
                books.remove((String) item.getKey());
            }
        }
    }

    private String rotate(String str){
        int i = str.indexOf(' ');
        if(i == -1 ){
            return  str;
        }
        else{
            String[] arr = str.split(" ", 2);
            String word = arr[0];   //the
            String rest = arr[1];
            return rest + " " + word;
        }

    }

    @Override
    public List<Book> search(String term) {
        list = new ArrayList<Book>();
        Iterable<KeyValuePair<Book>> res = books.searchKeyPrefix(term.toLowerCase());
        for (KeyValuePair<Book> item : res) {
            list.add(item.getValue());
        }
        return list;
    }

















}

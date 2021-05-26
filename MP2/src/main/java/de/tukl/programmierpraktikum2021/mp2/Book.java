package de.tukl.programmierpraktikum2021.mp2;

public class Book {
    public final String title;
    public final String author;
    public final int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}

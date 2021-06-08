package de.tukl.programmierpraktikum2021.mp2;
import java.util.*;
//Quelle zum Einlesen von Benutzereingaben: https://www.inf-schule.de/programmierung/oopjava/anhang/java 26.05.2021 12:13 Uhr

public class Main {
    public static void main(String[] args) {
        KwicImpl kwicimpl = new KwicImpl();
        for (Book book: Util.books){
            kwicimpl.add(book);
        }
        System.out.println("Please enter a search term! \n Search term:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<Book> matches = kwicimpl.search(input);
        if (matches.isEmpty()){
            System.out.println("Sorry, no match found for "+ input + ".");
        }
        else{
            for (Book match :matches) {
                for (Book book: Util.books) {
                        if (match.title.equals(book.title))
                            System.out.println("Autor: " + book.author + ". Titel: " + book.title + ". Jahr: " + book.year);
                    }
            }
        }
    }
}

package de.tukl.programmierpraktikum2021.mp2;
import java.util.*; // Noch vor der Klassendefinition importieren
//Quelle: https://www.inf-schule.de/programmierung/oopjava/anhang/java 26.05.2021 12:13 Uhr

public class Main {
    public static void main(String[] args) {
        KwicImpl kwicimpl = new KwicImpl();
        for (Book book: Util.books){
            kwicimpl.add(book);
        }
        System.out.println("Please enter a search term! \n search term:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<Book> matches = kwicimpl.search(input);
        if (matches == null){
            System.out.println("Sorry, no match found for "+ input + ".");
        }
        else{
            for (Book match :matches) {
                System.out.println("Author: " + match.getAuthor() + "\nTitle: " + match.getTitle() + "\nYear: " + match.getYear());
            }
        }
    }
}

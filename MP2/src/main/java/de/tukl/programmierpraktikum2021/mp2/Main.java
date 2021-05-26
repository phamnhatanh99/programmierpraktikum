package de.tukl.programmierpraktikum2021.mp2;
import java.util.*; // Noch vor der Klassendefinition importieren
//Quelle: https://www.inf-schule.de/programmierung/oopjava/anhang/java 26.05.2021 12:13 Uhr

public class Main {
    public static void main(String[] args) {
        System.out.println("Please enter a search term! \n search term:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Book match = new Book("ALive", "Fabian",2001);
        if (match == null){
            System.out.println("Sorry, no match found for "+ input + ".");
        }
        else{
            System.out.println("Author: "+match.getAuthor()+"\nTitle: "+match.getTitle()+"\nYear: "+match.getYear());
        }
    }
}

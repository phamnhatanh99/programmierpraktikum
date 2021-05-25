package de.tukl.programmierpraktikum2021.mp2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KwicTest {
    private static final String packageName = Trie.class.getPackageName();

    private static Stream<Kwic> getKwicInstance() {
        // Use Reflection in order to compile Tests without the Implementation
        boolean hasImplementation = false;
        Stream.Builder<Kwic> sb = Stream.builder();
        String className = "KwicImpl";
        try {
            sb.add((Kwic) Class.forName(packageName + "." + className).getConstructor().newInstance());
            hasImplementation = true;
        } catch (ClassNotFoundException e) {
            System.err.println(">>> Sie haben die Klasse " + className + " noch nicht implementiert!");
        } catch (Exception e) {
            System.err.println(">>> Fehler beim Instanziieren der Klasse " + className + ":");
            e.printStackTrace();
        }
        if (!hasImplementation) {
            sb.add(null);
        }
        return sb.build();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @ParameterizedTest
    @MethodSource("getKwicInstance")
    public void kwicBeispiele(Kwic kwic) {
        if (kwic == null) {
            throw new RuntimeException("Keine Kwic Implementierung gefunden! Die Klasse muss im Paket " + packageName + " abgelegt werden.");
        }
        System.out.println("Teste kwicBeispiele mit " + kwic.getClass().getSimpleName());

        Arrays.stream(Util.books).forEach(kwic::add);

        assertEquals(5, kwic.search("Haskell").size());

        Optional<Book> b1 = kwic.search("Haskell").stream().findFirst();
        assertTrue(b1.isPresent());
        assertEquals("Haskell 98 Language and Libraries", b1.get().title);
        assertEquals("Peyton Jones, Simon", b1.get().author);
        assertEquals(2003, b1.get().year);

        Optional<Book> b2 = kwic.search("Haskell").stream().filter(x -> x.year == 1999).findFirst();
        assertTrue(b2.isPresent());
        assertEquals("Haskell The Craft of Functional Programming", b2.get().title);
        assertEquals("Thompson, Simon", b2.get().author);

        assertEquals(14, kwic.search("The").size(), "Have you excluded book title rotations starting with uninformative words (c.f. Util.exceptions)?");
    }
}

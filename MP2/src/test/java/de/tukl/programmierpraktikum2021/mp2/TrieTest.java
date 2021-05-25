package de.tukl.programmierpraktikum2021.mp2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {
    private static final String packageName = Trie.class.getPackageName();

    @SuppressWarnings("unchecked")
    private static Stream<Trie<Integer>> getTrieInstance() {
        // Use Reflection in order to compile Tests without the Implementation
        boolean hasImplementation = false;
        Stream.Builder<Trie<Integer>> sb = Stream.builder();
        String className = "TrieMap";
        try {
            sb.add((Trie<Integer>) Class.forName(packageName + "." + className).getConstructor().newInstance());
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
    @MethodSource("getTrieInstance")
    public void trieBeispiele(Trie<Integer> trie) {
        if (trie == null) {
            throw new RuntimeException("Keine Trie Implementierung gefunden! Die Klasse muss im Paket " + packageName + " abgelegt werden.");
        }
        System.out.println("Teste trieBeispiele mit " + trie.getClass().getSimpleName());
        // get, put, size
        assertEquals(0, trie.size());
        trie.put("a", 1);
        trie.put("b", 2);
        assertNull(trie.get("someKey"), "Zu diesem Schlüssel ist kein Wert gespeichert, es soll null zurückgegeben werden");
        assertEquals(1, trie.get("a"));
        assertEquals(2, trie.get("b"));
        trie.put("b", 3);
        assertEquals(3, trie.get("b"), "put überschreibt Wert von existierendem Schlüssel nicht");
        assertEquals(2, trie.size());

        // remove (und Auswirkungen auf get, size)
        trie.remove("someKey");
        assertEquals(1, trie.get("a"), "Löschen eines nicht vorhandenen Schlüssels soll Trie nicht verändern!");
        assertEquals(3, trie.get("b"), "Löschen eines nicht vorhandenen Schlüssels soll Trie nicht verändern!");
        trie.remove("b");
        assertNull(trie.get("b"));
        assertEquals(1, trie.size());
        assertEquals(1, trie.get("a"), "Eintrag zum Schlüssel a soll nicht verändert werden, wenn b gelöscht wird");
        trie.remove("a");
        assertNull(trie.get("a"));
        assertEquals(0, trie.size());

        // searchKeyPrefix
        trie.put("Harry", 1);
        trie.put("Hacker", 2);
        trie.put("Lisa", 3);
        trie.put("Lista", 4);
        var s = StreamSupport.stream(trie.searchKeyPrefix("Ha").spliterator(), false);
        assertEquals(2, s.count(), "2 Ergebnisse erwartet bei Suche nach 'Ha'");
        var t = StreamSupport.stream(trie.searchKeyPrefix("Li").spliterator(), false);
        assertEquals(1, t.filter(x -> x.getKey().equals("Lista")).count(), "1 Ergebnis erwartet bei Suche nach 'Lista'");
        assertFalse(trie.searchKeyPrefix("Hugo").iterator().hasNext(), "Suche nach 'Hugo' sollte keinen Treffer erzielen");
    }
}

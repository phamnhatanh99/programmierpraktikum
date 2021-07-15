package de.tukl.programmierpraktikum2021.p2.a2;

import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;
import de.tukl.programmierpraktikum2021.p1.a2.*;
import de.tukl.programmierpraktikum2021.p1.a2.Package;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PacmanExtendedTest {

    public int countOccurrences(String str, String word) {
        int count = 0;
        if (!str.isEmpty() && !word.isEmpty()) {
            for (int i = 0; (i = str.indexOf(word, i)) != -1; i += word.length()) {
                count++;
            }
        }
        return count;
    }

    @Test
    public void testDependencyGraph() throws IOException, InvalidNodeException {
        PacmanExtended pacman = new PacmanExtendedImpl();
        pacman.buildDependencyGraph();

        Set<String> res = pacman.whoRequires("sqlite");
        assertEquals(6, res.size());
        assertTrue(res.containsAll(Arrays.asList("gnupg", "sqlite-analyzer", "sqlite-tcl", "elfutils", "nss", "sqlite3")));

        assertTrue(pacman.whoRequires("linux-api-headers").contains("glibc"));

        assertThrows(InvalidNodeException.class, () -> pacman.whoRequires("coincard"));
    }

    @Test
    public void testTransitiveDependencies() throws IOException, InvalidNodeException {
        PacmanExtended pacman = new PacmanExtendedImpl();
        pacman.buildDependencyGraph();

        assertThrows(InvalidNodeException.class, () -> pacman.transitiveDependencies("coincard"));

        String res = pacman.transitiveDependencies("sqlite");
        System.out.println(res);
        assertTrue(res.contains("sqlite-3.35.5-1"));
        assertTrue(res.contains("linux-api-headers-5.12.3-1"));
        assertTrue(res.contains("tzdata-2021a-1"));
        assertTrue(res.contains("filesystem-2021.05.31-1"));
        assertTrue(res.contains("ncurses provides libncursesw.so-6-64"));
        assertTrue(res.contains("glibc-2.33-5"));
        assertEquals(4, countOccurrences(res, "glibc-2.33-5"));
        assertEquals(1, countOccurrences(res, "provides"));
    }

    @Test
    public void testInstall1() throws IOException, InvalidNodeException {
        PacmanExtended pacman = new PacmanExtendedImpl();
        pacman.buildDependencyGraph();

        List<Package> res1 = pacman.buildInstallList("glibc");
        assertEquals(7, res1.size());
        assertEquals("filesystem", res1.get(0).getName());
        assertEquals("2021.05.31-1", res1.get(0).getVersion());
        List<String> res2 = new ArrayList<>();
        for (Package p : res1) {
            res2.add(p.getName());
        }

        assertTrue(res2.containsAll(Arrays.asList("linux-api-headers", "tzdata", "filesystem")));

        assertThrows(InvalidNodeException.class, () -> pacman.buildInstallList("coincard"));
    }


    @Test
    public void testInstall2() throws IOException, InvalidNodeException {
        PacmanExtended pacman = new PacmanExtendedImpl();
        pacman.buildDependencyGraph();

        assertThrows(InvalidNodeException.class, () -> pacman.install("coincard"));

        pacman.install("glibc");
        List<Package> res = pacman.buildInstallList("glibc");
        assertTrue(res.isEmpty());
        Set<String> res2 = pacman.getInstalled();
        Set<String> res3 = pacman.getInstalledExplicitly();
        assertEquals(7, res2.size());
        assertEquals(1, res3.size());
        assertTrue(res2.contains("tzdata"));
        assertTrue(res3.contains("glibc"));
    }

    @Test
    public void testConflicted() throws IOException, InvalidNodeException {
        PacmanExtended pacman = new PacmanExtendedImpl();
        pacman.buildDependencyGraph();

        pacman.install("iptables");
        pacman.install("iptables");



    }

    @Test
    public void testRemove() throws IOException, InvalidNodeException {
        PacmanExtended pacman = new PacmanExtendedImpl();
        pacman.buildDependencyGraph();
        pacman.install("linux-api-headers");
        pacman.remove("linux-api-headers");
        Set<String> res = pacman.getInstalledExplicitly();
        assertTrue(res.isEmpty());

        pacman.install("sqlite");
        pacman.remove("zlib");
        Set<String> res2 = pacman.getInstalled();
        assertFalse(res2.contains("zlib"));
        pacman.remove("sqlite");
        Set<String> res3 = pacman.getInstalled();
        assertFalse(res3.contains("sqlite"));
        
        pacman.install("glibc");
        pacman.remove("glibc");
        pacman.remove("ncurses");
        assertThrows(InvalidNodeException.class , () -> pacman.remove("notInstalled"));
    }

}


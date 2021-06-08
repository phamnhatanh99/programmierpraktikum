package de.tukl.programmierpraktikum2021.p1.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Util {
    private final Map<String, String> pkgVersion = new HashMap<>();
    private final Map<String, List<String>> pkgDependencies = new HashMap<>();
    private final Map<String, List<String>> pkgVirtual = new HashMap<>();


    // Get names of all normal (i.e. non virtual) packages in the package repository.
    public Set<String> getAllNormalPackages() {
        return pkgVersion.keySet();
    }

    // Get names of all virtual packages in the package repository.
    public Set<String> getAllVirtualPackages() {
        return pkgVirtual.keySet();
    }

    // get version of a package pkg
    public String getVersion(String pkg) {
        return pkgVersion.get(pkg);
    }

    // get list of dependencies for package pkg
    public List<String> getDependencies(String pkg) {
        return pkgDependencies.get(pkg);
    }

    // get names of all virtual packages that the package pkg provides
    public List<String> getVirtual(String pkg) {
        return pkgVirtual.get(pkg);
    }


    private void parsePacmanDesc(String content) {
        String[] parts = content.split("(\\r\\n|\\n|\\r){2}");
        Map<String, List<String>> m = new HashMap<>();
        for (String p : parts) {
            List<String> partLines = new ArrayList<>(Arrays.asList(p.split("(\\r\\n|\\n|\\r)")));
            String key = partLines.remove(0);
            m.put(key.replace("%", ""), partLines);
        }
        pkgVersion.put(m.get("NAME").get(0), m.get("VERSION").get(0));
        pkgDependencies.put(m.get("NAME").get(0), m.get("DEPENDS"));
        pkgVirtual.put(m.get("NAME").get(0), m.get("PROVIDES"));
    }

    Util() throws IOException {
        ZipFile zip = new ZipFile("./src/main/resources/core.db.zip");
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
                InputStream s = zip.getInputStream(entry);
                String result = new BufferedReader(new InputStreamReader(s)).lines().collect(Collectors.joining("\n"));
                parsePacmanDesc(result);
            }
        }
        zip.close();
    }

}

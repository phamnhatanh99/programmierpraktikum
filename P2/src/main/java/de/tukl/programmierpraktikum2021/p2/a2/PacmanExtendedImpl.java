package de.tukl.programmierpraktikum2021.p2.a2;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;
import de.tukl.programmierpraktikum2021.p1.a2.*;
import de.tukl.programmierpraktikum2021.p1.a2.Package;
import java.util.*;

public class PacmanExtendedImpl extends PacmanImpl implements PacmanExtended {

    @Override
    public Set<String> getInstalled() {
        return installed;
    }

    @Override
    public Set<String> getInstalledExplicitly() {
        return explicitlyInstalled;
    }

    @Override
    public List<Package> buildInstallList(String pack) throws InvalidNodeException {

        List<Package> installList = new ArrayList<>();

        LinkedHashSet<String> installNameSet = new LinkedHashSet<>() {
            @Override
            public boolean add (String s) {
                if (contains(s)) remove(s);
                return super.add(s);
            }
        };

        LinkedHashSet<String> level = new LinkedHashSet<>();
        level.add(pack);

        while (!level.isEmpty()) {
            LinkedHashSet<String> directDependencies = new LinkedHashSet<>();

            for (String paket: level) {
                directDependencies.addAll(g.getOutgoingNeighbors(paket));
            }

            // Packages in cyclic dependency will appear again in directDependencies of one level
            // even when they are already added to installNameSet by some levels above.
            // Before passing directDependencies onto be the next level, remove all those package.
            level.clear();
            level.addAll(directDependencies);
            level.removeAll(installNameSet);
            installNameSet.addAll(directDependencies);
        }

        installNameSet.removeAll(installed);
        List<String> nameList = new ArrayList<>(installNameSet);
        Collections.reverse(nameList);
        for (String name : nameList) {
            installList.add(g.getData(name));
        }
        return installList;
    }

    /* Install all prerequisite packages of pkg first, then finally pkg.
     * Names of installed package are stored in the list "install"
     * */
    @Override
    public void install(String pkg) throws InvalidNodeException {
        List<Package> packageList = buildInstallList(pkg);
        Set<String> toInstall = new HashSet<>();
        toInstall.add(pkg);

        for (Package p : packageList) {
            toInstall.add(p.getName());
        }

        List<String> whatIf = new ArrayList<>(installed);
        whatIf.addAll(toInstall);

        // Check if conflicted
        boolean conflicted = false;
        for (Set<String> pair : conflicts) {
            if (whatIf.containsAll(pair)) {
                conflicted = true;
                break;
            }
        }

        if (!conflicted) {
            // Check for cycle
            Set<String> dbCycle = g.getCycles();
            Set<String> cycle = new HashSet<>(toInstall);
            cycle.retainAll(dbCycle);
            if (!cycle.isEmpty()) {
                String cycleString = String.join(", ", cycle);
                System.out.println(cycleString + " formed cyclic dependency!");
            }
            // Install
            installed.remove(pkg); // Remove old version
            for (Package p:packageList) {
                installed.add(p.getName());
            }
            explicitlyInstalled.add(pkg);
        }
        else
            System.out.println("Conflicts while installing " + pkg + " detected!");
    }

    @Override
    public void remove(String pkg) throws InvalidNodeException {
        throw new RuntimeException("TODO");
    }

}

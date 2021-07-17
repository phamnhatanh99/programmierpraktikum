package de.tukl.programmierpraktikum2021.p2.a2;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;
import de.tukl.programmierpraktikum2021.p1.a2.*;
import de.tukl.programmierpraktikum2021.p1.a2.Package;
import de.tukl.programmierpraktikum2021.p2.a1.GraphExtendedImpl;

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
    public String transitiveDependencies(String pkg) throws InvalidNodeException {
        return listPackagesNoCycles(pkg,"", new ArrayList<>());
    }

    private String listPackagesNoCycles(String pkg, String indent, ArrayList<String> parents) throws InvalidNodeException {
        StringBuilder res = new StringBuilder(g.getData(pkg) + "\n");
        Iterator<String> pkgs = g.getOutgoingNeighbors(pkg).iterator();

        if (parents.contains(pkg)) {
            parents.add("End");
            return res.toString();
        }
        else{
            parents.add(pkg);
            while(pkgs.hasNext()) {
                res.append(indent).append("|___").append(listPackagesNoCycles(pkgs.next(),indent + (pkgs.hasNext()? "|   ":"    "), parents));
                parents.remove(parents.size() - 1);
            }
            return res.toString();
        }
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
                Package p = g.getData(paket);
                //if the package is virtual, remove itself from the list to add its provider instead
                if(g.getData(paket) instanceof VirtualPackage){
                    directDependencies.add(((VirtualPackage) p).getVirtualSource());
                    installNameSet.remove(paket);
                }
                else{
                    directDependencies.addAll(g.getOutgoingNeighbors(paket));
                }

            }
            //Packages in cyclic dependency will appear again in directDependencies of one level
            //even when they are already added to installNameSet by some levels above.
            //Before passing directDependencies onto next level, remove all those package.
            level.clear();
            level.addAll(directDependencies);
            level.removeAll(installNameSet);

            installNameSet.addAll(directDependencies);

        }

        installNameSet.removeAll(installed);
        List<String> nameList= new ArrayList<>(installNameSet);
        Collections.reverse(nameList);
        for (String name: nameList) {
            installList.add(g.getData(name));
        }
        return installList;
    }

    /* Install all prerequisite packages of pkg first, then finally pkg.
     * Names of installed package are stored in the list "install"
     * */
    @Override
    public void install(String pkg) throws InvalidNodeException{
        List<Package> packageList = buildInstallList(pkg);
        Set<String> toInstall = new HashSet<>();
        toInstall.add(pkg);
        for (Package p : packageList) {
            toInstall.add(p.getName());
        }
        List<String> whatIf= new ArrayList<>(installed);
        whatIf.addAll(toInstall);
        // Check if conflicted
        boolean conflicted = false;
        for (Set<String> pair:conflicts){
            if(whatIf.containsAll(pair)){
                conflicted=true;
                break;
            }
        }

        if (!conflicted) {
            //Check for cycle
            Set<String> dbCycle = g.getCycles();
            Set<String> cycle = new HashSet<>(toInstall);
            cycle.retainAll(dbCycle);
            if (!cycle.isEmpty()) {
                String cycleString = String.join(",", cycle);
                System.out.println(cycleString + " formed cyclic dependency!");
            }
            //Install
            installed.remove(pkg); //remove old version
            installed.addAll(toInstall);
            explicitlyInstalled.add(pkg);

        }else {
            System.out.println("Conflicts while installing "+pkg+ " detected!");
        }
    }

    @Override
    public void remove(String pkg) throws InvalidNodeException {
        // Check if any package needs pkg
        Set<String> anyoneRequires = whoRequires(pkg);
        anyoneRequires.retainAll(installed);
        // Compute the cycle in Graph
        Set<String> cycle = g.getCycles();

        if (anyoneRequires.isEmpty()) {
            installed.remove(pkg);
            explicitlyInstalled.remove(pkg);
            Set<String> directDependence = g.getOutgoingNeighbors(pkg);

            // Remove all implicitly installed packages
            while (!directDependence.isEmpty()) {
                Set<String> nextLevel = new HashSet<>();
                for (String pack : directDependence) {
                    nextLevel.addAll(g.getOutgoingNeighbors(pack));
                    Set<String> needy = whoRequires(pack);
                    needy.retainAll(installed);
                    if (needy.isEmpty()) {
                        installed.remove(pack);
                        explicitlyInstalled.remove(pack);
                    }
                }
                nextLevel.removeAll(cycle);
                // Passing all dependencies onto next level except for ones in cycle
                directDependence = nextLevel;
            }
        }
        else
            System.out.println(anyoneRequires + " still depend(s) on " + pkg);
    }
}

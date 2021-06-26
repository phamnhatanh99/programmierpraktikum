package de.tukl.programmierpraktikum2021.p1.a2;
import de.tukl.programmierpraktikum2021.p1.a1.GraphImpl;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;

import java.io.IOException;
import java.util.*;

public class PacmanImpl implements Pacman {
    private final GraphImpl<Package> g = new GraphImpl<>();
    private final Set<String> installed = new HashSet<>();

    @Override
    public void buildDependencyGraph() throws IOException {
        Util u = new Util();

        //Adding normal packages
        for (String normalPackage : u.getAllNormalPackages()) {
            NormalPackage pack = new NormalPackage(normalPackage, u.getVersion(normalPackage));
            g.addNode(normalPackage, pack);
        }

        //Adding virtual packages and their dependencies to the provider packages
        for (String providerPackage : u.getAllVirtualPackages()) {
            if (u.getVirtual(providerPackage) != null) {
                for (String virtualPackage : u.getVirtual(providerPackage)) {
                    VirtualPackage pack = new VirtualPackage(virtualPackage, u.getVersion(virtualPackage), providerPackage);
                    g.addNode(virtualPackage, pack);
                    try {
                        g.addEdge(virtualPackage, providerPackage);
                    }
                    catch (Exception ignored) {}
                }
            }
        }

        //Adding all other dependencies
        for (String node : g.getNodeIds()) {
            if (u.getDependencies(node) != null) {
                for (String dependence : u.getDependencies(node)) {
                    if (!dependence.contains(">")) {
                        try {
                            g.addEdge(node, dependence);
                        }
                        catch (Exception ignored) {}
                    }
                    else try {
                        g.addEdge(node, dependence.substring(0, dependence.indexOf(">")));
                    }
                    catch (Exception ignored) {}
                }
            }
        }
    }

    @Override
    public Set<String> whoRequires(String pkg) throws InvalidNodeException {
        return g.getIncomingNeighbors(pkg);
    }

    @Override
    public String transitiveDependencies(String pkg) throws InvalidNodeException {
        StringBuilder res = new StringBuilder(pkg + "\n");
        Set<String> pkgs = g.getOutgoingNeighbors(pkg);
        for(String item : pkgs){
            res.append("|___").append(listPackages(item, 1));
        }
        System.out.println(res);
        return res.toString();
    }

    private String listPackages(String pkg , int counter) throws InvalidNodeException {
        Set<String> pkgs = g.getOutgoingNeighbors(pkg);
        StringBuilder res = new StringBuilder(pkg + "\n");
        for(String item : pkgs){
             res.append(depth(counter)).append("|___").append(listPackages(item, counter + 1));
        }
        return res.toString();
    }

    private String depth(int count) throws InvalidNodeException {
        String res = "";
        String tab = "    ";
        for (int i = 0; i < count; i++) {
            res += tab;
        }
        return res;
    }

    /* Run through the dependency graph of Package pkg Level by Level (Level-Order-Traversal)
     * 1st Level = directDependencies of pkg, 2nd Level = directDependencies of Level 1 etc. ...
     * Add the direct dependencies in the NameSet, these dependencies then become next level
     * Repeat till there's no dependency left aka. level is empty
     * Compare NameSet with Installed List to removed already installed packages
     * Fetch the packages in NameSet into installList in reverse order. Return this list of packages.
     * */
    @Override
    public List<Package> buildInstallList(String pkg) throws InvalidNodeException {
        List<Package> installList = new ArrayList<>();

        LinkedHashSet<String> installNameSet = new LinkedHashSet<>() {
            @Override
            public boolean add (String s) {
                if (contains(s)) remove(s);
                return super.add(s);
            }
        };

        LinkedHashSet<String> level = new LinkedHashSet<>();
        level.add(pkg);

        while (!level.isEmpty()) {
            LinkedHashSet<String> directDependencies = new LinkedHashSet<>();
            for (String paket: level) {
                directDependencies.addAll(g.getOutgoingNeighbors(paket));
            }
            installNameSet.addAll(directDependencies);
            level = directDependencies;
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
    public void install(String pkg) throws InvalidNodeException {
        installed.remove(pkg);
        List<Package> toInstall = buildInstallList(pkg);
        for (Package p : toInstall) {
            installed.add(p.getName());
        }
        installed.add(pkg);
    }

}


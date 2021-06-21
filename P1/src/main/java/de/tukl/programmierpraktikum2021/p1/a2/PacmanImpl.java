package de.tukl.programmierpraktikum2021.p1.a2;

import de.tukl.programmierpraktikum2021.p1.a1.DuplicateEdgeException;
import de.tukl.programmierpraktikum2021.p1.a1.GraphImpl;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;

import java.io.IOException;
import java.util.*;

public class PacmanImpl implements Pacman {
    GraphImpl<Package> g = new GraphImpl<>();
    Set<String> installed = new HashSet<>();
    @Override
    public void buildDependencyGraph() throws IOException {
        Util u = new Util();
        for (String normalPackage : u.getAllNormalPackages()) {
            NormalPackage pack = new NormalPackage(normalPackage, u.getVersion(normalPackage));
            g.addNode(normalPackage, pack);
        }

        for (String providerPackage : u.getAllVirtualPackages()) {
            for(String virtualPackage : u.getVirtual(providerPackage)) {
                VirtualPackage pack = new VirtualPackage(virtualPackage, u.getVersion(virtualPackage),providerPackage);
                g.addNode(virtualPackage, pack);
                try {
                    g.addEdge(virtualPackage,providerPackage);
                }
                catch (Exception ignored) {}

            }
        }

        for (String node : g.getNodeIds()) {
            if (u.getDependencies(node) != null) {
                for (String dependence : u.getDependencies(node)) {
                    try {
                        g.addEdge(node, dependence);
                    }
                    catch (Exception ignored) {}
                }
            }

/*             if (u.getVirtual(node) != null) {
                for (String virtualPackage : u.getVirtual(node)) {
                    try {
                        g.addEdge(virtualPackage,node);
                    }
                    catch (Exception ignored){}
                }
            }*/
        }
    }

    @Override
    public Set<String> whoRequires(String pkg) throws InvalidNodeException {
        return null;
    }
    @Override
    public String transitiveDependencies(String pkg) throws InvalidNodeException {
        return null;
    }

    /* Run through the dependency graph of Package pkg Level by Level (Level-Order-Traversal)
* 1st Level = directDependencies of pkg, 2nd Level = directDependencies of Level 1 usw.
* Add the direct dependencies in the NameSet, these dependencies then become next level
* Repeat till there's no dependency left aka. level is empty
* Compare NameSet with Installed List to removed already installed packages
* Fetch the packages in NameSet into installList in reverse order. Return this list of packages.
* */
    @Override
    public List<Package> buildInstallList(String pkg) throws InvalidNodeException {
        List<Package> installList = new ArrayList<>();
        LinkedHashSet<String> installNameSet = new LinkedHashSet<>();
        LinkedHashSet<String> level = new LinkedHashSet<>();
        level.add(pkg);

        while (!level.isEmpty()){
            LinkedHashSet<String> directDependencies = new LinkedHashSet<>();
            for(String paket: level){
                directDependencies.addAll(g.getOutgoingNeighbors(paket));
            }
            installNameSet.addAll(directDependencies);
            level = directDependencies;
        }

        installNameSet.removeAll(installed);

        List<String> nameList= new ArrayList<>(installNameSet);
        Collections.reverse(nameList);
        for(String name: nameList){
            installList.add(g.getData(name));
        }
        return installList;
    }

    /* Install all prerequisite packages of pkg first, then finally pkg.
* Names of installed package are stored in the list "install"
* */
    @Override
    public void install(String pkg) throws InvalidNodeException {
        List<Package> toInstall = buildInstallList(pkg);
        for(Package p : toInstall){
            installed.add(p.getName());
        }
        installed.add(pkg);
    }
}

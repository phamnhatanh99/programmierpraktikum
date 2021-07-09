package de.tukl.programmierpraktikum2021.p2.a2;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;
import de.tukl.programmierpraktikum2021.p1.a2.*;
import de.tukl.programmierpraktikum2021.p1.a2.Package;
import de.tukl.programmierpraktikum2021.p2.a1.GraphExtendedImpl;

import java.io.IOException;
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
    public Set<String> getCyc(){
        return g.getCycles();
    }
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
            //Packages in cyclic dependency will appear again in directDependencies of one level
            //even when they are already added to installNameSet by some levels above.
            //Before passing directDependencies onto be the next level, remove all those package.
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
    public void install(String pkg) throws InvalidNodeException, IOException, ConflictException, CyclicDependencyException {
        Util u = new Util("./src/main/resources/core-cycle.db.zip");
        List<Package> toInstall = buildInstallList(pkg);
        GraphExtendedImpl<Package> pkgGraph = new GraphExtendedImpl<>();
        List<Package> pkgBuild = new ArrayList<>();
        NormalPackage pack1= new NormalPackage(pkg,u.getVersion(pkg));
        //Build the set of nodes that are in dependency tree of pkg
        pkgBuild.add(pack1);
        pkgBuild.addAll(toInstall);
        // Check if conflicted
        boolean conflicted = false;
        for(Package pack: pkgBuild){
            for(String conf:u.getConflicts(pack.getName())){
                if (installed.contains(conf)){
                    conflicted = true;
                    throw new ConflictException(pack.getName());
                }
            }
        }

        //Check for Cycle
        //Build dependency tree for pkg
        for (Package p: pkgBuild){
            if(u.getDependencies(p.getName())!=null){
                for (String dependence : u.getDependencies(p.getName())) {
                    // Check if the name of dependencies contains ">", then we have to remove the part after that
                    // so we don't get InvalidNodeException
                    if (!dependence.contains(">")) {
                        try {
                            pkgGraph.addEdge(p.getName(), dependence);
                        }
                        catch (Exception ignored) {} // Skip if edge already exists
                    }
                    else try {
                        pkgGraph.addEdge(p.getName(), dependence.substring(0, dependence.indexOf(">")));
                    }
                    catch (Exception ignored) {} // Skip if edge already exists
                }
            }
        }
        Set<String> cycle = pkgGraph.getCycles();
        if(!cycle.isEmpty()){
            String cycleString = String.join(",", cycle);
            System.out.println(cycleString+"formed cyclic dependency!");
        }
        //Install
        installed.remove(pkg); //remove old version
        for (Package p : toInstall) {
            installed.add(p.getName());
        }
        installed.add(pkg);
        explicitlyInstalled.add(pkg);


    }


    @Override
    public void remove(String pkg) throws InvalidNodeException {
        throw new RuntimeException("TODO");
    }
}

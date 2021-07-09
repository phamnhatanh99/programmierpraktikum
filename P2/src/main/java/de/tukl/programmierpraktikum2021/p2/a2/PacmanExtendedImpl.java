package de.tukl.programmierpraktikum2021.p2.a2;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;
import de.tukl.programmierpraktikum2021.p1.a2.*;
import de.tukl.programmierpraktikum2021.p1.a2.Package;


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
           //System.out.println("level: "+level);
            installNameSet.addAll(directDependencies);
            //System.out.println("NameSet: "+installNameSet);

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
    public void install(String pkg) throws InvalidNodeException, IOException, ConflictException{
        Util u = new Util("./src/main/resources/core-cycle.db.zip");
        List<Package> toInstall = buildInstallList(pkg);
        Set<String> pkgTreeNodes = new HashSet<>();
        //Build the set of nodes that are in dependency tree of pkg
        pkgTreeNodes.add(pkg);
        for (Package p : toInstall) {
            pkgTreeNodes.add(p.getName());
        }
        // Check if conflicted

        for(String pack: pkgTreeNodes){
            if(u.getConflicts(pack)!=null){
                for(String conf:u.getConflicts(pack)){
                    if (installed.contains(conf)){
                        throw new ConflictException(pack);
                    }
                }
            }
        }


        Set<String> dbCycle = g.getCycles();
        Set<String> cycle = new HashSet<>(pkgTreeNodes);
        cycle.retainAll(dbCycle);
        if(!cycle.isEmpty()){
            String cycleString = String.join(",", cycle);
            System.out.println(cycleString+" formed cyclic dependency!");
        }
        //Install
        installed.remove(pkg); //remove old version
        installed.addAll(pkgTreeNodes);
        explicitlyInstalled.add(pkg);


    }


    @Override
    public void remove(String pkg) throws InvalidNodeException {
        throw new RuntimeException("TODO");
    }
}

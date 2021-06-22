package de.tukl.programmierpraktikum2021.p1.a2;

import de.tukl.programmierpraktikum2021.p1.a1.DuplicateEdgeException;
import de.tukl.programmierpraktikum2021.p1.a1.GraphImpl;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class PacmanImpl implements Pacman {
    GraphImpl g = new GraphImpl();
    
    @Override
    public void buildDependencyGraph() throws IOException {
        Util u = new Util();
        for (String normalPackage : u.getAllNormalPackages()) {
            NormalPackage pack = new NormalPackage(normalPackage, u.getVersion(normalPackage));
            g.addNode(normalPackage, pack);
        }
        for (String virtualPackage : u.getAllVirtualPackages()) {
            NormalPackage pack = new NormalPackage(virtualPackage, u.getVersion(virtualPackage));
            g.addNode(virtualPackage, pack);
        }
        for (String node : g.getNodeIds()) {
            if (u.getDependencies(node) != null) {
                for (String dependence : u.getDependencies(node)) {
                    try {
                        g.addEdge(dependence ,node);
                    }
                    catch (Exception ignored) {}
                }
            }
            if (u.getVirtual(node) != null) {
                for (String virtualPackage : u.getVirtual(node)) {
                    try {
                        g.addEdge(virtualPackage, node);
                    }
                    catch (Exception ignored){}
                }
            }
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

    @Override
    public List<Package> buildInstallList(String pkg) throws InvalidNodeException {
        return null;
    }

    @Override
    public void install(String pkg) throws InvalidNodeException {

    }
}

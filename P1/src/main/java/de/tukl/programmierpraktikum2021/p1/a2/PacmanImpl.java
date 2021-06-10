package de.tukl.programmierpraktikum2021.p1.a2;

import de.tukl.programmierpraktikum2021.p1.a1.DuplicateEdgeException;
import de.tukl.programmierpraktikum2021.p1.a1.GraphImpl;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class PacmanImpl implements Pacman {

    @Override
    public void buildDependencyGraph() throws IOException {
        Util u = new Util();
        GraphImpl g = new GraphImpl();
        for (String normalPackage : u.getAllNormalPackages()) {
            g.addNode(normalPackage, u.getVersion(normalPackage));
        }
        for (String virtualPackage : u.getAllVirtualPackages()) {
            g.addNode(virtualPackage, u.getVersion(virtualPackage));
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
            if (u.getVirtual(node) != null) {
                for (String virtualPackage : u.getVirtual(node)) {
                    try {
                        g.addEdge(node, virtualPackage);
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

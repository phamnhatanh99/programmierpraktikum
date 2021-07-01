package de.tukl.programmierpraktikum2021.p2.a2;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;
import de.tukl.programmierpraktikum2021.p1.a2.PacmanImpl;

import java.util.Set;

public class PacmanExtendedImpl extends PacmanImpl implements PacmanExtended {
    @Override
    public Set<String> getInstalled() {
        throw new RuntimeException("TODO");
    }

    @Override
    public Set<String> getInstalledExplicitly() {
        throw new RuntimeException("TODO");
    }

    @Override
    public void remove(String pkg) throws InvalidNodeException {
        throw new RuntimeException("TODO");
    }
}

package de.tukl.programmierpraktikum2021.p2.a1;
import de.tukl.programmierpraktikum2021.p1.a1.GraphImpl;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;

import java.util.List;

public class GraphExtendedImpl<D> extends GraphImpl<D> implements GraphExtended<D> {
    @Override
    public boolean hasCycle() {
        throw new RuntimeException("TODO");
    }

    @Override
    public boolean isConnected() {
        throw new RuntimeException("TODO");
    }

    @Override
    public List<String> breadthFirstSearch(String fromId, String toId) throws InvalidNodeException {
        throw new RuntimeException("TODO");
    }
}

package de.tukl.programmierpraktikum2021.p1.a1;
import java.util.*;

public class GraphImpl<D> implements Graph<D> {
    protected HashMap<String, D> nodes = new HashMap<>();
    protected ArrayList<ArrayList<String>> edges = new ArrayList<>();

    @Override
    public void addNode(String name, D data) {
        nodes.put(name, data);
    }

    @Override
    public D getData(String nodeId) throws InvalidNodeException {
        if (!nodes.containsKey(nodeId)) throw new InvalidNodeException(nodeId);
        return nodes.get(nodeId);
    }

    @Override
    public void setData(String nodeId, D data) throws InvalidNodeException {
        if (!nodes.containsKey(nodeId)) throw new InvalidNodeException(nodeId);
        nodes.put(nodeId, data);
    }

    @Override
    public void addEdge(String fromId, String toId) throws InvalidNodeException, DuplicateEdgeException {
        if (!(nodes.containsKey(fromId))) throw new InvalidNodeException(fromId);
        if (!(nodes.containsKey(toId))) throw new InvalidNodeException(toId);
        for (ArrayList<String> edge : edges) {
            if (edge.get(0).equals(fromId) && edge.get(1).equals(toId)) {
                throw new DuplicateEdgeException(fromId, toId);
            }
        }
        ArrayList<String> arr = new ArrayList<>();
        arr.add(fromId);
        arr.add(toId);
        this.edges.add(arr);
    }

    @Override
    public Set<String> getNodeIds() {
        return nodes.keySet();
    }

    @Override
    public Set<String> getIncomingNeighbors(String nodeId) throws InvalidNodeException {
        if (!(nodes.containsKey(nodeId))) throw new InvalidNodeException(nodeId);
        Set<String> incomingNeighbors = new HashSet<>();
        for (ArrayList<String> edge : edges) {
            if (edge.get(1).equals(nodeId)) {
                incomingNeighbors.add(edge.get(0));
            }
        }
        return incomingNeighbors;
    }

    @Override
    public Set<String> getOutgoingNeighbors(String nodeId) throws InvalidNodeException {
        if (!(nodes.containsKey(nodeId))) throw new InvalidNodeException(nodeId);
        Set<String> outgoingNeighbors = new HashSet<>();
        for (ArrayList<String> edge : edges) {
            if (edge.get(0).equals(nodeId)) {
                outgoingNeighbors.add(edge.get(1));
            }
        }
        return outgoingNeighbors;
    }

    @Override
    public String toString() {
        return "Nodes: " + nodes + "\nEdges: " + edges;
    }
}


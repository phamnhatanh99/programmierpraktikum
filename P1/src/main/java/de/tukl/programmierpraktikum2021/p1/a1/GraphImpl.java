package de.tukl.programmierpraktikum2021.p1.a1;

import java.util.*;

public class GraphImpl implements Graph {
    private Map<String, Object> nodes = new HashMap<>();
    private ArrayList<ArrayList<String>> edges = new ArrayList();
    //private Exception InvalidNodeException;

    @Override
    public void addNode(String name, Object data) {
        nodes.put(name, data);
    }

    @Override
    public Object getData(String nodeId) throws InvalidNodeException {
        Object data = nodes.get(nodeId);
        if (data == null) throw new InvalidNodeException(nodeId);
        else return data;
    }

    @Override
    public void setData(String nodeId, Object data) throws InvalidNodeException {
        if (nodes.containsKey(nodeId)) nodes.put(nodeId, data);
        else throw new InvalidNodeException(nodeId);
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

}


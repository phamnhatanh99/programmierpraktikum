package de.tukl.programmierpraktikum2021.p2.a1;
import de.tukl.programmierpraktikum2021.p1.a1.GraphImpl;
import de.tukl.programmierpraktikum2021.p1.a1.InvalidNodeException;

import java.util.*;

public class GraphExtendedImpl<D> extends GraphImpl<D> implements GraphExtended<D> {

    public GraphExtendedImpl() {}

    private GraphExtendedImpl(HashMap<String, D> nodes, ArrayList<ArrayList<String>> edges) {
        this.nodes = nodes;
        this.edges = new ArrayList<>(edges.size());
        for (ArrayList<String> edge : edges) {
            this.edges.add(new ArrayList<>(edge));
        }
    }

    @Override
    public boolean hasCycle() {
        // An empty graph is acyclic
        if (nodes.isEmpty()) return false;

        // Run topological sorting algorithm, return false if there is a topological sorting
        return !topologicalSorting().isEmpty();
    }

    // When the graph is seen as a flow of work. This algorithm tries to create a list of nodes sorted in
    // an order such that the nodes/tasks that need to be done first (no incoming neighbor) appear first in
    // the list. It cannot however return a sorting if the graph has cycles.
    private ArrayList<ArrayList<String>> topologicalSorting() {
        Set<String> scan = new HashSet<>();
        Set<String> toRemove = new HashSet<>();
        Set<String> toAdd = new HashSet<>();
        GraphExtendedImpl<D> cloned = new GraphExtendedImpl<>(this.nodes, this.edges);

        // Add nodes without incoming neighbors to scan set
        for (String node : cloned.nodes.keySet()) {
            try {
                if (cloned.getIncomingNeighbors(node).isEmpty())
                    scan.add(node);
            } catch (Exception ignored) {}
        }

        while (!scan.isEmpty()) {
            for (String node : scan) {
                toRemove.add(node);
                try {
                    for (String nodeOut : cloned.getOutgoingNeighbors(node)) {
                        cloned.edges.removeIf(edge -> edge.get(0).equals(node) && edge.get(1).equals(nodeOut));
                        if (cloned.getIncomingNeighbors(nodeOut).isEmpty())
                            toAdd.add(nodeOut);
                    }
                } catch (Exception ignored) {}
            }
            scan.removeAll(toRemove);
            toRemove.clear();
            scan.addAll(toAdd);
            toAdd.clear();
        }
        // Extend topological sorting to filter out everything but the cycles
        // Add nodes without outgoing neighbors to scan set
        for (String node : cloned.nodes.keySet()) {
            try {
                if (cloned.getOutgoingNeighbors(node).isEmpty())
                    scan.add(node);
            } catch (Exception ignored) {}
        }

        while (!scan.isEmpty()) {
            for (String node : scan) {
                toRemove.add(node);
                try {
                    for (String nodeIn : cloned.getIncomingNeighbors(node)) {
                        cloned.edges.removeIf(edge -> edge.get(0).equals(nodeIn) && edge.get(1).equals(node));
                        if (cloned.getOutgoingNeighbors(nodeIn).isEmpty())
                            toAdd.add(nodeIn);
                    }
                } catch (Exception ignored) {}
            }
            scan.removeAll(toRemove);
            toRemove.clear();
            scan.addAll(toAdd);
            toAdd.clear();
        }

        return cloned.edges;
    }

    // Returns a set of nodes in the cycles
    public Set<String> getCycles() {
        Set<String> res = new HashSet<>();
        for (ArrayList<String> edge : topologicalSorting()) {
            res.addAll(edge);
        }
        return res;
    }

    @Override
    public boolean isConnected() {
        // An empty graph is connected
        if (nodes.isEmpty()) return true;

        // Run BFS on undirected subgraph
        List<String> nodesList = new ArrayList<>(nodes.keySet());
        String node = nodesList.get(0);

        Set<String> toAdd = new HashSet<>();
        try {
            toAdd.addAll(getIncomingNeighbors(node));
            toAdd.addAll(getOutgoingNeighbors(node));
        } catch (Exception ignored) {}
        LinkedList<String> toVisit = new LinkedList<>(toAdd);
        toAdd.clear();

        Set<String> visited = new HashSet<>();
        visited.add(node);

        while (!toVisit.isEmpty()) {
            String next = toVisit.pop();
            visited.add(next);
            try {
                toAdd.addAll(getIncomingNeighbors(next));
                toAdd.addAll(getOutgoingNeighbors(next));
            } catch (Exception ignored) {}
            for (String neighbor : toAdd) {
                if (!toVisit.contains(neighbor) && !visited.contains(neighbor))
                    toVisit.add(neighbor);
            }
            toAdd.clear();
        }

        // Returns true if all nodes were visited by the BFS
        return visited.size() == nodes.size();
    }

    @Override
    public List<String> breadthFirstSearch(String fromId, String toId) throws InvalidNodeException {
        // Throw exception if given nodes don't exist in graph or are null
        if (!nodes.containsKey(fromId) || fromId == null) throw new InvalidNodeException(fromId);
        if (!nodes.containsKey(toId) || toId == null) throw new InvalidNodeException(toId);

        // BFS
        HashMap<String, String> parentMap = new HashMap<>();
        parentMap.put(fromId, null);

        Set<String> tmp = getOutgoingNeighbors(fromId);
        for (String n : tmp) {
            parentMap.put(n, fromId);
        }

        Set<String> visited = new HashSet<>();
        visited.add(fromId);

        Set<String> toAdd = new HashSet<>(tmp);
        LinkedList<String> toVisit = new LinkedList<>(toAdd);
        toAdd.clear();

        while (!toVisit.isEmpty()) {
            String next = toVisit.pop();
            visited.add(next);
            Set<String> nextNeighbors = getOutgoingNeighbors(next);
            for (String n : nextNeighbors) {
                    parentMap.putIfAbsent(n, next);
            }
            toAdd.addAll(nextNeighbors);
            for (String neighbor : toAdd) {
                if (!toVisit.contains(neighbor) && !visited.contains(neighbor))
                    toVisit.add(neighbor);
            }
            if (toVisit.contains(toId)) break;
            toAdd.clear();
        }

        // Construct shortest path
        return getPath(parentMap, fromId, toId, new ArrayList<>());
    }

    // Method to construct shortest path from the BFS
    private List<String> getPath(HashMap<String, String> parentMap, String fromId, String toId, List<String> list) {
        if (!parentMap.containsKey(toId)) return list;
        if (fromId.equals(toId)) {
            list.add(0, fromId);
            return list;
        }
        else {
            list.add(0, toId);
            return getPath(parentMap, fromId, parentMap.get(toId), list);
        }
    }
}

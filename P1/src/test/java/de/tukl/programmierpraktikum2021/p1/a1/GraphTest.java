package de.tukl.programmierpraktikum2021.p1.a1;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    @Test
    public void testNode1() throws GraphException {
        Graph<Integer> graph = new GraphImpl<>();
        graph.addNode("hello", 1);
        graph.addNode("bye", null);
        assertEquals(1, graph.getData("hello"));
        assertThrows(InvalidNodeException.class, () -> graph.getData("hi"));
        assertThrows(InvalidNodeException.class, () -> graph.getData(null));
        assertNull(graph.getData("bye"));
    }

    @Test
    public void testNode2() throws GraphException {
        Graph<Integer> graph = new GraphImpl<>();
        graph.addNode("hello", 1);
        graph.addNode("hello", 2);
        graph.addNode("hi", 1);
        assertEquals(2, graph.getData("hello"));
        assertEquals(1, graph.getData("hi"));
    }

    @Test
    public void testNode3() throws GraphException {
        Graph<String> graph = new GraphImpl<>();
        graph.addNode("hello", "1");
        graph.setData("hello", "2");
        assertEquals("2", graph.getData("hello"));
        assertThrows(InvalidNodeException.class, () -> graph.setData("hi", "2"));
        assertThrows(InvalidNodeException.class, () -> graph.setData(null, "2"));
    }

    @Test
    public void testNode4() {
        Graph<String> graph = new GraphImpl<>();
        assertEquals(0, graph.getNodeIds().size());
        graph.addNode("node1", "1");
        graph.addNode("node2", "2");
        graph.addNode("node3", "1");
        ArrayList<String> res = new ArrayList<>(Arrays.asList("node1", "node2", "node3"));
        assertTrue(graph.getNodeIds().containsAll(res));
    }

    @Test
    public void testEdge1() throws GraphException {
        Graph<Integer> graph = new GraphImpl<>();
        graph.addNode("here", 1);
        graph.addNode("there", 2);
        graph.addEdge("here", "there");
        assertThrows(DuplicateEdgeException.class, () -> graph.addEdge("here", "there"));
        assertThrows(InvalidNodeException.class, () -> graph.addEdge(null, "here"));
        assertThrows(InvalidNodeException.class, () -> graph.addEdge("there", null));
    }

    @Test
    public void testEdge2() throws GraphException {
        Graph<Integer> graph = new GraphImpl<>();
        graph.addNode("A", 2);
        graph.addNode("B", 1);
        graph.addNode("C", 1);
        graph.addNode("D", 0);
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("A", "A");
        graph.addEdge("A", "C");
        ArrayList<String> res1 = new ArrayList<>(Arrays.asList("A", "C"));
        ArrayList<String> res2 = new ArrayList<>(Arrays.asList("A", "B"));
        assertTrue(graph.getIncomingNeighbors("A").containsAll(res1));
        assertTrue(graph.getIncomingNeighbors("C").containsAll(res2));
        assertTrue(graph.getIncomingNeighbors("B").contains("A"));
        assertTrue(graph.getIncomingNeighbors("D").isEmpty());
        assertEquals(2, graph.getIncomingNeighbors("A").size());
        assertEquals(1, graph.getIncomingNeighbors("B").size());
        assertThrows(InvalidNodeException.class, () -> graph.getIncomingNeighbors("E"));
    }

    @Test
    public void testEdge3() throws GraphException {
        Graph<Integer> graph = new GraphImpl<>();
        graph.addNode("A", 2);
        graph.addNode("B", 1);
        graph.addNode("C", 1);
        graph.addNode("D", 0);
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("A", "A");
        graph.addEdge("A", "C");
        ArrayList<String> res = new ArrayList<>(Arrays.asList("A", "B", "C"));
        assertTrue(graph.getOutgoingNeighbors("A").containsAll(res));
        assertTrue(graph.getOutgoingNeighbors("C").contains("A"));
        assertTrue(graph.getOutgoingNeighbors("D").isEmpty());
        assertEquals(3, graph.getOutgoingNeighbors("A").size());
        assertEquals(1, graph.getOutgoingNeighbors("B").size());
        assertThrows(InvalidNodeException.class, () -> graph.getOutgoingNeighbors("E"));
    }
}
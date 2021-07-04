package de.tukl.programmierpraktikum2021.p2.a1;
import de.tukl.programmierpraktikum2021.p1.a1.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphExtendedTest {
    @Test
    public void testHasCycle1() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        graph.addNode("A", 1);
        graph.addNode("B", 2);
        graph.addNode("C", 3);
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        assertTrue(graph.hasCycle());
    }

    @Test
    public void testHasCycle2() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        graph.addNode("A", 1);
        graph.addNode("B", 2);
        graph.addNode("C", 3);
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        assertFalse(graph.hasCycle());
    }

    @Test
    public void testHasCycle3() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        graph.addNode("A", 1);
        graph.addNode("B", 2);
        graph.addEdge("A", "B");
        graph.addEdge("B", "A");
        assertTrue(graph.hasCycle());
    }

    @Test
    public void testHasCycle4() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        assertFalse(graph.hasCycle());
        graph.addNode("A", 0);
        graph.addEdge("A", "A");
        assertTrue(graph.hasCycle());
    }

    @Test
    public void testIsConnected1() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        assertTrue(graph.isConnected());
        graph.addNode("A", 1);
        graph.addNode("B", 2);
        graph.addNode("C", 3);
        graph.addNode("D", 4);
        graph.addEdge("B", "C");
        graph.addEdge("C", "B");
        graph.addEdge("D", "A");
        assertFalse(graph.isConnected());
    }

    @Test
    public void testIsConnected2() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        graph.addNode("A", 1);
        assertTrue(graph.isConnected());
        graph.addEdge("A", "A");
        assertTrue(graph.isConnected());
        graph.addNode("B", 2);
        graph.addNode("C", 3);
        graph.addNode("D", 4);
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "B");
        graph.addEdge("D", "A");
        assertTrue(graph.isConnected());
    }

    @Test
    public void testBFS1() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        graph.addNode("A", 1);
        graph.addNode("B", 1);
        graph.addEdge("A" , "A");
        graph.addEdge("A" , "B");
        List<String> res1 = graph.breadthFirstSearch("A", "A");
        assertEquals(1, res1.size());
        assertTrue(res1.contains("A"));
        assertThrows(InvalidNodeException.class, () -> graph.breadthFirstSearch("A", "D"));
        assertThrows(InvalidNodeException.class, () -> graph.breadthFirstSearch("D", "A"));
        assertThrows(InvalidNodeException.class, () -> graph.breadthFirstSearch("A", null));
        assertThrows(InvalidNodeException.class, () -> graph.breadthFirstSearch(null, "A"));
        assertThrows(InvalidNodeException.class, () -> graph.breadthFirstSearch(null, null));
    }

    @Test
    public void testBFS2() throws GraphException {
        GraphExtended<Integer> graph = new GraphExtendedImpl<>();
        graph.addNode("A", 1);
        graph.addNode("B", 1);
        graph.addNode("C", 1);
        graph.addNode("D", 1);
        graph.addNode("E", 1);
        graph.addNode("F", 1);
        graph.addEdge("A" , "B");
        graph.addEdge("A" , "C");
        graph.addEdge("A" , "F");
        graph.addEdge("B" , "D");
        graph.addEdge("B" , "E");
        graph.addEdge("D" , "E");
        graph.addEdge("C" , "F");
        List<String> res1 = graph.breadthFirstSearch("A", "F");
        List<String> res2 = graph.breadthFirstSearch("A", "E");
        assertEquals(2, res1.size());
        assertEquals(3, res2.size());
        assertTrue(res1.contains("A") && res1.contains("F"));
        assertTrue(res2.contains("A") && res2.contains("B") && res2.contains("E"));
    }
}

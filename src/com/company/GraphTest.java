package com.company;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class GraphTest extends TestCase {

    @Test
    public void testAddEdge() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 0);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // nie pownien zawierac krawedzi 1 -2
        assertFalse("Czy nie zawiera krawedzi 2 - 1", g.containsEdge(2, 1));

        // dodaj krawedz 2-1
        g.addEdge(2, 1, 0);
        assertTrue("Czy zawiera krawedz 2 - 1", g.containsEdge(2, 1));

    }


    @Test
    public void testRemoveEdge() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 0);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // usun krawedz 1 - 2
        g.removeEdge(1, 2);
        assertFalse("Czy krawedz 1-2 zostala usunieta", g.containsEdge(1, 2));
    }

    @Test
    public void testAddVertex() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));


    }

    @Test
    public void testToString() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 5);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // nie pownien zawierac krawedzi 1 -2
        assertFalse("Czy nie zawiera krawedzi 2 - 1", g.containsEdge(2, 1));

//        // dodaj krawedz 2-1
//        g.addEdge(2, 1, 6);
//        assertTrue("Czy zawiera krawedz 2 - 1", g.containsEdge(2,1));

        String toString = g.toString();
        assertTrue(toString.contains(" waga: " + 5));
    }

    @Test
    public void testFordBellmanAlgorithm() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj wierzcholek o n. 3
        g.addVertex(3);

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 5);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // dodaj krawedz 1 -3
        g.addEdge(1, 3, 10);
        assertTrue("Czy zawiera krawedz 1 - 3", g.containsEdge(1, 2));

        // dodaj krawedz 2-3
        g.addEdge(2, 3, 2);

        g.fordBellmanAlgorithm(1);

        // dystans 1-2 to 5
        assertTrue("Dystans 1-2 to 5", 5 == g.getFordBellmanDistanceTo(2));

        // dystans 1-3 to 7
        assertTrue("Dystans 2-3 to 7", 7 == g.getFordBellmanDistanceTo(3));

//        System.out.println("Graf: ");
//        System.out.println(g.toString());
//
//        System.out.println("Ford-Bellman Map: ");
//        System.out.println(g.fordBellmanDistanceToString());
    }

    @Test
    public void testDijkstraAlgorithm() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj wierzcholek o n. 3
        g.addVertex(3);

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 5);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // dodaj krawedz 1 -3
        g.addEdge(1, 3, 8);
        assertTrue("Czy zawiera krawedz 1 - 3", g.containsEdge(1, 2));

        // dodaj krawedz 2-3
        g.addEdge(2, 3, 0);

//        System.out.println("Graf: ");
//        System.out.println(g.toString());
        // algorytm
        g.dijkstraAlgorithm(1);


        System.out.println("Dijkstra Map: ");
        System.out.println(g.dijkstraDistanceToString());


    }

    @Test
    public void test2FordBellmanAlgorithm() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj wierzcholek o n. 3
        g.addVertex(3);

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 5);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // dodaj krawedz 1 -3
        g.addEdge(1, 3, 2);
        assertTrue("Czy zawiera krawedz 1 - 3", g.containsEdge(1, 2));

        // dodaj krawedz 2-3
        g.addEdge(2, 3, -6);


        g.fordBellmanAlgorithm(1);


        System.out.println("Graf: ");
        System.out.println(g.toString());

        System.out.println("Ford-Bellman Map: ");
        System.out.println(g.fordBellmanDistanceToString());
    }

    @Test
    public void testJohnsonAlgorithm() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj wierzcholek o n. 3
        g.addVertex(3);

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 5);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // dodaj krawedz 1 -3
        g.addEdge(1, 3, 2);
        assertTrue("Czy zawiera krawedz 1 - 3", g.containsEdge(1, 2));

        // dodaj krawedz 2-3
        g.addEdge(2, 3, -6);

        // algorytm
        g.johnsonAlgorithm();

        System.out.println("Johnson matrix: ");
        System.out.println(g.johnsonDistanceToString());

//        // dystans 1-2 to 5
//        assertTrue("Dystans 1-2 to 5", 5 == g.getDijkstraDistanceTo(2));
//
//        // dystans 1-3 to 7
//        assertTrue("Dystans 2-3 to 7", 7 == g.getDijkstraDistanceTo(3));

    }
    @Test
    public void test2JohnsonAlgorithm() throws Exception {
        // utworz graf
        Graph g = new Graph();

        // dodaj wierzcholek o n. 1
        g.addVertex(1);
        assertTrue("Czy zawiera w. 1", g.containsVertex(1));

        // dodaj wierzcholek o n. 2
        g.addVertex(2);
        assertTrue("Czy zawiera w. 2", g.containsVertex(2));

        // dodaj wierzcholek o n. 3
        g.addVertex(3);

        // dodaj krawedz 1 - 2
        g.addEdge(1, 2, 5);
        assertTrue("Czy zawiera krawedz 1 - 2", g.containsEdge(1, 2));

        // dodaj krawedz 1 -3
        g.addEdge(1, 3, 2);
        assertTrue("Czy zawiera krawedz 1 - 3", g.containsEdge(1, 2));

        // dodaj krawedz 2-3
        g.addEdge(2, 3, -6);

        // algorytm
        g.johnsonAlgorithm();

        System.out.println("Johnson matrix: ");
        System.out.println(g.johnsonDistanceToString());
        System.out.println("Path Testing: ");
        String toPrint = g.reconstructShortestPath(1,3);
        assertTrue(toPrint != null);

        System.out.println(toPrint);

    }
}
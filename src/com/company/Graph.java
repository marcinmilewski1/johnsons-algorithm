package com.company;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import sun.reflect.generics.tree.*;

import java.util.*;


public class Graph {
    /*
  Graf zaimplementowany jako lista sasiedztwa,
  1. Integer - wezel
  2. Integer - wezel
  3. Integer - waga krawedzi wezel - wezel
   */
    private TreeMap<Integer, TreeMap<Integer, Integer>> adjacencyList;
    private int vertexCount;
    private int edgeCount;

    private static final int INF = Integer.MAX_VALUE / 2; // reprezentacja nieskonczonosci

    // wyniki algorytmow
    private TreeMap<Integer, Integer> distanceFordBellman;
    private TreeMap<Integer, Integer> predecessorFordBellman;
    private TreeMap<Integer, Integer> distanceDijkstra;
    private TreeMap<Integer, Integer> predecessorDijkstra;
    private TreeMap<Integer, TreeMap<Integer, Integer>> distanceJohnson; // macierz wag najkrotszych sciezek
    private TreeMap<Integer, Integer> d;
    List<Map.Entry<Integer, TreeMap<Integer, String>>> shortestPathToPrint = new ArrayList<Map.Entry<Integer, TreeMap<Integer, String>>>();
    public Graph() {
        this.adjacencyList = new TreeMap<Integer, TreeMap<Integer, Integer>>();
    }

    // dodaje krawedz do grafu
    public void addEdge(Integer from, Integer to, Integer weight) {
        if (!(adjacencyList.containsKey(from) && adjacencyList.containsKey(to)))
            throw new IllegalArgumentException(); // jesli brak wierzcholka from i to w grafie - wyjatek
        TreeMap<Integer, Integer> oldRegistry = adjacencyList.get(from);
        oldRegistry.put(to, weight); // dodanie polaczenia - nowy wpis
        adjacencyList.replace(from, adjacencyList.get(from), oldRegistry);
        edgeCount++;
    }

    public void removeEdge(Integer from, Integer to) {
        if (!(adjacencyList.containsKey(from) && adjacencyList.containsKey(to)))
            throw new IllegalArgumentException(); // jesli brak wierzcholkow - wyrzuc wyjatek
        TreeMap<Integer, Integer> oldRegistry = adjacencyList.get(from);
        oldRegistry.remove(to);
        adjacencyList.replace(from, adjacencyList.get(from), oldRegistry);
        edgeCount--;
    }

    // dodaje odizolowany wierzcholek o indeksie v
    public void addVertex(Integer v) {
        TreeMap<Integer, Integer> temp = new TreeMap<Integer, Integer>();
        adjacencyList.put(v, temp);
        vertexCount++;
    }

    // sprawdza czy graf zawiera dany wierzcholek
    public boolean containsVertex(Integer v) {
        return adjacencyList.containsKey(v);
    }

    // sprawdza czy graf zawiera dana krawedz
    public boolean containsEdge(Integer from, Integer to) {
        if (!adjacencyList.containsKey(from)) return false; // brak w. from w grafie - brak krawedzi
        TreeMap<Integer, Integer> vertexList = adjacencyList.get(from);

        if (!vertexList.containsKey(to)) return false;
        else return true;
    }

    public String toString() {
        String string = new String();

        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) { // dla kazdego wierzcholka w grafie
            for (Map.Entry<Integer, Integer> connectionList : node.getValue().entrySet()) {
                string += node.getKey() + " --> " + connectionList.getKey() + " waga: " + connectionList.getValue() +"\n";
            }
        }
        return string;
    }

    public void fordBellmanAlgorithm(Integer start) {
        if (!adjacencyList.containsKey(start)) throw new IllegalArgumentException(); // jesli brak wierzcholka start

        distanceFordBellman = new TreeMap<Integer, Integer>();
        predecessorFordBellman = new TreeMap<Integer, Integer>();

        //ALGORYTM
        // 1. Inicjalizacja grafu
        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) { // dla kazdego wierzcholka w grafie
            if (node.getKey().equals(start)) distanceFordBellman.put(start, 0); // zmiana wartosci - reput
            else distanceFordBellman.put(node.getKey(), INF); // inicjalizuj mape dystansu
            predecessorFordBellman.put(node.getKey(), null);  // inicjalizuj mape poprzednikow
        }

        // 2. Relax edges repeatedly
        for (int i = 1; i <= vertexCount - 1; i++) {
            for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) { // dla kazdego wierzcholka
                for (Map.Entry<Integer, Integer> connectionList : node.getValue().entrySet()) { // dla kazdej krawedzi od niego odchodzacej.
                    if (distanceFordBellman.get(node.getKey()) + connectionList.getValue() < distanceFordBellman.get(connectionList.getKey())) {  // if distance[u] + w < distance[v]:
                        int newDistance = distanceFordBellman.get(node.getKey()) + connectionList.getValue();
                        distanceFordBellman.put(connectionList.getKey(), newDistance); // distance[v] := distance[u] + w
                        predecessorFordBellman.put(connectionList.getKey(), node.getKey()); // predecessor[v] := u
                    }
                }

            }
        }
        // 3. Check for negative-weight cycle, node.getKey() = u , connectionList.getKey() = v
        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) { // dla kazdego wierzcholka
            for (Map.Entry<Integer, Integer> connectionList : node.getValue().entrySet()) { // dla kazdej krawedzi od niego odchodzacej.
                if (distanceFordBellman.get(node.getKey()) + connectionList.getValue() < distanceFordBellman.get(connectionList.getKey())) {
                    System.out.println("Graf zawiera ujemny cykl");
                }
            }
        }
    }

    public int getFordBellmanDistanceTo(int node) {
        if (!adjacencyList.containsKey(node)) throw new IllegalArgumentException(); // jesli brak wierzcholka start
        return distanceFordBellman.get(node);
    }


    public String fordBellmanDistanceToString() {
        String toReturn = new String();
        for (Map.Entry<Integer, Integer> distance : distanceFordBellman.entrySet()) {
            toReturn += "from start to " + distance.getKey() + " distance: " + distance.getValue() + " ,predecessor :" + predecessorFordBellman.get(distance.getKey()) + "\n";
        }
        return toReturn;
    }


    public void dijkstraAlgorithm(Integer start) {
        if (!adjacencyList.containsKey(start)) throw new IllegalArgumentException(); // jesli brak wierzcholka start

        distanceDijkstra = new TreeMap<Integer, Integer>();
        predecessorDijkstra = new TreeMap<Integer, Integer>();

        TreeMap<Integer, TreeMap<Integer, Integer>> copyAdjacencyList = adjacencyList;

        TreeSet<Integer> set = new TreeSet<Integer>();
        //ALGORYTM
        // 1. Inicjalizacja grafu
        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) { // dla kazdego wierzcholka w grafie
            if (node.getKey() == start) distanceDijkstra.put(start, 0); // zmiana wartosci - reput
            else distanceDijkstra.put(node.getKey(), INF); // inicjalizuj mape dystansu
            predecessorDijkstra.put(node.getKey(), null);  // inicjalizuj mape poprzednikow

            // dodawaj do tablicy
            set.add(node.getKey());
        }
        // while array is not empty:
        while (set.size() > 0) {
            int min = Integer.MAX_VALUE;
            int vertex = 0;
            for (Map.Entry<Integer, Integer> distance : distanceDijkstra.entrySet()) {
                if (distance.getValue() < min && set.contains(distance.getKey())) {
                    min = distance.getValue();
                    vertex = distance.getKey();
                }
            }
            // remove vertex from array
            set.remove(vertex);

            TreeMap<Integer, Integer> list = adjacencyList.get(vertex);

            //for each neighbor v of vertex: v - entry.getKey(), u - vertex, w(u,v) - entry.getValue()
            for (Map.Entry<Integer, Integer> entry : list.entrySet()) {
                int alt = distanceDijkstra.get(vertex) + entry.getValue();
                if (alt < distanceDijkstra.get(entry.getKey())) {
                    distanceDijkstra.put(entry.getKey(), alt);
                    predecessorDijkstra.put(entry.getKey(), vertex);
                }
            }
        }
    }

    public int getDijkstraDistanceTo(int node) {
        if (!adjacencyList.containsKey(node)) throw new IllegalArgumentException(); // jesli brak wierzcholka start
        return distanceDijkstra.get(node);
    }

    public String dijkstraDistanceToString() {
        String toReturn = new String();
        for (Map.Entry<Integer, Integer> distance : distanceDijkstra.entrySet()) {
            toReturn += " from start to " + distance.getKey() + " distance: " + distance.getValue() + " ,predecessor :" + predecessorDijkstra.get(distance.getKey()) + "\n";
        }
        return toReturn;
    }

    public void johnsonAlgorithm() {

        //init
        distanceJohnson = new TreeMap<Integer, TreeMap<Integer, Integer>>();
        shortestPathToPrint = new ArrayList<Map.Entry<Integer, TreeMap<Integer, String>>>(); // najkrotsze sciezki do wydruku
        //1. Dodaj nowy węzeł q połączony krawędziami o wagach 0 z każdym innym wierzchołkiem grafu
        int q = Integer.MAX_VALUE;
        TreeMap<Integer, Integer> temp = new TreeMap<Integer, Integer>();
        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) { // dla kazdego wierzcholka - dodaj go do mapy temp
            temp.put(node.getKey(), 0);
        }
        adjacencyList.put(q, temp);

        //2. Użyj algorytmu Bellmana-Forda startując od dodanego wierzchołka q, aby odnaleźć minimalną odległość
        // d[v] każdego wierzchołka v od q. Jeżeli został wykryty ujemny cykl, zwróć tę informację i przerwij działanie algorytmu
        fordBellmanAlgorithm(q);

        //3. W tym kroku przewagujemy graf tak, aby zlikwidować ujemne wagi krawędzi
        // nie zmieniając wartości najkrótszych ścieżek. W tym celu każdej krawędzi (u,v) o
        // wadze w(u,v) przypisz nową wagę w(u,v) + d[u] - d[v]

        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) { // dla kazdego wierzcholka
            for (Map.Entry<Integer, Integer> connectionList : node.getValue().entrySet()) { // dla listy
                int staraWage = connectionList.getValue();
               int uDystans = distanceFordBellman.get(node.getKey());
                int vDystans = distanceFordBellman.get(connectionList.getKey());
               int newWeight = connectionList.getValue() + distanceFordBellman.get(node.getKey()) - distanceFordBellman.get(connectionList.getKey());
            connectionList.setValue(connectionList.getValue() + distanceFordBellman.get(node.getKey()) - distanceFordBellman.get(connectionList.getKey()));
            }
        }
        //4. Usuń początkowo dodany węzeł q
        adjacencyList.remove(q);

        //5. Użyj algorytmu Dijkstry dla każdego wierzchołka w grafie
        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : adjacencyList.entrySet()) {// dla kazdego wierzcholka
            dijkstraAlgorithm(node.getKey());

            // wypelniaj macierz wynikowa
            d = new TreeMap<Integer, Integer>();
            for (Map.Entry<Integer, TreeMap<Integer, Integer>> i : adjacencyList.entrySet()) {
                d.put(i.getKey(), distanceDijkstra.get(i.getKey()) - distanceFordBellman.get(node.getKey()) + distanceFordBellman.get(i.getKey()));
            }

            distanceJohnson.put(node.getKey(), d);

            // odtwarzanie sciezek
            Integer start = node.getKey();
            // dla kazdego z listy distanceDijkstra aktualnego wierzcholka
            for (Integer NodeFromDistanceList : distanceDijkstra.keySet()) {
                Integer end = NodeFromDistanceList;
                Integer tmp = end;
                String string = new String();

                // jesli distance od node do end = INF, wpisz noPath - brak sciezki od start do end
                if (distanceDijkstra.get(end) == INF) {
                    TreeMap<Integer, String> toPut =  new TreeMap<Integer, String>();
                    toPut.put(end,"No Path");
                    shortestPathToPrint.add(new AbstractMap.SimpleEntry<Integer,TreeMap<Integer, String>>(node.getKey(), toPut));
                    continue; // next
                }

                // dopoki start != end
                string += end + ", ";
                while (start != end) {
                    string += predecessorDijkstra.get(end) + " ,";
                    end = predecessorDijkstra.get(end); // koncem staje sie wierzcholek poprzedzajacy koniec
                }

                string = string.substring(0, string.length() -2); // usun przecinek
                string = new StringBuffer(string).reverse().toString(); // reverse
                // wrzuc do kontenera
                TreeMap<Integer, String> toPut =  new TreeMap<Integer, String>();
                toPut.put(tmp,string);
                shortestPathToPrint.add(new AbstractMap.SimpleEntry<Integer, TreeMap<Integer, String>>(node.getKey(), toPut));
            }
        }
    }

    public String johnsonDistanceToString() {
        String toReturn = new String();
        for (Map.Entry<Integer, TreeMap<Integer, Integer>> node : distanceJohnson.entrySet()) { // dla kazdego wierzcholka
            toReturn += "From " + node.getKey() + ":\n";
            for (Map.Entry<Integer, Integer> connectionList : node.getValue().entrySet()) { // dla listy
                toReturn += " to " + connectionList.getKey() + " ,distance: ";
                if (connectionList.getValue() > (INF/10)) {
                    toReturn += " INF" + "\n";
                }
                else {
                    toReturn += connectionList.getValue() + "\n";
                }
            }
        }
        return toReturn;
    }

    public String reconstructShortestPath (Integer start, Integer end) {
        String toReturn = new String();
        if (!adjacencyList.containsKey(start) && adjacencyList.containsKey(end)) {
            return new String("Zle argumenty");
        }
        // znajdz w tablicy
        String tempString = new String();
        for (Map.Entry<Integer, TreeMap<Integer, String>> entry : shortestPathToPrint) {
            if (entry.getKey() == start && entry.getValue().containsKey(end)) {
                toReturn += start + " -> " + end + " Path: " + entry.getValue().get(end) + "\n";
            }
        }
        return toReturn;
    }
}






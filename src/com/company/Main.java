package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public enum Status {
        READ_NODES,
        READ_EDGES,
        RUNNING,
        RECONSTRUCT_PATHS,
        END
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        Status status = Status.READ_NODES;

    // wczytaj wierzcholki i krawedzie
        Scanner scanner = new Scanner(System.in);
        while (status == Status.READ_NODES) {
            int nodeNumber;
            System.out.println("Dodaj wierzcholek (wpisz x aby zakonczyc): ");
            try
            {
                nodeNumber = Integer.parseInt(scanner.nextLine());
                graph.addVertex(nodeNumber);
            }
            catch(NumberFormatException nfe)
            {
                System.out.println("Zakonczyles wprowadzanie wierzcholkow.");
                status = status.READ_EDGES;
                break;
            }
        }

        while (status == Status.READ_EDGES) {
            int from;
            int to;
            int weight;
            System.out.println("Wprowadz krawedzie: (Wpisz x aby zakonczyc)");

            try
            {
                System.out.println("Wprowadz poczatek krawedzi: ");
                from = Integer.parseInt(scanner.nextLine());
                System.out.println("Wprowadz koniec krawedzi: ");
                to = Integer.parseInt(scanner.nextLine());
                System.out.println("Wprowadz wage: ");
                weight = Integer.parseInt(scanner.nextLine());
                graph.addEdge(from,to,weight);
            }
            catch(NumberFormatException nfe)
            {
                System.out.println("Zakonczyles wprowadzanie krawedzi.");
                status = Status.RUNNING;
                break;
            }
        }

        System.out.println("Wprowadziles graf: ");
        System.out.println(graph.toString());

         // algorytm Johnsona
        try {
            if (status == Status.RUNNING) graph.johnsonAlgorithm();
            status = Status.RECONSTRUCT_PATHS;
        }
        catch (Exception e) {
            System.out.println("Wystapil blad");
            System.out.println(e.getStackTrace());
        }

        // zapisz do pliku i na ekran
        System.out.println(graph.johnsonDistanceToString());
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Johnson-output.txt");
            writer.println(graph.johnsonDistanceToString());
            writer.close();
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        }

        // odtwarzaj sciezki
        System.out.println("Odtwarzanie sciezki: (Wpisz x aby zakonczyc) ");
        while (status == Status.RECONSTRUCT_PATHS) {
            Integer start;
            Integer end;
            try {
                System.out.println("Wprowadz poczatek sciezki: ");
                start = Integer.parseInt(scanner.nextLine());
                System.out.println("Wprowadz koniec sciezki: ");
                end = Integer.parseInt(scanner.nextLine());
                System.out.print(graph.reconstructShortestPath(start, end));
            }
            catch (NumberFormatException nfe) {
                System.out.println("Zakonczyles odtwarzanie sciezki.");
                status = Status.END;
            }
        }
    }

}

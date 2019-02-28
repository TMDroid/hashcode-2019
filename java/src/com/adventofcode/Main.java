package com.adventofcode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private List<Image> images;
    private List<Slide> slides;
    private Integer[] outputs;

    public Main() {
        images = new ArrayList<>();
        slides = new ArrayList<>();
    }

    public static void main(String[] args) {
        (new Main()).run(args[0]);
    }

    public void run(String file) {
        try {
            readFile(file);
            splitImagesToSlides();
            orderSlides();
            writeFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(2);
        }

        System.out.println(images);
    }


    public void readFile(String file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File(file));
        Scanner s = new Scanner(fis);

        int numberOfImages = s.nextInt();

        for (int i = 0; i < numberOfImages; i++) {
            Character c = s.next().charAt(0);

            int numberOfTags = s.nextInt();

            Image m = new Image(i, c);
            for (int j = 0; j < numberOfTags; j++) {
                m.addTag(s.next());
            }
            images.add(m);

        }
    }

    private void splitImagesToSlides() {
        Slide waiting = new Slide();

        for (Image i : images) {
            if (i.isHorizontal()) {
                slides.add(new Slide(i));
            } else {
                if (waiting.getImages().size() == 1) {
                    waiting.addImage(i);
                    slides.add(waiting);
                    waiting = new Slide();
                } else {
                    waiting.addImage(i);
                }
            }
        }
    }

    private int minKey(int key[], boolean mstSet[], int V) {
        // Initialize min value
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < V; v++)
            if (!mstSet[v] && key[v] < min && outputs[v] < 2) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    // Function to construct and print MST for
// a graph represented using adjacency
// matrix representation
    public int[] primMST(int graph[][], int V) {
        // Array to store constructed MST
        int parent[] = new int[V];
        // Key values used to pick minimum weight edge in cut
        int key[] = new int[V];
        // To represent set of vertices not yet included in MST
        boolean mstSet[] = new boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        // Make key 0 so that this vertex is picked as first vertex.
        key[0] = 0;
        parent[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum key vertex from the
            // set of vertices not yet included in MST
            int u = minKey(key, mstSet, V);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of
            // the adjacent vertices of the picked vertex.
            // Consider only those vertices which are not
            // yet included in MST
            for (int v = 0; v < V; v++)

                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    outputs[v]++;
                    key[v] = graph[u][v];
                }
        }

        return parent;
    }

    private void orderSlides() {
        int size = slides.size();

        outputs = new Integer[size];
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            Slide s1 = slides.get(i);
            System.out.println("Matirce " + i);

            for (int j = 0; j < size; j++) {
                if (j <= i) {
                    matrix[i][j] = 0;
                }

                Slide s2 = slides.get(j);
                matrix[i][j] = -s1.calculateScore(s2);
            }
        }

        int parent[] = primMST(matrix, size);

        ArrayList<Slide> inOrdine = new ArrayList<>();
        inOrdine.add(slides.get(0));
        for (int i = 1; i < size; i++) {
            int destinatie = parent[i];
            inOrdine.add(slides.get(destinatie));
        }

        slides = inOrdine;


        /*List<Slide> best = new ArrayList<>();
        best.add(slides.get(0));
        slides.remove(0);

        while(slides.size() > 0) {
            System.out.println(slides.size());
            Slide reference = best.get(best.size() - 1);

            int scoreMax = 0;
            Slide useTHISSlide = null;
            for(Slide test : slides) {
                int testScore = reference.calculateScore(test);
                if(testScore > scoreMax) {
                    useTHISSlide = test;
                    scoreMax = testScore;
                }
            }

            best.add(useTHISSlide);
            slides.remove(slides.indexOf(useTHISSlide));
        }

        slides = best;
*/
        /*Graph graph = new Graph(size, size * size);
        for(int i = 0; i < size; i++) {
            Slide s1 = slides.get(i);

            for(int j = 0; j < size; j++) {
                Slide s2 = slides.get(j);


            }
        }


        Integer[][] matrix = new Integer[size][size];
        for(int i = 0; i < size; i++) {
            Slide s1 = slides.get(i);

            for(int j = 0; j < size; j++) {
                if(j <= i) {
                    matrix[i][j] = 0;
                }

                Slide s2 = slides.get(j);
                matrix[i][j] = -s1.calculateScore(s2);
            }
        }

        List<Slide> best = new ArrayList<>();
        best.add(slides.get(0));


        Graph*/
    }

    private void writeFile() throws FileNotFoundException {
        PrintStream ps = new PrintStream(new FileOutputStream(new File("../output.txt")));

        ps.println(slides.size());

        for (Slide s : slides) {
            ps.println(s);
        }
    }
}

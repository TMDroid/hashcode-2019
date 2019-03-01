package com.adventofcode;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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

    private void orderSlides() {

        List<List<Slide>> bestOfBests = new ArrayList<>();

        List<Slide> best = new ArrayList<>();
        best.add(slides.get(0));
        slides.remove(0);

        while (slides.size() > 0) {
            Slide reference = best.get(best.size() - 1);


            int partitions = slides.size() / 1000;
            if (partitions == 0) partitions = 1;

            System.out.println(slides.size());
            for (int z = 0; z < partitions; z++) {
                List<Slide> thousand = slides.subList(z, (z + 1) * 1000);

                int scoreMax = 0;
                Slide useTHISSlide = null;
                for (Slide test : thousand) {
                    int testScore = reference.calculateScore(test);
                    if (testScore > scoreMax) {
                        useTHISSlide = test;
                        scoreMax = testScore;
                    }
                }

                best.add(useTHISSlide);
                thousand.remove(useTHISSlide);
            }

            bestOfBests.add(best);
        }

        for (int i = 0; i < bestOfBests.size(); i++) {
            List<Slide> boss = bestOfBests.get(0);

            int max = 0;
            int bestComparare = 0;
            int bestsIndex = 0;

            for (int j = i + 1; j < bestOfBests.size(); j++) {
                List<Slide> bests2 = bestOfBests.get(j);


                Slide bossInceput = boss.get(0);
                Slide bossSfarsit = boss.get(boss.size() - 1);

                Slide bestsInceput = bests2.get(0);
                Slide bestsSfarsit = bests2.get(bests2.size() - 1);


                int s = bossInceput.calculateScore(bestsInceput);
                if (s > max) {
                    max = s;
                    bestComparare = 1;
                    bestsIndex = j;
                }

                s = bossInceput.calculateScore(bestsSfarsit);
                if (s > max) {
                    max = s;
                    bestComparare = 2;
                    bestsIndex = j;
                }

                s = bossSfarsit.calculateScore(bestsInceput);
                if (s > max) {
                    max = s;
                    bestComparare = 3;
                    bestsIndex = j;
                }

                s = bossSfarsit.calculateScore(bestsSfarsit);
                if (s > max) {
                    max = s;
                    bestComparare = 4;
                    bestsIndex = j;
                }
            }

            List<Slide> bests2 = bestOfBests.get(bestsIndex);
            switch (bestComparare) {
                case 1:
                    Collections.reverse(boss);
                    boss.addAll(bests2);
                    break;
                case 2:
                    bests2.addAll(boss);
                    bestOfBests.set(0, bests2);
                    break;
                case 3:
                    boss.addAll(bests2);
                    break;
                case 4:
                    Collections.reverse(bests2);
                    boss.addAll(bests2);
                    break;

            }
            bestOfBests.remove(bestOfBests.indexOf(bests2));
        }

        slides = bestOfBests.get(0);

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

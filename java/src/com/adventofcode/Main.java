package com.adventofcode;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private List<Image> images;
    private List<Slide> slides;

    public static void main(String[] args) {
        (new Main()).run(args[0]);
    }

    public Main() {
        images = new ArrayList<>();
        slides = new ArrayList<>();
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

        for(int i = 0; i < numberOfImages; i++) {
            Character c = s.next().charAt(0);

            int numberOfTags = s.nextInt();

            Image m = new Image(i, c);
            for(int j = 0; j < numberOfTags; j++) {
                m.addTag(s.next());
            }
            images.add(m);

        }
    }

    private void splitImagesToSlides() {
        Slide waiting = new Slide();

        for(Image i : images) {
            if(i.isHorizontal()) {
                slides.add(new Slide(i));
            } else {
                if(waiting.getImages().size() == 1) {
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
        List<Slide> verticals = new ArrayList<>();
        int size = slides.size();

        for(int i = 0; i < size; i++) {
            Slide s = slides.get(i);
            if(s.getImages().size() > 1) {
                slides.remove(slides.indexOf(s));
                verticals.add(s);
                i--;
                size--;
            }
        }

        slides.addAll(verticals);
    }

    private void writeFile() throws FileNotFoundException {
        PrintStream ps = new PrintStream(new FileOutputStream(new File("../output.txt")));

        ps.println(slides.size());

        for(Slide s : slides) {
            ps.println(s);
        }
    }
}

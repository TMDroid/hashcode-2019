package com.adventofcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Slide {
    private List<Image> images;

    public Slide() {
        images = new ArrayList<>();
    }

    public Slide(Image i) {
        this();
        this.images.add(i);
    }

    public Slide addImage(Image i) {
        this.images.add(i);

        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public int calculateScore(Slide s) {
        Set<String> common = new HashSet<>();
        Set<String> first = new HashSet<>();
        Set<String> second = new HashSet<>();

        for(Image i : images) {
            common.addAll(i.getTags());
            first.addAll(i.getTags());
        }

        for(Image i : s.images) {
            common.addAll(i.getTags());
            second.addAll(i.getTags());
        }

        return Math.min(Math.min(first.size(), second.size()), common.size());
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        for(Image i : images) {
            line.append(i.getId()).append(" ");
        }

        return line.toString();
    }
}

package com.adventofcode;

import java.util.HashSet;
import java.util.Set;

public class Image {
    private Integer id;
    private Character hv;
    private Set<String> tags;

    public Image(Integer id, Character hv) {
        this.id = id;
        this.hv = hv;
        this.tags = new HashSet<>();
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public Set<String> getTags() {
        return tags;
    }

    public Character getHv() {
        return hv;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%d %c %s", id, hv, tags.toString());
    }

    public boolean isHorizontal() {
        return this.hv == 'H';
    }
}

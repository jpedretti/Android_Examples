package com.example.jpedretti.chapter4recyclerview;

/**
 * Created by jpedretti on 05/10/2016.
 */

public class MainDataDef {
    private int image;
    private String name;
    private String info;

    public MainDataDef(int image, String name, String info){
        this.image = image;
        this.name = name;
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}

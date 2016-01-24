package com.podcentral.podcastcentral.bean;

/**
 * Created by dyego on 12/24/15.
 */
public class Podcast {
    private String name;
    private String category;

    public Podcast(String name, String category){
        super();
        this.name = name;
        this.category = category;
    }

    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }
}

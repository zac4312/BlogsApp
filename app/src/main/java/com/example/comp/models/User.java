package com.example.comp.models;

import com.google.gson.annotations.SerializedName;

public class User {

    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("parag")
    private String parag;

    @SerializedName("image")
    private String image;
    public User(){}

    public User(String title, String parag){
        this.title = title;
        this.parag = parag;
    }

    public Long getId(){
        return id;
    } public void setId(Long id) {this.id = id;}

    public String getTitle(){
        return title;
    }

    public String getParag(){
        return parag;
    }
    public String getImage(){
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParag(String parag) {
        this.parag = parag;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

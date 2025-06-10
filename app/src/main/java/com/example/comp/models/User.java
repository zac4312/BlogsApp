package com.example.comp.models;

import com.google.gson.annotations.SerializedName;

public class User {

    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("parag")
    private String parag;

    @SerializedName("memories")
    private boolean memories;

    @SerializedName("sport")
    private boolean sport;

    @SerializedName("media")
    private boolean media;

    @SerializedName("food")
    private boolean food;

    @SerializedName("image")
    private String image;
    public User(){}

    public User(String title, String parag, String image, boolean memories, boolean sport, boolean media, boolean food){
        this.title = title;
        this.parag = parag;
        this.image = image;

        this.memories = memories;
        this.sport = sport;
        this.media = media;
        this.food = food;
    }

    public Long getId(){
        return id;
    } public void setId(Long id) {this.id = id;}

    public String getTitle(){
        return title;
    } public void setTitle(String title) {this.title = title;}


    public String getParag(){
        return parag;
    } public void setParag(String parag) {this.parag = parag;}

    public String getImage(){
        return image;
    } public void setImage(String image) {this.image = image;}

    ///////////////////////////////////////////////////////////////////////////////////

    public boolean getMemories(){
        return memories;
    }public void setMemories(boolean memories) {this.memories = memories;}

    public boolean getSport(){
        return sport;
    }public void setSport(boolean sport) {this.sport = sport;}

    public boolean getMedia(){
        return media;
    }public void setMedia(boolean media) {this.media = media;}

    public boolean getFood(){
        return food;
    }public void setFood(boolean food) {this.food = food;}

}

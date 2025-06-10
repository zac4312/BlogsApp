package com.example.comp;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

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

    public UserDTO(){

    }


    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public String getParag(){return parag;}
    public void setParag (String parag){this.parag = parag;}

    public boolean isMemories(){
        return memories;
    }public void setMemories(boolean memories) {
        this.memories = memories;
    }

    public boolean isSport(){
        return sport;
    }public void setSport(boolean sport){
        this.sport = sport;
    }

    public boolean isMedia(){
        return media;
    }public void setMedia(boolean media) {;
        this.media = media;
    }

    public boolean isFood(){
        return food;
    }public void setFood(boolean food) {
        this.food = food;
    }

    }





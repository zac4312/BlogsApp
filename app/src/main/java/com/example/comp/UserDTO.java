package com.example.comp;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("title")
    private String title;
    @SerializedName("parag")
    private String parag;

    public UserDTO(){
        this.title = this.title;
        this.parag = this.parag;
    }


    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public String getParag(){return parag;}
    public void setParag (String parag){this.parag = parag;}
}




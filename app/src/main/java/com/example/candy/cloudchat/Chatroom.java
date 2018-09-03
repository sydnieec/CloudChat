package com.example.candy.cloudchat;

/**
 * Created by candy on 6/28/2018.
 */

public class Chatroom {
    private int id;
    private String name;
    private int numberofpeople;

    //Constructor
    public Chatroom (int id, String name, int numberofpeople){
        this.id=id;
        this.name=name;
        this.numberofpeople=numberofpeople;


    }
    //Setter, getter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberofpeople() {
        return numberofpeople;
    }

    public void setNumberofpeople(int numberofpeople) {
        this.numberofpeople = numberofpeople;
    }





}

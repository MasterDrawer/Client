package com.nuts.my.drawnuts.domain.game;

public class Game {

    private String name;


    private Game() {
        //Empty constructor for Firebase deserialization
    }

    public Game(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

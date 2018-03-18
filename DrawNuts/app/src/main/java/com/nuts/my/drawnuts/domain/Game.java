package com.nuts.my.drawnuts.domain;

public class Game {

  private String name;
  private String state;

  public Game(String name,String state) {
    this.name = name;
    this.state = state;
  }

  public String getName() {
    return name;
  }
}

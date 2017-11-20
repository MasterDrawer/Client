package com.nuts.my.drawnuts.app;

import com.nuts.my.drawnuts.domain.GamesRepository;

public class ObjectCreator {

  private static GamesRepository gamesRepository = new GamesRepository();

  static {
    gamesRepository = new GamesRepository();
  }

  public static GamesRepository getGamesRepository() {
    return gamesRepository;
  }
}

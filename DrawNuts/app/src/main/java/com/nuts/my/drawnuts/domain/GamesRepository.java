package com.nuts.my.drawnuts.domain;

import java.util.ArrayList;
import java.util.List;

public class GamesRepository {

  private final List<Game> games;
  private final List<Listener> listeners;

  public GamesRepository() {
    games = new ArrayList<>();
    listeners = new ArrayList<>();

    games.add(new Game("Game 1"));
    games.add(new Game("Game 2"));
    games.add(new Game("Game 3"));
  }

  public void addGame(Game game) {
    games.add(game);
    emitExistingGames(game);
  }

  public void addListener(Listener listener) {
    listeners.add(listener);
    for (Game game : games) {
      listener.onAdded(game);
    }
  }

  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }

  private void emitExistingGames(Game game) {
    for (Listener listener : listeners) {
      listener.onAdded(game);
    }
  }

  public interface Listener {
    void onAdded(Game game);
  }
}

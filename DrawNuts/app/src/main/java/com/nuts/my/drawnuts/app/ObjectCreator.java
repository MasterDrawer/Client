package com.nuts.my.drawnuts.app;

import com.nuts.my.drawnuts.domain.game.GamesRepository;

public class ObjectCreator {

    private static GamesRepository gamesRepository;

    static {
        gamesRepository = new GamesRepository();
    }

    public static GamesRepository getGamesRepository() {
        return gamesRepository;
    }
}

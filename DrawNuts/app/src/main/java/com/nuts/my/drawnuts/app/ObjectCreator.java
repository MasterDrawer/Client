package com.nuts.my.drawnuts.app;

import com.nuts.my.drawnuts.app.login.LoginService;
import com.nuts.my.drawnuts.domain.game.GamesRepository;

public class ObjectCreator {

  private static GamesRepository gamesRepository;
  private static LoginService loginService;

  static {
    gamesRepository = new GamesRepository();
    loginService = LoginService.LoginServiceCreator.create("http://myServerUrl");
  }

  public static GamesRepository getGamesRepository() {
    return gamesRepository;
  }

  public static LoginService getLoginService() {
    return loginService;
  }
}

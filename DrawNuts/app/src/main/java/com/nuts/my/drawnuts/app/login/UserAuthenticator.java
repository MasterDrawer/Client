package com.nuts.my.drawnuts.app.login;

import com.nuts.my.drawnuts.app.ObjectCreator;
import com.nuts.my.drawnuts.domain.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAuthenticator {

  private static final String allowedUsername = "barmako@gmail.com";
  private static final String allowedPassword = "12345";

  public UserAuthenticator() {
  }

  void login(String email, String password, final UserLoginCallback callback) {
    if (isLocallyAllowed(email, password)) {
      callback.onResponse(true);
    } else {
      askServer(email, password, callback);
    }
  }

  private boolean isLocallyAllowed(String email, String password) {
    return allowedUsername.equals(email) && allowedPassword.equals(password);
  }

  private void askServer(String email, String password, final UserLoginCallback callback) {
    ObjectCreator.getLoginService()
        .login(new User(email, password))
        .enqueue(new Callback<Boolean>() {
          @Override
          public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            callback.onResponse(response.body());
          }

          @Override
          public void onFailure(Call<Boolean> call, Throwable t) {
            callback.onFailure(t);
          }
        });
  }

  interface UserLoginCallback {
    void onResponse(Boolean isAuthenticated);

    void onFailure(Throwable reason);
  }
}
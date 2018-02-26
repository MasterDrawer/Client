package com.nuts.my.drawnuts.app.login;

import com.nuts.my.drawnuts.domain.user.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

  @POST("login")
  Call<Boolean> login(@Body User user);

  public static class LoginServiceCreator {
    public static LoginService create(String baseUrl) {
      Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
          .baseUrl(baseUrl)
          .build();

      return retrofit.create(LoginService.class);
    }
  }
}

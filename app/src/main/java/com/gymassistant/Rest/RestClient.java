package com.gymassistant.Rest;

import com.gymassistant.Models.ServerResponse;
import com.gymassistant.Models.User;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public class RestClient {

    private static UserInterface userInterface;
    private static String baseUrl = "http://dzym.azurewebsites.net/";

    public static UserInterface getClient() {
        if (userInterface == null) {
            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverter(String.class, new ToStringConverter())
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            userInterface = client.create(UserInterface.class);
        }
        return userInterface ;
    }

    public interface UserInterface {

        @Headers("User-Agent: GymAssistant")
        @GET("/user/getall")
        Call<User> getAllUsers();

        @POST("/authentication/register")
        Call<ServerResponse> register(@Body User user);

        @POST("/authentication/login")
        Call<ServerResponse> login(@Body User user);

        @GET("/user/getById/{id}")
        Call<User> getUser(@Path("id") int id);
    }
}
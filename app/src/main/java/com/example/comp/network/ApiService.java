package com.example.comp.network;

import com.example.comp.models.User;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.PUT;

public interface ApiService {
    @Multipart
    @POST("/user/save")
    Call<User> saveUser(
            @Part("user") RequestBody user,
            @Part MultipartBody.Part image
    );

    @GET("/user")  // Matches Spring Boot's GET endpoint
    Call<List<User>> getUsers();

    @DELETE("/user/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

    @PUT("/user/{id}")
    Call<User> updateUser(@Path("id") Long id, @Body User user);

}

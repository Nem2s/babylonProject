package com.marco.babylonproject.repository;

import com.marco.babylonproject.model.primitives.Comment;
import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.model.primitives.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Repository {
    private static Retrofit instance;
    private static final String URL = "http://jsonplaceholder.typicode.com/";

    /**
     * Create an instance of Retrofit object
     * */
    public static Retrofit getRetrofitInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(createHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    private static OkHttpClient createHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public interface postsApi {
        @GET("posts/{id}")
        Call<Post> getPostById(@Path("id") String id);
        @GET("posts/")
        Call<List<Post>> getPosts();

    }
    public interface usersApi {
        @GET("users/{id}")
        Call<User> getUserById(@Path("id")String id);
    }
    public interface commentsApi {
        @GET("comments/")
        Call<List<Comment>> getComments();
        @GET("comments/{id}")
        Call<Comment> getCommentById(@Path("id") String id);
    }
}

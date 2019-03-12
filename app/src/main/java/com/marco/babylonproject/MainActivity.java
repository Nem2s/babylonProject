package com.marco.babylonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.repository.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository.postsApi repository = Repository.getRetrofitInstance().create(Repository.postsApi.class);
        Call<Post> call = repository.getPostById("3");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(MainActivity.this, "WOW", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FUCK", Toast.LENGTH_LONG).show();
            }
        });
    }
}

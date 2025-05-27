package com.example.comp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.comp.adapter.UserAdapter;
import com.example.comp.models.User;
import com.example.comp.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class  UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button buttonViewForm;

    private UserAdapter userAdapter;

    private List<User> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonViewForm = findViewById(R.id.buttonViewForm);

        buttonViewForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);

        fetchUsers(); // Load users from API
    }

    private void fetchUsers() {
        RetrofitClient.getApiService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    userList.addAll(response.body());
                    userAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(UserListActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(UserListActivity.this, "Error fetching users", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
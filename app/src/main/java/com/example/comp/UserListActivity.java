package com.example.comp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import android.widget.Spinner;


import java.util.ArrayList;
import java.util.List;

    public class  UserListActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private Button buttonViewForm;

        private Spinner genreSpinner;

        private UserAdapter userAdapter;

        private List<User> userList = new ArrayList<>();
        private List<User> allUsers = new ArrayList<>();

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

            genreSpinner = findViewById(R.id.genreSpinner);

            String[] genres = {"All", "Memories", "Food", "Sport", "Media"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genreSpinner.setAdapter(adapter);

// Handle dropdown selection
            genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedGenre = parent.getItemAtPosition(position).toString().toLowerCase();

                    if (selectedGenre.equals("all")) {
                        userAdapter.updateUsers(allUsers); // show all
                    } else {
                        filterUsersByGenre(selectedGenre); // filter by selected genre
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
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
                        allUsers.clear();
                        allUsers.addAll(response.body());

                        userList.clear();
                        userList.addAll(allUsers);
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

        private void filterUsersByGenre(String genre) {
            List<User> filteredList = new ArrayList<>();
            for (User user : allUsers) {
                switch (genre) {
                    case "food":
                        if (user.getFood()) filteredList.add(user);
                        break;
                    case "sport":
                        if (user.getSport()) filteredList.add(user);
                        break;
                    case "media":
                        if (user.getMedia()) filteredList.add(user);
                        break;
                    case "memories":
                        if (user.getMemories()) filteredList.add(user);
                        break;
                }
            }
            userAdapter.updateUsers(filteredList);

        }


}
package com.example.comp;

import  android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.comp.models.User;
import com.example.comp.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextParag;
    private Button buttonUpdateUser;
    private Long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextParag = findViewById(R.id.editTextParag);
        buttonUpdateUser = findViewById(R.id.buttonUpdateUser);

        // Get user data from intent
        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", -1);
        editTextTitle.setText(intent.getStringExtra("userName"));
        editTextParag.setText(String.valueOf(intent.getIntExtra("userAge", 0)));

        // Handle Update
        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {updateUser();}
        });

    }

    private void updateUser() {
        if (userId == null) {
            Toast.makeText(this, "there is no such user", Toast.LENGTH_SHORT).show();
            return;
        }

        String updatedTitle = editTextTitle.getText().toString().trim();
        String updatedParag = editTextParag.getText().toString().trim();

        if (updatedTitle.isEmpty() || updatedParag.isEmpty()) {
            Toast.makeText(this, "Please enter valid name and age", Toast.LENGTH_SHORT).show();
            return;
        }

        int updatedAge = Integer.parseInt(updatedParag);
        User updatedUser = new User(updatedTitle, updatedParag);

        RetrofitClient.getApiService().updateUser(userId, updatedUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditUserActivity.this, "User updated!", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(EditUserActivity.this, Buffer.class);
                    startActivity(intent);
                    finish(); // Close activity after update
                } else {
                    Toast.makeText(EditUserActivity.this, "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("EditUserActivity", "Error updating user", t);
                Toast.makeText(EditUserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
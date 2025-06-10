package com.example.comp;

import  android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.comp.models.User;
import com.example.comp.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextParag;
    private Button buttonUpdateUser;
    private Long userId;

    private CheckBox BoxMemories, BoxFood, BoxSport, Boxmedia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextParag = findViewById(R.id.editTextParag);
        buttonUpdateUser = findViewById(R.id.buttonUpdateUser);

        BoxMemories = findViewById(R.id.memories);
        Boxmedia = findViewById(R.id.media);
        BoxSport = findViewById(R.id.sport);
        BoxFood = findViewById(R.id.food);


        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", -1);
        editTextTitle.setText(intent.getStringExtra(""));
        editTextParag.setText(intent.getStringExtra(""));

        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {updateUser();}
        });
    }
/// /////////////////////////////////////////////////////////////////////////////////////////
    private void updateUser() {
        if (userId == null || userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String updatedTitle = editTextTitle.getText().toString().trim();
        String updatedParag = editTextParag.getText().toString().trim();

        User updatedUser = new User();
        updatedUser.setTitle(updatedTitle);
        updatedUser.setParag(updatedParag);

        updatedUser.setFood(BoxFood.isChecked());
        updatedUser.setSport(BoxSport.isChecked());
        updatedUser.setMedia(Boxmedia.isChecked());
        updatedUser.setMemories(BoxMemories.isChecked());


        RetrofitClient.getApiService().updateUser(userId, updatedUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditUserActivity.this, "User updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditUserActivity.this, Buffer.class);
                    startActivity(intent);
                    finish();
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
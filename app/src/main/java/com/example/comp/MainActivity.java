package com.example.comp;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.comp.models.User;
import com.example.comp.network.RetrofitClient;
import com.google.gson.Gson;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.activity.result.ActivityResultLauncher;

public class MainActivity extends AppCompatActivity {

    private CheckBox BoxMemories, BoxFood, BoxSport, Boxmedia;

    private EditText editTextTitle, editTextParag;

    private ActivityResultLauncher<String> imagePickerLauncher;

    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextTitle = findViewById(R.id.editTextTitle);
        editTextParag = findViewById(R.id.editTextParag);

        BoxMemories = findViewById(R.id.memories);
        BoxFood = findViewById(R.id.food);
        BoxSport = findViewById(R.id.Sport);
        Boxmedia = findViewById(R.id.media);

        Button btnImg = findViewById(R.id.btnImg);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonViewUsers = findViewById(R.id.buttonViewUsers);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageUri = uri;
                    }
                }
        );

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerLauncher.launch("image/*");
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        buttonViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class FileUtils {
        public String getPath(Context context, Uri uri) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String filePath = cursor.getString(column_index);
                cursor.close();
                return filePath;
            }

            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
             }
    }

    private void saveUser()  {
        FileUtils FileUtils = new FileUtils();
        String Title = editTextTitle.getText().toString().trim();
        String Parag = editTextParag.getText().toString().trim();

        boolean memo = BoxMemories.isChecked();
        boolean media = Boxmedia.isChecked();
        boolean sport = BoxSport.isChecked();
        boolean food = BoxFood.isChecked();

        UserDTO userDTO = new UserDTO();
        userDTO.setTitle(Title);
        userDTO.setParag(Parag);

        userDTO.setMedia(media);
        userDTO.setFood(food);
        userDTO.setSport(sport);
        userDTO.setMemories(memo);

        Gson gson = new Gson();
        String json = gson.toJson(userDTO);
        Log.d("DEBUG", "JSON: " + json);

        RequestBody userPart = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        File file;
        try {
            file = uriToFile(imageUri);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to read image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RetrofitClient.getApiService().saveUser(userPart, imagePart).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Post saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File uriToFile(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File tempFile = new File(getCacheDir(), "temp_image.jpg");

        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }



}

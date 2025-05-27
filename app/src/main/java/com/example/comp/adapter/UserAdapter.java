package com.example.comp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.comp.EditUserActivity;
import com.example.comp.R;
import com.example.comp.models.User;
import com.example.comp.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import com.bumptech.glide.Glide;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.Title.setText(user.getTitle());
        holder.Parag.setText(user.getParag());

        String imageUrl = "http://10.0.2.2:8080/uploads/" + user.getImage();

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.userImage);

        // ✅ Handle Delete
        holder.buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(user.getId(), holder.getAdapterPosition());
            }
        });

        // ✅ Handle Edit (Open EditUserActivity)
        holder.buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra("userId", user.getId());
                intent.putExtra("Title", user.getTitle());
                intent.putExtra("Parag", user.getParag());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra("userId", user.getId());
                intent.putExtra("Title", user.getTitle());
                intent.putExtra("Parag", user.getParag());
                context.startActivity(intent);
            }
        });

        Log.d("DEBUG", "Title: " + user.getTitle() + ", Parag: " + user.getParag());


    }

    private void deleteUser(Long userId, int position) {
        if (userId == null) {
            Toast.makeText(context, "User ID is null", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getApiService().deleteUser(userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("UserAdapter", "Error deleting user", t);
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Parag;
        Button buttonEditUser, buttonDeleteUser;

        ImageView userImage;

        public UserViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Parag = itemView.findViewById(R.id.Parag);
            buttonEditUser = itemView.findViewById(R.id.buttonEditUser);
            buttonDeleteUser = itemView.findViewById(R.id.buttonDeleteUser);
            userImage = itemView.findViewById(R.id.userImage);

        }
    }
}
package com.folahan.unilorinscholar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.folahan.unilorinscholar.Activity.ChatActivity;
import com.folahan.unilorinscholar.Models.Constants;
import com.folahan.unilorinscholar.Models.User;
import com.folahan.unilorinscholar.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {

    Context context;
    List<User> userList;

    //Create the constructor for the recyclerview
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_container_user, parent, false
        );

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.setData(userList.get(position));
        String hisUID = userList.get(position).getUid();
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra(Constants.SENDER_ID, hisUID);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        RoundedImageView img;
        TextView username, email;


        public MyHolder(@NonNull View view) {
            super(view);

            username = view.findViewById(R.id.txtNameUser);
            email = view.findViewById(R.id.txtNameUserName);
            img = view.findViewById(R.id.imageUser);

        }
        void setData(User user) {
            String userImage = user.getImage();
            String userName = user.getUsername();
            String userEmail = user.getEmail();


            username.setText(userName);
            email.setText(userEmail);
            img.setImageBitmap(getUserImage(userImage));
        }
        
        private Bitmap getUserImage(String encodedImage) {
            byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}

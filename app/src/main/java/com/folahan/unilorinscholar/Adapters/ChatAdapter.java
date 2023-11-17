package com.folahan.unilorinscholar.Adapters;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.folahan.unilorinscholar.Models.ChatMessage;
import com.folahan.unilorinscholar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<ChatMessage> chatList;
    String imageUri;


    public ChatAdapter(Context context, List<ChatMessage> chatList, String imageUri) {
        this.context = context;
        this.chatList = chatList;
        this.imageUri = imageUri;
    }

    FirebaseUser fUser;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.row_chat_left, parent, false
            );
            return new MyHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.row_chat_right,
                    parent, false);
        }
        return new MyHolder(view);
    }


    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String timeStamp = chatList.get(position).getTimestamp();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();
        holder.timeTv.setText(dateTime);

        holder.setData(chatList.get(position));
        if (position==chatList.size()-1) {
            if (chatList.get(position).isSeen) {
                holder.isSeenTv.setText(R.string.seen);
            } else {
                holder.isSeenTv.setText(R.string.delivered);
            }
        } else {
            holder.isSeenTv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSenderId().equals(fUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else return MSG_TYPE_LEFT;
    }


    static class  SendMessageViewHolder extends RecyclerView.ViewHolder {

        private TextView mMessage, mDate, mSeen;
        public SendMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            //mMessage = itemView.findViewById(R.id.txtMessage);
            //mDate = itemView.findViewById(R.id.txtDateTime);
            mSeen = itemView.findViewById(R.id.isSeen);
        }

        void setData(ChatMessage message) {
            mMessage.setText(message.getMessage());
            mDate.setText(message.getTimestamp());

        }
    }

    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        private TextView mMessage, mDateTime;
        private RoundedImageView imageView;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.txtMessageReceived);
            mDateTime = itemView.findViewById(R.id.textDateTimeReceived);
            imageView = itemView.findViewById(R.id.imageProfileRecieved);
        }

        void setData(ChatMessage message, Bitmap receivedProfileImage) {
            mMessage.setText(message.getMessage());
            mDateTime.setText(message.getTimestamp());
            imageView.setImageBitmap(receivedProfileImage);
        }

    }

    class MyHolder extends RecyclerView.ViewHolder {
        RoundedImageView profileTv;
        TextView messageTv, timeTv, isSeenTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            profileTv = itemView.findViewById(R.id.imageProfileRecieved);
            messageTv = itemView.findViewById(R
                    .id.txtMessageReceived);
            timeTv = itemView.findViewById(R
                    .id.textDateTimeReceived);
            isSeenTv = itemView.findViewById(R.id.isSeen);
        }

        void setData(ChatMessage message1) {
            String message = message1.getMessage();


            messageTv.setText(message);
            profileTv.setImageBitmap(getUserImage(imageUri));
        }

        private Bitmap getUserImage(String encodedImage) {
            byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }

}

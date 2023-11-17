package com.folahan.unilorinscholar.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinscholar.Adapters.ChatAdapter;
import com.folahan.unilorinscholar.MainActivity;
import com.folahan.unilorinscholar.Models.ChatMessage;
import com.folahan.unilorinscholar.Models.Constants;
import com.folahan.unilorinscholar.Models.User;
import com.folahan.unilorinscholar.Notification.ApiService;
import com.folahan.unilorinscholar.Notification.Client;
import com.folahan.unilorinscholar.Notification.Data;
import com.folahan.unilorinscholar.Notification.Response;
import com.folahan.unilorinscholar.Notification.Sender;
import com.folahan.unilorinscholar.Notification.Token;
import com.folahan.unilorinscholar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;


public class ChatActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText mEdtChat;
    private TextView userName, status;
    private RoundedImageView img;
    ApiService apiService;
    boolean notify = false;

    private String hisUid, myUid;
    private FirebaseDatabase database;
    private DatabaseReference userDbRef;
    private AppCompatImageView mImageView;
    private FrameLayout layout;
    private String hisImage;
    private RecyclerView mRecyclerView;
    private DatabaseReference userRefForSeen;
    private ValueEventListener seenListener;

    List<ChatMessage> chatList;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent data = getIntent();
        hisUid = data.getStringExtra(Constants.SENDER_ID);
        mImageView = findViewById(R.id.imageBack);
        mRecyclerView = findViewById(R.id.chatRecyclerView);
        mEdtChat = findViewById(R.id.inputMessage);
        userName = findViewById(R.id.txtChatName);
        status = findViewById(R.id.txtChatStatus);
        img = findViewById(R.id.imgUser);
        layout = findViewById(R.id.layoutSend);
        firebaseAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        userDbRef = database.getReference(Constants.KEY_COLLECTION_USERS);

        hisUid = getIntent().getStringExtra(Constants.SENDER_ID);
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(ApiService.class);


        Query query = userDbRef.orderByChild(Constants.KEY_UID)
                .equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ""+ds.child(Constants.KEY_USERNAME).getValue();
                     hisImage = ""+ds.child(Constants.KEY_IMAGE).getValue();

                    userName.setText(name);
                    String online = ""+ds.child(Constants.KEY_ONLINE).getValue();

                    if (online.equals("online")) {
                        status.setText(online);
                    } else {
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(Long.parseLong(online));
                        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();
                        status.setText(String.format("Last seen at: %s", dateTime));
                    }
                    byte [] bytes = Base64.decode(hisImage, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onCancelled(@EverythingIsNonNull DatabaseError error) {

            }
        });

        mImageView.setOnClickListener(view -> onBackPressed());

        layout.setOnClickListener(view -> {
            notify=true;
            String message = mEdtChat.getText().toString();
            if (TextUtils.isEmpty(message)) {
                Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
            } else {

                sendMessage(message);

            }
        });

        readMessage();

        seenMessage();
    }

    private void sendMessage(String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.SENDER_ID, hisUid);
        hashMap.put(Constants.RECEIVER_ID, myUid);
        hashMap.put(Constants.KEY_MESSAGE, message);
        hashMap.put(Constants.KEY_TIMESTAMP, timestamp);
        hashMap.put(Constants.KEY_IS_SEEN, false);
        reference.child(Constants.KEY_COLLECTION_CHATS)
                .push().setValue(hashMap);

        mEdtChat.setText("");


        final DatabaseReference database = FirebaseDatabase.getInstance()
                .getReference(Constants.KEY_COLLECTION_USERS).child(myUid);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (notify) {
                    senNotification(hisUid, "user.getUsername()", message);

                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void senNotification(String hisUid, String username, String message) {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference(Constants.KEY_FCM_TOKEN);
        Query query = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Toast.makeText(ChatActivity.this, "error.getMessage()", Toast.LENGTH_SHORT).show();
                    Token token = ds.getValue(Token.class);
                    Data data = new Data(myUid, username+":"+message,
                            "New Message", hisUid, R.drawable.unilorin_logo_design);


                    assert token != null;
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    Toast.makeText(ChatActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seenMessage() {
        userRefForSeen = FirebaseDatabase.getInstance().
                getReference(Constants.KEY_COLLECTION_CHATS);
        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChatMessage chat = ds.getValue(ChatMessage.class);
                    assert chat != null;
                    if (chat.getReceiverId().equals(myUid) &&
                    chat.getSenderId().equals(hisUid)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put(Constants.KEY_IS_SEEN, true);
                        ds.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readMessage() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(
                Constants.KEY_COLLECTION_CHATS
        );
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChatMessage chatMessage = new ChatMessage();
                    ChatMessage chat = ds.getValue(ChatMessage.class);
                    assert chat != null;
                    if (chat.getReceiverId().equals(myUid) &&
                    chat.getSenderId().equals(hisUid) || chat.getReceiverId().equals(hisUid)
                    && chat.getSenderId().equals(myUid)) {
                        chatList.add(chat);
                    }

                    adapter = new ChatAdapter(ChatActivity.this, chatList, hisImage);
                    adapter.notifyDataSetChanged();


                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        userRefForSeen.removeEventListener(seenListener);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        checkOnlineStatus(timeStamp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
        checkOnlineStatus(Constants.KEY_ONLINE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkOnlineStatus(Constants.KEY_ONLINE);
    }

    private void checkOnlineStatus(String status) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                .child(myUid);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.KEY_ONLINE, status);
        dbRef.updateChildren(hashMap);
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            myUid = user.getUid();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void checkStatusStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            myUid = user.getUid();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
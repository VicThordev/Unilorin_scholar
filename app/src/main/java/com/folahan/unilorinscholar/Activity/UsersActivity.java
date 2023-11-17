package com.folahan.unilorinscholar.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.folahan.unilorinscholar.Adapters.UserAdapter;
import com.folahan.unilorinscholar.Models.Constants;
import com.folahan.unilorinscholar.Models.User;
import com.folahan.unilorinscholar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private TextView mTxtError;
    private ProgressBar mBar;
    private RecyclerView mRecyclerView;
    private List<User> userT;
    private UserAdapter userAdapter;
    private ImageView mImage;
    private EditText edtSearch;
    private AppCompatImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mBar = findViewById(R.id.progressBar);

        mImage = findViewById(R.id.imgSearch);
        edtSearch = findViewById(R.id.edtText);
        mRecyclerView = findViewById(R.id.usersRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        userT = new ArrayList<>();

        getAllUsers();

        mImage.setOnClickListener(view -> {
            String username = edtSearch.getText().toString();
            searchUser(username);
        });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            mBar.setVisibility(View.VISIBLE);
        } else {
            mBar.setVisibility(View.INVISIBLE);
        }
    }

    private void searchUser(String username) {
        loading(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.KEY_COLLECTION_USERS);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                userT.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    loading(false);
                    User user1 = ds.getValue(User.class);
                    assert user1 != null;
                    if (!user1.getUid().equals(user.getUid())) {

                        if (user1.getUsername().toLowerCase().contains(username.toLowerCase()) ||
                        user1.getEmail().toLowerCase().contains(username.toLowerCase())) {
                            userT.add(user1);
                        }
                    }

                    userAdapter = new UserAdapter(UsersActivity.this, userT);
                    userAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(userAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    private void getAllUsers() {
        loading(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.KEY_COLLECTION_USERS);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                userT.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    loading(false);
                    User user1 = ds.getValue(User.class);
                    assert user1 != null;
                    if (!user1.getUid().equals(user.getUid())) {
                        userT.add(user1);
                    }

                    userAdapter = new UserAdapter(UsersActivity.this, userT);
                    mRecyclerView.setAdapter(userAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
}
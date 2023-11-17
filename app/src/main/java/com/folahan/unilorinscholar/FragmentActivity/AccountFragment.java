package com.folahan.unilorinscholar.FragmentActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.folahan.unilorinscholar.Models.Constants;
import com.folahan.unilorinscholar.Models.PreferenceManager;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private TextView txtUsername, txtEmail, txtEmailAddress, txtDepartment;
    private PreferenceManager manager;
    private TextView txtName;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_design_layout, container, false);
        txtName = view.findViewById(R.id.txt_profile_username);
        RoundedImageView imageView = view.findViewById(R.id.imgProfile);
        manager = new PreferenceManager(requireActivity().getApplicationContext());
        txtUsername = view.findViewById(R.id.txt_profile_username2);
        txtEmail = view.findViewById(R.id.txt_profile_username3);
        txtEmailAddress = view.findViewById(R.id.txt_profile_username4);
        txtDepartment = view.findViewById(R.id.txt_profile_username5);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Constants.KEY_COLLECTION_USERS);

        Query query = reference.orderByChild(Constants.KEY_EMAIL).equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String surname = ""+ds.child(Constants.KEY_SURNAME).getValue();
                    String lastname = ""+ds.child(Constants.KEY_LASTNAME).getValue();
                    String username = ""+ds.child(Constants.KEY_USERNAME).getValue();
                    String faculty = ""+ds.child(Constants.KEY_FACULTY).getValue();
                    String department = ""+ds.child(Constants.KEY_DEPARTMENT).getValue();
                    String email = ""+ds.child(Constants.KEY_EMAIL).getValue();
                    String image = ""+ds.child(Constants.KEY_IMAGE).getValue();
                    manager.putString(Constants.KEY_SURNAME, surname);
                    manager.putString(Constants.KEY_LASTNAME, lastname);
                    manager.putString(Constants.KEY_USERNAME, username);
                    manager.putString(Constants.KEY_FACULTY, faculty);
                    manager.putString(Constants.KEY_DEPARTMENT, department);
                    manager.putString(Constants.KEY_EMAIL, email);
                    txtName.setText(String.format("%s", surname + " " + lastname));
                    txtUsername.setText(username);
                    txtEmail.setText(faculty);
                    txtDepartment.setText(department);
                    txtEmailAddress.setText(email);
                    byte [] bytes = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }


}


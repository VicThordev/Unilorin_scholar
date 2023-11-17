package com.folahan.unilorinscholar.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinscholar.MainActivity;
import com.folahan.unilorinscholar.Models.Constants;
import com.folahan.unilorinscholar.Models.PreferenceManager;
import com.folahan.unilorinscholar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtSurname, edtFirstName, edtUsername, edtMobile, edtEmail, edtPassword,
            edtConfirmPassword, edtFaculty, edtDepartment;
    private TextView signIn, mSignUp, txtSurname, txtFirstname, txtUsername, txtMobile, txtEmail, txtPassword,
            txtConfirmPassword, txtFaculty, txtDepartment;
    private Button btnRegister;
    private PreferenceManager manager;

    private String message1, message2, message3, message4, encodedImage, message5, message6, message,
            messageG, messageD;
    private RoundedImageView img;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.btnRegister);
        manager = new PreferenceManager(getApplicationContext());

        edtSurname = findViewById(R.id.txtSurname);
        edtFirstName = findViewById(R.id.txtFirstName);
        edtUsername = findViewById(R.id.Username);
        edtMobile = findViewById(R.id.txtMobile);
        edtEmail = findViewById(R.id.txtEmail);
        edtPassword = findViewById(R.id.txtPassword);
        edtConfirmPassword = findViewById(R.id.confirmPassword);
        edtFaculty = findViewById(R.id.faculty);
        edtDepartment = findViewById(R.id.department);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Registering...");
        RelativeLayout layout;
        layout = findViewById(R.id.rlProfile);

        img = findViewById(R.id.imgSignUp);


        txtSurname = findViewById(R.id.txtSurnameText);
        txtFirstname = findViewById(R.id.txtFirstNameText);
        txtUsername = findViewById(R.id.txtUsernameText);
        txtMobile = findViewById(R.id.txtMobileText);
        txtEmail = findViewById(R.id.txtEmailText);
        txtPassword = findViewById(R.id.txtPasswordText);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordText);
        txtFaculty = findViewById(R.id.txtFacultyText);
        txtDepartment = findViewById(R.id.txtDepartmentText);

        btnRegister.setOnClickListener(view -> {
            message1 = edtEmail.getText().toString();
            message2 = edtPassword.getText().toString();

            registerUser(message1, message2);
        });

        layout.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images
                    .Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private void registerUser(String email, String password) {
        Intent intent = new Intent(this, MainActivity.class);
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            dialog.dismiss();
            FirebaseUser user = mAuth.getCurrentUser();
            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
            manager.putBoolean(Constants.KEY_LOGGED_IN, true);
            Random random = new Random();
            int number = random.nextInt(1999999999);
            startActivity(intent);

            String e_mail = user.getEmail();
            String uid = user.getUid();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(Constants.KEY_EMAIL, e_mail);
            hashMap.put(Constants.KEY_UID, uid);
            hashMap.put(Constants.KEY_SURNAME, edtSurname.getText().toString());
            hashMap.put(Constants.KEY_LASTNAME, edtFirstName.getText().toString());
            hashMap.put(Constants.KEY_USERNAME, edtUsername.getText().toString());
            hashMap.put(Constants.KEY_FACULTY, edtFaculty.getText().toString());
            hashMap.put(Constants.KEY_ONLINE, "online");
            hashMap.put(Constants.KEY_DEPARTMENT, edtDepartment.getText().toString());
            hashMap.put(Constants.KEY_IMAGE, encodedImage);
            hashMap.put(Constants.KEY_PAID, number);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference(Constants.KEY_COLLECTION_USERS);
            reference.child(uid).setValue(hashMap);


        }).addOnFailureListener(e -> {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth/bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth,
                previewHeight, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte [] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    assert result.getData() != null;
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img.setImageBitmap(bitmap);
                        //mSignUp.setVisibility(View.GONE);
                        encodedImage = encodeImage(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

}
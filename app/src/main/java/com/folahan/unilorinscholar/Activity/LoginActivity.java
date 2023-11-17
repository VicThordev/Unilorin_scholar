package com.folahan.unilorinscholar.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinscholar.MainActivity;
import com.folahan.unilorinscholar.Models.Constants;
import com.folahan.unilorinscholar.Models.PreferenceManager;
import com.folahan.unilorinscholar.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText, passwordText;
    private TextView signIn, forgotPassword;
    private PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;
    private MaterialButton btnClick;
    private ProgressDialog bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        btnClick = findViewById(R.id.btnLogin);
        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = FirebaseAuth.getInstance();
        forgotPassword = findViewById(R.id.forgot_password);

        signIn = findViewById(R.id.signIn);
        //bar = findViewById(R.id.progressBar);
        bar = new ProgressDialog(this);


        preferenceManager = new PreferenceManager(getApplicationContext());

        if (preferenceManager.getBoolean(Constants.KEY_LOGGED_IN)) {
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnClick.setOnClickListener(view -> {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 7 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Check your input again", Toast.LENGTH_SHORT).show();
            }
            else {
                signIn(email, password);
            }

        });

        forgotPassword.setOnClickListener(task -> {
            showRecoverPasswordDialog();
        });

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        });
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        //Set Layout Linear Layout
        LinearLayout layout = new LinearLayout(this);
        EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(10);
        layout.addView(emailEt);
        layout.setPadding(10,10,10,10);
        builder.setView(layout);

        builder.setPositiveButton("Recover", (dialogInterface, i) -> {
            String email = emailEt.getText().toString();
            beginRecovery(email);
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        builder.create();
        builder.show();
    }

    private void beginRecovery(String email) {
        bar.show();
        bar.setMessage("Sending mail...");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, task -> {
            bar.dismiss();
            Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            bar.dismiss();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void signIn(String email, String password) {
        bar.show();
        bar.setMessage("Logging In...");
        Intent data = new Intent(this, MainActivity.class);
        preferenceManager.putBoolean(Constants.KEY_LOGGED_IN, true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        bar.dismiss();
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(data);
                        finish();
                    } else {
                        return;
                    }
        }).addOnFailureListener(e -> {
            bar.dismiss();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        });
    }

    private boolean checkUserStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            return false;
        } else {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
    }


}
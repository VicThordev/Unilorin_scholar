package com.folahan.unilorinscholar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;

import com.folahan.unilorinscholar.Activity.Questions.FirstSemester100l.FirstSemesterActivity;
import com.folahan.unilorinscholar.Activity.Questions.SecondSemester100l.SecondSemesterActivity;
import com.folahan.unilorinscholar.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class CheckLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_level);
        Objects.requireNonNull(getSupportActionBar()).hide();
        SwitchCompat screenOnSwitch = findViewById(R.id.screen_on_switch);
        screenOnSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

    }

    public void open(View view) {
        showButton();
    }

    protected void showButton() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.question_check_nav,
                findViewById(R.id.design_bottom_sheet));
        Button scoreShow = bottomSheet.findViewById(R.id.btn100);
        Button goHome = bottomSheet.findViewById(R.id.btn200);

        scoreShow.setOnClickListener(view -> {
            startActivity(new Intent(this, FirstSemesterActivity.class));
        });

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, SecondSemesterActivity.class));
            dialog.dismiss();
            finish();
        });
        dialog.setCancelable(true);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }
}
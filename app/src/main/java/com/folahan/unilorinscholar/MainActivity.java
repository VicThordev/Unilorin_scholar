package com.folahan.unilorinscholar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.folahan.unilorinscholar.Activity.GP_Activity;
import com.folahan.unilorinscholar.Activity.LoginActivity;
import com.folahan.unilorinscholar.Activity.UsersActivity;
import com.folahan.unilorinscholar.FragmentActivity.AccountFragment;
import com.folahan.unilorinscholar.FragmentActivity.ActivateFragment;
import com.folahan.unilorinscholar.FragmentActivity.HomeFragment;
import com.folahan.unilorinscholar.FragmentActivity.SettingsFragment;
import com.folahan.unilorinscholar.Models.Constants;
import com.folahan.unilorinscholar.Models.PreferenceManager;
import com.folahan.unilorinscholar.Notification.Token;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;

public class MainActivity extends AppCompatActivity {
    HomeFragment homeFragment = new HomeFragment();
    ActivateFragment activateFragment = new ActivateFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    AccountFragment accountFragment = new AccountFragment();
    BottomNavigationView navigationView;
    private FirebaseAuth mAuth;
    private PreferenceManager manager;
    private String mUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new PreferenceManager(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        manager = new PreferenceManager(getApplicationContext());

        SwitchCompat screenOnSwitch = findViewById(R.id.screen_on_switch);
        screenOnSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

        navigationView = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayout, homeFragment).commit();

        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true).
                            replace(R.id.frameLayout, homeFragment).commit();
                    return true;
                //
                case R.id.acct:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout, accountFragment).commit();
                    return true;
                //
                case R.id.activate:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frameLayout, activateFragment).commit();
                    return true;
                //
                case R.id.settings:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true).
                            replace(R.id.frameLayout, settingsFragment).commit();
                    return true;
            }
            return false;
        });

        checkUserStatus();

        updateToken(FirebaseInstallations.getInstance().getToken(false));

    }

    private void checkUserStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));

        } else {
            //startActivity(new Intent(this, MainActivity.class));
            mUID = user.getUid();
            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", mUID);
            editor.apply();
        }
    }



    public void updateToken(Task<InstallationTokenResult> token) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.KEY_FCM_TOKEN);
        Token mToken = new Token(token);
        ref.child(mUID).setValue(mToken);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserStatus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
        manager.putBoolean(Constants.KEY_ONLINE, true);
    }

    public void openFriendActivity(View view) {
        startActivity(new Intent(this, GP_Activity.class));
    }

    public void openCalculator(View view) {
        Intent data = new Intent(this, GP_Activity.class);
        startActivity(data);
    }
}
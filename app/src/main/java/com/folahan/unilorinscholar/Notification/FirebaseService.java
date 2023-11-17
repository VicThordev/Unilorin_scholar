package com.folahan.unilorinscholar.Notification;

import androidx.annotation.NonNull;

import com.folahan.unilorinscholar.Models.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        token = String.valueOf(FirebaseMessaging.getInstance().getToken());
        //Task<InstallationTokenResult> tokenRefresh = FirebaseInstallations.getInstance().getToken(true);
        if (user != null) {
            updateToken(token);
        }
    }

    private void updateToken(String tokenRefresh) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.KEY_FCM_TOKEN);
        Token token = new Token(tokenRefresh);
        ref.child(user.getUid()).setValue(token);
    }
}

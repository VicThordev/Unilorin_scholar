package com.folahan.unilorinscholar.Notification;

import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.InstallationTokenResult;

public class Token {

    String token;

    public Token(String token) {
        this.token = token;
    }

    public Token(Task<InstallationTokenResult> tokenRefresh) {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Token() {

    }
}

package com.folahan.unilorinscholar.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAXlmBUCw:APA91bFsqwb3iemqSw8gP76X-i3bG1pSJgVIpuole8KlnkKvnqCa6q-kW9559a5fVEaLeiWT2Mc9d_zN6nco1riyYNI2-L0Kb3nSJxip1rTmeffuIYBBvydpzAdaPgDWS5gf6Cc-0gBZ"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}

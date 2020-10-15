package ch.ost.group20.speedcamerareminder.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://speedcamera-reminder.herokuapp.com/";

    public static Retrofit getClient() {

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }



}

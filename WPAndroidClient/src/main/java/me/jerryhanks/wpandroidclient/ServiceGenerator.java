package me.jerryhanks.wpandroidclient;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by @Po10cio on 29/03/2017 for WPAndroid.
 */

public class ServiceGenerator {

    private static String BASE_URL = WPAndroid.getBaseUrl();

    private static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    //get the retrofit builder
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {

        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}

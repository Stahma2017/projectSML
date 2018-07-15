package com.example.stas.sml;


import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient ourInstance = new RetrofitClient();
    private static Api api;
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_ID_VALUE = "NYT2MPS2OZZNSXIB3D4HGFNO5UMNWD0CHQWZNAEZDQPJ5JHH";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CLIENT_SECRET_VALUE = "MZQIKOZG0YNUGRAO5YS3PDUHYHFPATNDS1OCO0FXEO2DAYLR";
    private static final String VERSION = "v";
    private static final String VERSION_VALUE = "20180323";
    private static final String BASE_URL = "https://api.foursquare.com/";

    public static RetrofitClient getInstance() {
        return ourInstance;
    }

    private RetrofitClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter(CLIENT_ID, CLIENT_ID_VALUE)
                            .addQueryParameter(CLIENT_SECRET, CLIENT_SECRET_VALUE)
                            .addQueryParameter(VERSION, VERSION_VALUE)
                            .build();

                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}

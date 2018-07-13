package com.example.stas.sml;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient ourInstance = new RetrofitClient();
    private static Api api;
    private static final String CLIENT_ID = "client_id";
    private static final String BASE_URL = "https://api.foursquare.com/";

    public static RetrofitClient getInstance() {
        return ourInstance;
    }

    private RetrofitClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();
                        HttpUrl url = originalHttpUrl.newBuilder()
                                //move to build config
                                .addQueryParameter(CLIENT_ID, "NYT2MPS2OZZNSXIB3D4HGFNO5UMNWD0CHQWZNAEZDQPJ5JHH")
                                .addQueryParameter("client_secret", "MZQIKOZG0YNUGRAO5YS3PDUHYHFPATNDS1OCO0FXEO2DAYLR")
                                .addQueryParameter("v", "20180323")
                                .build();

                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
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

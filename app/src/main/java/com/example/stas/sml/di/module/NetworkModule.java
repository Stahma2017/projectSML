package com.example.stas.sml.di.module;

import com.example.stas.sml.data.network.Api;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_ID_VALUE = "3DF5155LWX0OQSGYLJMQNCPLQ2E4MFXH3QWTJO4U0RUGVQHH";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CLIENT_SECRET_VALUE = "5TYV1AL1ZZ3IPLQAQD4GQBLBQZ4SLBEQ1H4MOUEYCJHBPUY1";
    private static final String VERSION = "v";
    private static final String VERSION_VALUE = "20180323";
    private static final String BASE_URL = "https://api.foursquare.com/";

    @Singleton
    @Provides
    @Named("Logging")
    Interceptor provideLogingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Singleton
    @Provides
    @Named("Query")
    Interceptor provideQueryInterceptor() {
        return chain -> {
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
        };
    }

    @Singleton
    @Provides
    OkHttpClient provideHttpClient(@Named("Logging") Interceptor loggingInterceptor,
                                   @Named("Query") Interceptor queryInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(queryInterceptor)
                .build();
    }

    @Singleton
    @Provides
    Api provideApi(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(Api.class);
    }
}
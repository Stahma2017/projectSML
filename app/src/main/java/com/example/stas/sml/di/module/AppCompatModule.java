package com.example.stas.sml.di.module;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppCompatModule {

    @Singleton
    @Provides
    AppCompatActivity provideAppCompatActivity(){
        return new AppCompatActivity();
    }
}

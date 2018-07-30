package com.example.stas.sml.di.module;

import android.content.Context;

import com.example.stas.sml.presentation.base.BaseErrorHandler;
import com.example.stas.sml.presentation.base.ErrorHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class ErrorHandlerModule {

    @Provides
    @NonNull
    @Singleton
    public ErrorHandler provideErrorHandler(Context context){
        return new BaseErrorHandler(context);
    }
}

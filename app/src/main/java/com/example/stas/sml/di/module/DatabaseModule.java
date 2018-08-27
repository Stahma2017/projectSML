package com.example.stas.sml.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.stas.sml.data.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class DatabaseModule {

    @Provides
    @NonNull
    @Singleton
    public AppDatabase provideAppDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                // todo erase mainthred after rxJava included
                .allowMainThreadQueries()
                .build();
    }

}

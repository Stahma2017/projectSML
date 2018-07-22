package com.example.stas.sml;
import android.app.Application;
import com.example.stas.sml.di.AppComponent;

import com.example.stas.sml.di.DaggerAppComponent;
import com.example.stas.sml.di.module.AppModule;
import com.example.stas.sml.di.module.ErrorHandlerModule;
import com.example.stas.sml.di.module.PresenterModule;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .errorHandlerModule(new ErrorHandlerModule())
                .presenterModule(new PresenterModule())
                .build();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
    public static AppComponent getComponent() {
        return component;
    }
}

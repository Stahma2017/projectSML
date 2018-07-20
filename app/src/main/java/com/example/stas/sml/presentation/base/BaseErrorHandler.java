package com.example.stas.sml.presentation.base;

import android.content.Context;
import android.util.Log;

import com.example.stas.sml.CanShowError;
import com.example.stas.sml.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import retrofit2.HttpException;

public class BaseErrorHandler implements ErrorHandler {


    public BaseErrorHandler(Context context) {
        this.context = context;
    }

    private WeakReference<CanShowError> mapsView;
    private Context context;


    @Override
    public void proceed(Throwable error) {

        Log.d("Error", error.getMessage());
        CanShowError view = mapsView.get();
        if (view == null) {
            throw new IllegalStateException("View is detached");
        }

        String message;

        if (error instanceof HttpException) {
            HttpException serverError = (HttpException) error;
            switch (serverError.code()) {
                case 400:
                    message = context.getResources().getString(R.string.bad_request_error);
                    break;
                default: message = context.getResources().getString(R.string.unknown_error);
                    break;
            }
        } else if (error instanceof IOException) {
            message = context.getResources().getString(R.string.network_error);
            } else message = context.getResources().getString(R.string.unknown_error);

        view.showError(message);
    }

    @Override
    public void attachView(CanShowError view) {
        this.mapsView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.mapsView.clear();
    }
}

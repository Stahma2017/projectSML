package com.example.stas.sml.presentation.base;

import com.example.stas.sml.CanShowError;

public interface ErrorHandler {
    void proceed(Throwable error);
    void attachView(CanShowError view);
    void detachView();
}

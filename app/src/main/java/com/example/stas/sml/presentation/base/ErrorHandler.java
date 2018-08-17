package com.example.stas.sml.presentation.base;

public interface ErrorHandler {
    void proceed(Throwable error);
    void attachView(CanShowError view);
    void detachView();
}

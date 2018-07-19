package com.example.stas.sml;

public interface ErrorHandler {
    void proceed(Throwable error);
    void attachView(CanShowError view);
    void detachView();
}

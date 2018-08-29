package com.example.stas.sml.presentation.feature.main;

import android.content.Context;
import android.content.Intent;

import com.example.stas.sml.R;
import com.example.stas.sml.presentation.base.ErrorHandler;
import java.lang.ref.WeakReference;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements ActivityContract.Presenter {

    private WeakReference<ActivityContract.ActivityView> mapsView;

    private final ActivityContract.Model model;
    private final ErrorHandler errorHandler;
    private final CompositeDisposable compositeDisposable;
    private final Context context;

    public MainActivityPresenter(ActivityContract.Model model,
                                 ErrorHandler errorHandler,
                                 CompositeDisposable compositeDisposable,
                                 Context context) {
        this.model = model;
        this.errorHandler = errorHandler;
        this.compositeDisposable = compositeDisposable;
        this.context = context;
    }

    @Override
    public void checkNetworkConnection() {
        Disposable networkConnectionDisposable = model.observeConnectionStates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                    if (!isConnected) {
                        mapsView.get().showError(context.getString(R.string.netError));
                    }
                });
        compositeDisposable.add(networkConnectionDisposable);
    }

    @Override
    public void attachView(ActivityContract.ActivityView view) {
        mapsView = new WeakReference<>(view);
        errorHandler.attachView(view);
    }

    @Override
    public void detachView() {
        mapsView.clear();
        mapsView = null;
        errorHandler.detachView();
        compositeDisposable.dispose();
    }

}
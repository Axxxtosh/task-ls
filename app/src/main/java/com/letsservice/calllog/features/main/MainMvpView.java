package com.letsservice.calllog.features.main;

import android.app.Activity;
import android.content.ContentResolver;

import java.util.List;

import com.letsservice.calllog.data.model.response.Call;
import com.letsservice.calllog.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPokemon(List<Call> callList);

    void showProgress(boolean show);

    void showError(Throwable error);

    ContentResolver getResolver();
    public Activity getViewActivity();
    public boolean checkSelfPermission();

}

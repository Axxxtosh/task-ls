package com.letsservice.calllog.features.detail;

import com.letsservice.calllog.data.model.response.Pokemon;
import com.letsservice.calllog.data.model.response.Statistic;
import com.letsservice.calllog.features.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showPokemon(Pokemon pokemon);

    void showStat(Statistic statistic);

    void showProgress(boolean show);

    void showError(Throwable error);
}

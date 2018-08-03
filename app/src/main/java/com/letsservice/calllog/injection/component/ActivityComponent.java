package com.letsservice.calllog.injection.component;

import dagger.Subcomponent;
import com.letsservice.calllog.features.detail.DetailActivity;
import com.letsservice.calllog.features.main.MainActivity;
import com.letsservice.calllog.injection.PerActivity;
import com.letsservice.calllog.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);
}

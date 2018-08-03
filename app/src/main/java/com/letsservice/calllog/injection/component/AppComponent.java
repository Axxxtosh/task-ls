package com.letsservice.calllog.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.letsservice.calllog.data.DataManager;
import com.letsservice.calllog.injection.ApplicationContext;
import com.letsservice.calllog.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();
}

package com.letsservice.calllog.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.letsservice.calllog.common.injection.module.ApplicationTestModule;
import com.letsservice.calllog.injection.component.AppComponent;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}

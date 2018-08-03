package com.letsservice.calllog.injection.component;

import dagger.Subcomponent;
import com.letsservice.calllog.injection.PerFragment;
import com.letsservice.calllog.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}

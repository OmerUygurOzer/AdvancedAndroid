package com.ceomer.tutorial.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Optional;

public abstract class BasePresenter<T> implements LifecycleOwner{

    private Optional<WeakReference<T>> viewReference;
    private LifecycleRegistry lifecycleRegistry;

    protected BasePresenter(){
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    public void setView(Object view){
        this.lifecycleRegistry.markState(Lifecycle.State.STARTED);
        this.viewReference = Optional.of(new WeakReference<T>((T)view));
    }

    public void clearView(){
        this.viewReference = Optional.empty();
    }

    public void destroy(){
        this.lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    protected Optional<WeakReference<T>> getView(){
        return viewReference;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}

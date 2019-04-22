package com.ceomer.tutorial.base;

import java.lang.ref.WeakReference;
import java.util.Optional;

public abstract class BasePresenter<T> {

    private Optional<WeakReference<T>> viewReference;

    public void setView(Object view) {
        viewReference = Optional.of(new WeakReference<>((T) view));
    }

    public void clearView(){
        viewReference = Optional.empty();
    }

    protected Optional<WeakReference<T>> getView() {
        return viewReference;
    }
}

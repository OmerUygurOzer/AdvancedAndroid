package com.ceomer.tutorial.vm;

import java.lang.ref.WeakReference;
import java.util.Optional;

public abstract class BasePresenter<T> {

    private Optional<WeakReference<T>> viewReference;

    public void setView(Object view){
       viewReference = Optional.of(new WeakReference<T>((T)view));
   };
}

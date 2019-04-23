package com.ceomer.tutorial.myui;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.ceomer.tutorial.base.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MyPresenter extends BasePresenter<MyContract.View> implements MyContract.Presenter {

    private MockModel mockModel;

    private MyPresenter(){
        this.mockModel = new MockModel();
        this.mockModel.getUuidLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> strings) {
                getView().ifPresent(new Consumer<WeakReference<MyContract.View>>() {
                    @Override
                    public void accept(WeakReference<MyContract.View> viewWeakReference) {
                        viewWeakReference.get().updateText(strings.toString());
                    }
                });
            }
        });
    }

    @Override
    public void buttonClicked() {
        this.mockModel.updateUUIDs();
    }

    public static class Factory implements Supplier<MyPresenter>{

        @Override
        public MyPresenter get() {
            return new MyPresenter();
        }
    }
}

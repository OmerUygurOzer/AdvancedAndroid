package com.ceomer.tutorial.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class PresenterProvider extends Fragment {

    private static final String PRESENTER_RETAINER_TAG;

    static {
        PRESENTER_RETAINER_TAG = "presenter_tag_" + UUID.randomUUID().toString();
    }

    public static <T extends BasePresenter> T attach(FragmentActivity fragmentActivity, Supplier<T> factory) {
        if (fragmentActivity.getSupportFragmentManager().findFragmentByTag(PRESENTER_RETAINER_TAG) == null) {
            fragmentActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(new PresenterProvider(),
                            PRESENTER_RETAINER_TAG)
                    .commitNow();
        }
        PresenterProvider presenterProvider =
                (PresenterProvider)fragmentActivity.getSupportFragmentManager().findFragmentByTag(PRESENTER_RETAINER_TAG);
        presenterProvider.setView(fragmentActivity,factory);
        return presenterProvider.getPresenter(fragmentActivity);
    }

    public static <T extends BasePresenter> T attach(Fragment fragment, Supplier<BasePresenter> factory) {
        if (fragment.getChildFragmentManager().findFragmentByTag(PRESENTER_RETAINER_TAG) == null) {
            fragment.getChildFragmentManager()
                    .beginTransaction()
                    .add(new PresenterProvider(),
                            PRESENTER_RETAINER_TAG)
                    .commitNow();
        }
        PresenterProvider presenterProvider =
                (PresenterProvider)fragment.getChildFragmentManager().findFragmentByTag(PRESENTER_RETAINER_TAG);
        presenterProvider.setView(fragment,factory);
        return presenterProvider.getPresenter(fragment);
    }


    private Map<Class<?>, BasePresenter> presenterMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.presenterMap = new HashMap<>();
    }

    private <T extends BasePresenter> void setView(Object view, Supplier<T> basePresenterSupplier) {
        if (!presenterMap.containsKey(view.getClass())) {
            presenterMap.put(view.getClass(), basePresenterSupplier.get());
        }
        presenterMap.get(view.getClass()).setView(view);
    }

    private <T extends BasePresenter> T getPresenter(Object view){
        return (T)presenterMap.get(view.getClass());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        for(Class<?> clazz : presenterMap.keySet()){
            presenterMap.get(clazz).clearView();
        }
    }
}

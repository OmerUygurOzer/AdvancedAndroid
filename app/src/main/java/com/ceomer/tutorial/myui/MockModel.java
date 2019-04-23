package com.ceomer.tutorial.myui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MockModel {

    private MutableLiveData<List<String>> uuidLiveData;

    public MockModel(){
        this.uuidLiveData = new MutableLiveData<>();
    }

    private List<String> generateIds(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
    }

    public void updateUUIDs(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                uuidLiveData.postValue(generateIds());
            }
        }).start();
    }

    public LiveData<List<String>> getUuidLiveData() {
        return uuidLiveData;
    }
}

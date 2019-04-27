package com.ceomer.tutorial.services;

import android.app.Service;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiService extends Service {

    private ExecutorService thread;
    private ApiServiceBinder serviceBinder;
    private MutableLiveData<List<String>> uuidList;

    @Override
    public void onCreate() {
        super.onCreate();
        this.thread = Executors.newFixedThreadPool(1);
        this.serviceBinder = new ApiServiceBinder();
        this.uuidList = new MutableLiveData<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    public LiveData<List<String>> getUUIDList(){
        thread.submit(new Runnable() {
            @Override
            public void run() {
                uuidList.postValue(generateUUIDs());
            }
        });
        return uuidList;
    }

    private List<String> generateUUIDs(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(UUID.randomUUID().toString(),UUID.randomUUID().toString());
    }

    public class ApiServiceBinder extends Binder {
        public ApiService getService(){
            return ApiService.this;
        }
    }

    public static class ApiServiceConnection implements ServiceConnection{

        private ApiServiceBinder binder;
        private boolean isConnected;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            this.binder = (ApiServiceBinder)service;
            this.isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            this.isConnected = false;
        }

        public ApiService getService(){
            return binder.getService();
        }
    }
}

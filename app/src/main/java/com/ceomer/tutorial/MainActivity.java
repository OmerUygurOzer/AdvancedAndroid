package com.ceomer.tutorial;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ceomer.tutorial.base.PresenterProvider;
import com.ceomer.tutorial.myui.MyContract;
import com.ceomer.tutorial.myui.MyPresenter;
import com.ceomer.tutorial.services.ApiService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyContract.View {

    TextView textView;
    Button button_startFeed;
    ApiService.ApiServiceConnection apiServiceConnection;
    //MyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiServiceConnection = new ApiService.ApiServiceConnection();
        textView = findViewById(R.id.text_view_main);
        button_startFeed = findViewById(R.id.button_main);
//        button_startFeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.buttonClicked();
//            }
//        });
        //presenter = PresenterProvider.attach(this,new MyPresenter.Factory());
        button_startFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceConnection.getService().getUUIDList().observe(MainActivity.this, new Observer<List<String>>() {
                    @Override
                    public void onChanged(@Nullable List<String> strings) {
                        updateText(strings.toString());
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,ApiService.class);
        bindService(intent,apiServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(apiServiceConnection);
    }

    @Override
    public void updateText(String text) {
        textView.setText(text);
    }
}

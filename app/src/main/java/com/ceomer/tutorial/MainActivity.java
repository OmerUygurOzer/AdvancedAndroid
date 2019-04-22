package com.ceomer.tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ceomer.tutorial.base.PresenterProvider;
import com.ceomer.tutorial.vm.MyContract;
import com.ceomer.tutorial.vm.MyPresenter;

import java.util.function.Supplier;

public class MainActivity extends AppCompatActivity implements MyContract.View {

    MyPresenter myPresenter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.main_text);
        myPresenter = PresenterProvider.attach(this, new Supplier<MyPresenter>() {
            @Override
            public MyPresenter get() {
                return null;
            }
        });
    }


    @Override
    public void updateText(String text) {

    }
}

package com.ceomer.tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ceomer.tutorial.base.PresenterProvider;
import com.ceomer.tutorial.myui.MyContract;
import com.ceomer.tutorial.myui.MyPresenter;

import java.util.function.Supplier;

public class MainActivity extends AppCompatActivity implements MyContract.View {

    TextView textView;
    Button button_startFeed;
    MyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view_main);
        button_startFeed = findViewById(R.id.button_main);
        button_startFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.buttonClicked();
            }
        });
        presenter = PresenterProvider.attach(this,new MyPresenter.Factory());
    }

    @Override
    public void updateText(String text) {
        textView.setText(text);
    }
}

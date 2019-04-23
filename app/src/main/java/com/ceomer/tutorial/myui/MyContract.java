package com.ceomer.tutorial.myui;

public final class MyContract {

    public interface View{
        void updateText(String text);
    }

    public interface Presenter{
        void buttonClicked();
    }

    private MyContract(){}
}

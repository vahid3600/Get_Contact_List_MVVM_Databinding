package com.example.user.contactslistapp;

import android.app.Application;
import android.databinding.DataBindingUtil;

import com.example.user.contactslistapp.databinding.AppDataBindingComponent;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataBindingUtil.setDefaultComponent(new AppDataBindingComponent());
    }
}

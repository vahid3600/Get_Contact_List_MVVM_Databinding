package com.example.user.contactslistapp.ui.contactlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user.contactslistapp.data.ContactRepository;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListFactory implements ViewModelProvider.Factory {

    private static final String TAG = ContactListAndroidViewModel.class.getSimpleName();
    private Context context;

    public ContactListFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ContactListViewModel(context);
    }
}

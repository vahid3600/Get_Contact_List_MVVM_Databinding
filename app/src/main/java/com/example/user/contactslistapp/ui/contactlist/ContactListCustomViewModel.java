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

import java.util.List;

public class ContactListCustomViewModel implements ViewModelProvider.Factory {

    private static final String TAG = ContactListAndroidViewModel.class.getSimpleName();

    private final MutableLiveData<String> toastString;
    private ContactRepository contactRepository;
    private Context context;

    public ContactListCustomViewModel(Context context) {
        this.context = context;
        toastString = new MutableLiveData<>();
        contactRepository = new ContactRepository(context);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

    LiveData<List<ContactDBModel>> getContactsList() {
        return contactRepository.getContactsLiveData();
    }

    MutableLiveData<String> getToastString(){
        return toastString;
    }

    void setToastString(String string){
        this.toastString.setValue(string);
    }

    void fetchContactList() {
        if (contactRepository.getContactSizeFromDB() == contactRepository.fetchContactsList().size())
            return;
        List<ContactDBModel> contactsList = contactRepository.fetchContactsList();
        Log.e(TAG, "fetchContactList: " + contactsList.size());
        contactRepository.insertContactsToDB(contactsList);
    }
}

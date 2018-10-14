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

public class ContactListViewModel extends ViewModel {

    private static final String TAG = ContactListAndroidViewModel.class.getSimpleName();

    private final MutableLiveData<String> toastString;
    private ContactRepository contactRepository;

    public ContactListViewModel(Context context) {
        toastString = new MutableLiveData<>();
        contactRepository = new ContactRepository(context);
    }

    LiveData<List<ContactDBModel>> getContactsList() {
        return contactRepository.getContactsLiveData();
    }

    MutableLiveData<String> getToastString() {
        return toastString;
    }

    void setToastString(String string){
        this.toastString.setValue(string);
    }

    void fetchAndInsertContactListIntoDB() {
//        List<ContactDBModel> contactsList = new ArrayList<>();
        contactRepository.insertContactsToDB(contactRepository.fetchContactsList());
//        for (ContactDBModel contact: contactRepository.fetchContactsList())
//        {
//            if (!contactRepository.checkContactIsInDB(contact.contactName,contact.contactPhone))
//                contactsList.add(contact);
//        }
//        Log.e(TAG, "fetchContactList: " + contactsList.size());
//        contactRepository.insertContactsToDB(contactsList);
    }
}

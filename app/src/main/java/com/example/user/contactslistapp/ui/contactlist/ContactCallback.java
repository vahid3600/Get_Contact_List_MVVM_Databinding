package com.example.user.contactslistapp.ui.contactlist;

import android.databinding.ObservableArrayList;

import com.example.user.contactslistapp.data.model.ContactModel;

public interface ContactCallback {
    void updateContactList(ObservableArrayList<ContactModel> contactList);
}

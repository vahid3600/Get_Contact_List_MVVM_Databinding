/*
 * Copyright (c) 2018 Phunware Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.user.contactslistapp.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.provider.ContactsContract;

import com.example.user.contactslistapp.BR;
import com.example.user.contactslistapp.model.ContactModel;
import com.example.user.contactslistapp.ui.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_WORK;

/**
 * Created by Gregory Rasmussen on 7/26/17.
 */
public class DataViewModel extends BaseObservable {
    private static final String TAG = "DataViewModel";
    private RecyclerViewAdapter adapter;
    private List<ContactModel> data;
    private Context context;

    public DataViewModel(Context context) {
        data = new ArrayList<>();
        adapter = new RecyclerViewAdapter();
        this.context = context;
    }

    public void setUp() {
        // perform set up tasks, such as adding listeners, data population, etc.

        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            ContactModel contactModel = new ContactModel();
            contactModel.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contactModel.setAvatarUri(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));
            String contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phones.moveToNext()) {
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                switch (type) {
                    case TYPE_HOME:
                        contactModel.setPhoneNumber(number + "\n");
                        break;
                    case TYPE_MOBILE:
                        contactModel.setPhoneNumber(number + "\n");
                        break;
                    case TYPE_WORK:
                        contactModel.setPhoneNumber(number + "\n");
                        break;
                }
            }
            phones.close();
            data.add(contactModel);
        }

        cursor.close();
        populateData();
    }

    public void tearDown() {
        // perform tear down tasks, such as removing listeners
        data.clear();
        notifyPropertyChanged(BR.data);
    }

    @Bindable
    public List<ContactModel> getData() {
        return this.data;
    }

    @Bindable
    public RecyclerViewAdapter getAdapter() {
        return this.adapter;
    }

    private void populateData() {
        // populate the data from the source, such as the database.
        notifyPropertyChanged(BR.data);
    }
}

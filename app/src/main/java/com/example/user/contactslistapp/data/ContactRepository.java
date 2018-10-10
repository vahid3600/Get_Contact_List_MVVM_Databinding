package com.example.user.contactslistapp.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
import static android.provider.ContactsContract.CommonDataKinds.Website.TYPE_HOME;
import static android.provider.ContactsContract.CommonDataKinds.Website.TYPE_WORK;

public class ContactRepository {

    public List<ContactDBModel> fetchContactsList(Context context) {
        List<ContactDBModel> contactsList = new ArrayList<>();
        Cursor cursor = context.getContentResolver()
                .query(ContactsContract.Contacts.CONTENT_URI,
                        null,
                        null,
                        null,
                        ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String contactName = cursor.
                    getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String contactAvatar = cursor.
                    getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            String contactPhone = "";
            String contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phones = context.getContentResolver().
                    query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null);
            while (phones.moveToNext()) {
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                switch (type) {
                    case TYPE_HOME:
                        contactPhone = number + "\n";
                        break;
                    case TYPE_MOBILE:
                        contactPhone = number + "\n";
                        break;
                    case TYPE_WORK:
                        contactPhone = number;
                        break;
                }
            }
            phones.close();
            ContactDBModel contactDBModel = new ContactDBModel(contactName, contactPhone, contactAvatar);
            contactsList.add(contactDBModel);
        }

        cursor.close();
        return contactsList;
    }
}

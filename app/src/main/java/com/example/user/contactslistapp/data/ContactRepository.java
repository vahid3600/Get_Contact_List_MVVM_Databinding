package com.example.user.contactslistapp.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;
import com.example.user.contactslistapp.data.sourse.local.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
import static android.provider.ContactsContract.CommonDataKinds.Website.TYPE_HOME;
import static android.provider.ContactsContract.CommonDataKinds.Website.TYPE_WORK;

public class ContactRepository {

    private AppDatabase appDatabase;
    private Context context;

    public ContactRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getDatabase(context);
    }

    public List<ContactDBModel> fetchContactsList() {
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

    public void insertContactsToDB(List<ContactDBModel> contactsDBList) {
        List<ContactDBModel> contactsList = new ArrayList<>();
        for (ContactDBModel contact: contactsDBList)
        {
            if (!checkContactIsInDB(contact.contactName,contact.contactPhone))
                contactsList.add(contact);
        }
        new addAsyncTask(appDatabase).execute(contactsList);
    }

    public void deleteContactsFromDB(List<ContactDBModel> contactsList) {
        new deleteAsyncTask(appDatabase).execute(contactsList);
    }

    public boolean checkContactIsInDB(String contactName, String contactPhone) {
        for (int id : appDatabase.contactModelDao().checkContactName(contactName))
            if (appDatabase.contactModelDao().getContactPhone(id).equals(contactPhone))
                return true;
        return false;
    }

    public LiveData<List<ContactDBModel>> getContactsLiveData() {
        return appDatabase.contactModelDao().getAllContacts();
    }

    private static class addAsyncTask extends AsyncTask<List<ContactDBModel>, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(final List<ContactDBModel>... params) {
            for (List<ContactDBModel> param : params) {
                db.contactModelDao().addContact(param);
            }
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<List<ContactDBModel>, Void, Void> {

        private AppDatabase appDatabase;
        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(final List<ContactDBModel>... params) {
            for (List<ContactDBModel> param : params) {
                db.contactModelDao().deleteContact(param);
            }
            return null;
        }
    }
}

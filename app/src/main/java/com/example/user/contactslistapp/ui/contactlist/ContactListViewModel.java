package com.example.user.contactslistapp.ui.contactlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.contactslistapp.data.ContactRepository;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;
import com.example.user.contactslistapp.data.sourse.local.AppDatabase;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {

    private static final String TAG = ContactListViewModel.class.getSimpleName();
    private final LiveData<List<ContactDBModel>> contactsList;
    ContactRepository contactRepository;

    private AppDatabase appDatabase;

    public ContactListViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        contactsList = appDatabase.contactModelDao().getAllContacts();
        contactRepository = new ContactRepository();
    }

    public LiveData<List<ContactDBModel>> getContactsList() {
        return contactsList;
    }

    public void deleteContactsList(List<ContactDBModel> contactsList) {

        new deleteAsyncTask(appDatabase).execute(contactsList);
    }

    public void fetchContactList() {
        List<ContactDBModel> contactsList = contactRepository.fetchContactsList(getApplication().getApplicationContext());
        Log.e(TAG, "fetchContactList: " + contactsList.size());
        new addAsyncTask(appDatabase).execute(contactsList);

    }

    private static class addAsyncTask extends AsyncTask<List<ContactDBModel>, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final List<ContactDBModel>... params) {
            for (int i = 0; i < params.length; i++) {
                db.contactModelDao().addContact(params[i]);
            }
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<List<ContactDBModel>, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final List<ContactDBModel>... params) {
            for (int i = 0; i < params.length; i++) {
                db.contactModelDao().deleteContact(params[i]);
            }
            return null;
        }
    }
}

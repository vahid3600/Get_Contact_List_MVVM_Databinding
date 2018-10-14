package com.example.user.contactslistapp.ui.contactlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.contactslistapp.data.ContactRepository;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;
import com.example.user.contactslistapp.data.sourse.local.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactListAndroidViewModel extends AndroidViewModel {

    private final MutableLiveData<String> toastString;
    private static final String TAG = ContactListAndroidViewModel.class.getSimpleName();
    private final LiveData<List<ContactDBModel>> contactsList;
    private ContactRepository contactRepository;

    private AppDatabase appDatabase;

    public ContactListAndroidViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        contactsList = appDatabase.contactModelDao().getAllContacts();
        toastString = new MutableLiveData<>();
        contactRepository = new ContactRepository(getApplication().getApplicationContext());
    }

    LiveData<List<ContactDBModel>> getContactsList() {
        return contactsList;
    }

    public void deleteContactsList(List<ContactDBModel> contactsList) {

        new deleteAsyncTask(appDatabase).execute(contactsList);
    }

    void fetchContactList() {
        List<ContactDBModel> contactsList = new ArrayList<>();
        for (ContactDBModel contact: contactRepository.fetchContactsList())
        {
            if (!contactRepository.checkContactIsInDB(contact.contactName,contact.contactPhone))
                contactsList.add(contact);
        }
        Log.e(TAG, "fetchContactList: " + contactsList.size());
        contactRepository.insertContactsToDB(contactsList);
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

    MutableLiveData<String> getToastString() {
        return toastString;
    }

    void setToastString(String string) {
        this.toastString.setValue(string);
    }

    private static class deleteAsyncTask extends AsyncTask<List<ContactDBModel>, Void, Void> {

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

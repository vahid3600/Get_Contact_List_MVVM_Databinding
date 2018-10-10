package com.example.user.contactslistapp.ui.contactlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user.contactslistapp.data.ContactRepository;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;
import com.example.user.contactslistapp.data.sourse.local.AppDatabase;

import java.util.List;

public class ContactListCustomViewModel implements ViewModelProvider.Factory {

    private static final String TAG = ContactListViewModel.class.getSimpleName();
    private final LiveData<List<ContactDBModel>> contactsList;
    private final MutableLiveData<String> toastString;
    private ContactRepository contactRepository;
    private AppDatabase appDatabase;
    private Context context;

    public ContactListCustomViewModel(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getDatabase(context);
        contactsList = appDatabase.contactModelDao().getAllContacts();
        toastString = new MutableLiveData<>();
        contactRepository = new ContactRepository();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

    LiveData<List<ContactDBModel>> getContactsList() {
        return contactsList;
    }

    MutableLiveData<String> getToastString(){
        return toastString;
    }

    void setToastString(String string){
        this.toastString.setValue(string);
    }

    void fetchContactList() {
        List<ContactDBModel> contactsList = contactRepository.fetchContactsList(context);
        Log.e(TAG, "fetchContactList: " + contactsList.size());
        new addAsyncTask(appDatabase).execute(contactsList);

    }

    public void deleteContactsList(List<ContactDBModel> contactsList) {

        new deleteAsyncTask(appDatabase).execute(contactsList);
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

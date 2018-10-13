package com.example.user.contactslistapp.data.sourse.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;

import java.util.List;

@Dao
public interface ContactModelDao {

    @Query("select * from ContactDBModel")
    LiveData<List<ContactDBModel>> getAllContacts();

    @Query("select count(*) from ContactDBModel")
    int getContactsSizeDB();

    @Insert
    void addContact(List<ContactDBModel> contactDBModel);

    @Delete
    void deleteContact(List<ContactDBModel> contactDBModel);

}

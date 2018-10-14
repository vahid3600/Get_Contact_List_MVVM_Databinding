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

    @Query("select id from ContactDBModel where contactName = :contactName")
    List<Integer> checkContactName(String contactName);

    @Query("select contactPhone from ContactDBModel where id = :id")
    String getContactPhone(int id);

//    @Query("select count(*) from ContactDBModel where contactPhone = :contactPhone")
//    int checkContactPhone(String contactPhone);

    @Insert
    void addContact(List<ContactDBModel> contactDBModel);

    @Delete
    void deleteContact(List<ContactDBModel> contactDBModel);

}

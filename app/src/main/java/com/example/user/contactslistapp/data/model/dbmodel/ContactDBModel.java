package com.example.user.contactslistapp.data.model.dbmodel;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ContactDBModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String contactName;
    public String contactPhone;
    public String contactAvatarUri;

    public ContactDBModel(String contactName, String contactPhone, String contactAvatarUri) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactAvatarUri = contactAvatarUri;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactAvatarUri() {
        return contactAvatarUri;
    }
}

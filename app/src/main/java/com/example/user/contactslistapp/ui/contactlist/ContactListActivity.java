package com.example.user.contactslistapp.ui.contactlist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.user.contactslistapp.R;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    private ContactListAdapter contactListAdapter;
    private ContactListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewModel = ViewModelProviders.of(this).get(ContactListViewModel.class);
        viewModel.getContactsList().observe(ContactListActivity.this, new Observer<List<ContactDBModel>>() {
            @Override
            public void onChanged(@Nullable List<ContactDBModel> contactDBModels) {
                contactListAdapter.addItems(contactDBModels);
                Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
            }
        });
        initRecyclerView();
        fetchContactList();
    }

    private void fetchContactList() {
        viewModel.fetchContactList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.contacts_recycler_view);
        contactListAdapter = new ContactListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactListAdapter);
    }

}

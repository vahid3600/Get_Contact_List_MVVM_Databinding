package com.example.user.contactslistapp.ui.contactlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.user.contactslistapp.R;

public class ContactListActivity extends AppCompatActivity {

    public static boolean isInGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button goNextLayout = findViewById(R.id.go_to_next_layout);
        final ContractListFragment listFragment = ContractListFragment.newInstance();
        final ContractGridListFragment gridListFragment = ContractGridListFragment.newInstance();
        setSupportActionBar(toolbar);
        replaceFragment(listFragment);
        goNextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInGridLayout)
                    replaceFragment(listFragment);
                else replaceFragment(gridListFragment);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }
}

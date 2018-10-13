package com.example.user.contactslistapp.ui.contactlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.contactslistapp.R;

public class ContactListActivity extends AppCompatActivity {

    private static final String TAG = ContactListActivity.class.getSimpleName();
    public static boolean isInGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button goNextLayout = findViewById(R.id.go_to_next_layout);
        final ContractListFragment listFragment = ContractListFragment.newInstance();
        final ContractGridListFragment gridListFragment = ContractGridListFragment.newInstance();
        final Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(ContractListFragment.TAG);
        final Fragment fragment2 = getSupportFragmentManager().findFragmentByTag(ContractGridListFragment.TAG);

        setSupportActionBar(toolbar);
        replaceFragment(listFragment, ContractListFragment.TAG);
        goNextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onCreate: " + fragment1 + " " + fragment2);
                if (isInGridLayout)
                    replaceFragment(listFragment, ContractListFragment.TAG);
                else replaceFragment(gridListFragment, ContractGridListFragment.TAG);
            }
        });
    }

    private void replaceFragment(Fragment fragment, String TAG) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment, TAG)
                .commit();
    }
}

package com.example.user.contactslistapp.ui.contactlist;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.contactslistapp.R;

public class ContactListActivity extends AppCompatActivity {

    private ContractListFragment listFragment;
    private ContractGridListFragment gridListFragment;
    private static final String TAG = ContactListActivity.class.getSimpleName();
    public static boolean isInGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Log.e(TAG, "onCreate: " + isInGridLayout);
//        if (savedInstanceState != null) {
//            isInGridLayout = savedInstanceState.getBoolean(TAG);
//            Log.e(TAG, "onCreate: " + isInGridLayout);
//        }
        Button goNextLayout = findViewById(R.id.go_to_next_layout);
        listFragment = ContractListFragment.newInstance();
        gridListFragment = ContractGridListFragment.newInstance();

        setFragmentState();

        setSupportActionBar(toolbar);
        goNextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentState();
            }
        });
    }

    private void setFragmentState() {
        Log.e(TAG, "setFragmentState: "+isInGridLayout );
        if (isInGridLayout)
            replaceFragment(gridListFragment, ContractGridListFragment.TAG);
        else replaceFragment(listFragment, ContractListFragment.TAG);
    }

    private void changeFragmentState() {
        if (isInGridLayout)
            replaceFragment(listFragment, ContractListFragment.TAG);
        else replaceFragment(gridListFragment, ContractGridListFragment.TAG);
    }

    private void replaceFragment(Fragment fragment, String TAG) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment, TAG)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(TAG, isInGridLayout);
    }
}

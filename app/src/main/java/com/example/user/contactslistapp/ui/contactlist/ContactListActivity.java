package com.example.user.contactslistapp.ui.contactlist;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button goNextLayout = findViewById(R.id.go_to_next_layout);

        listFragment = ContractListFragment.newInstance();
        gridListFragment = ContractGridListFragment.newInstance();

        if (savedInstanceState != null) {
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, TAG);
            if (fragment instanceof ContractListFragment)
                replaceFragment(fragment, ContractListFragment.TAG);
            if (fragment instanceof ContractGridListFragment)
                replaceFragment(fragment, ContractGridListFragment.TAG);
        } else replaceFragment(listFragment, ContractListFragment.TAG);

        setSupportActionBar(toolbar);
        goNextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentState();
            }
        });
    }

    private void changeFragmentState() {
        if (getSupportFragmentManager().findFragmentByTag(ContractListFragment.TAG) == null)
            replaceFragment(listFragment, ContractListFragment.TAG);
        else if (getSupportFragmentManager().findFragmentByTag(ContractGridListFragment.TAG) == null)
            replaceFragment(gridListFragment, ContractGridListFragment.TAG);
    }

    private void replaceFragment(Fragment fragment, String TAG) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment, TAG)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (listFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, TAG, listFragment);
        if (gridListFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, TAG, gridListFragment);
    }
}

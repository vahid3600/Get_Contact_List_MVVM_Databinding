package com.example.user.contactslistapp.ui.contactlist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.View;

import com.example.user.contactslistapp.R;
import com.example.user.contactslistapp.databinding.ActivityMainBinding;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {

    private static final int READ_CONTACTS_PERMISSION_CODE = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    private DataViewModel dataViewModel;
    private MainRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: " );
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dataViewModel = new DataViewModel(getApplicationContext());
        binding.setViewModel(dataViewModel);

        initRecyclerView();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: " );
        super.onResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            dataViewModel.setUp();
        else {
            askPermissionForReadContacts();
        }
    }

    private void askPermissionForReadContacts() {
        Log.e(TAG, "askPermissionForReadContacts: " );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS
                    , Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult: " );
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_CONTACTS_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "onRequestPermissionsResult: " + "granted");
                    dataViewModel.setUp();

                } else {
                    Log.e(TAG, "onRequestPermissionsResult: " + "denied");
                }
        }
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause: ");
        super.onPause();
        dataViewModel.tearDown();
    }

    private void initRecyclerView() {
        Log.e(TAG, "initRecyclerView: " );
        binding.contactsRecyclerView.addItemDecoration(
                new DividerItemDecoration(binding.contactsRecyclerView.getContext(), VERTICAL));
        adapter = new MainRecyclerViewAdapter();

    }

    private View bind() {
        Log.e(TAG, "bind: " );

        return binding.getRoot();
    }
}

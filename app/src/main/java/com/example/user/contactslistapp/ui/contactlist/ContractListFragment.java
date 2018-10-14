package com.example.user.contactslistapp.ui.contactlist;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.contactslistapp.R;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;

import java.util.List;

public class ContractListFragment extends Fragment {

    public static final String TAG = ContractListFragment.class.getSimpleName();
    private static final int READ_CONTACTS_PERMISSION_CODE = 1;
    RecyclerView recyclerView;
    private ContactListAdapter contactListAdapter;
    private ContactListViewModel viewModel;

    public ContractListFragment() {
        // Required empty public constructor
    }

    public static ContractListFragment newInstance() {
        Log.e(TAG, "newInstance: ");
        Bundle args = new Bundle();
        ContractListFragment fragment = new ContractListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_contract_list, container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.contacts_recycler_view);
        initRecyclerView();
        viewModel = ViewModelProviders.of(getActivity(), new ContactListFactory(getActivity())).get(ContactListViewModel.class);
        viewModel.setToastString("This is a live string");

        viewModel.getString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getContactsList().observe(this, new Observer<List<ContactDBModel>>() {
            @Override
            public void onChanged(@Nullable List<ContactDBModel> contactDBModels) {
                contactListAdapter.addItems(contactDBModels);
            }
        });

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            viewModel.fetchAndInsertContactListIntoDB();
        else {
            askPermissionForReadContacts();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    private void initRecyclerView() {
        contactListAdapter = new ContactListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(contactListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    private void askPermissionForReadContacts() {
        Log.e(TAG, "askPermissionForReadContacts: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS
                    , Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_CONTACTS_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "onRequestPermissionsResult: " + "granted");
                    viewModel.fetchAndInsertContactListIntoDB();
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_access_cotacts), Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}

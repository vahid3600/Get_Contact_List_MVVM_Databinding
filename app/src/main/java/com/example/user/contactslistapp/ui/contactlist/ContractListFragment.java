package com.example.user.contactslistapp.ui.contactlist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    RecyclerView recyclerView;
    private ContactListAdapter contactListAdapter;
    private ContactListAndroidViewModel viewModel1;
    private ContactListCustomViewModel viewModel2;
    private ContactListViewModel viewModel3;

    public ContractListFragment() {
        // Required empty public constructor
    }

    public static ContractListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ContractListFragment fragment = new ContractListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: " );
        return inflater.inflate(R.layout.fragment_contract_list, container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: " );
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.contacts_recycler_view);
        initRecyclerView();
//        viewModel1 = ViewModelProviders.of(this).get(ContactListAndroidViewModel.class);
        viewModel2 = new ContactListCustomViewModel(getActivity());
        viewModel2.setToastString("This is a live string");

        viewModel2.getToastString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        viewModel2.getContactsList().observe(this, new Observer<List<ContactDBModel>>() {
            @Override
            public void onChanged(@Nullable List<ContactDBModel> contactDBModels) {
                contactListAdapter.addItems(contactDBModels);
            }
        });
        viewModel2.fetchContactList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
        }

    private void initRecyclerView() {
        contactListAdapter = new ContactListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(contactListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
        ContactListActivity.isInGridLayout = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }
}

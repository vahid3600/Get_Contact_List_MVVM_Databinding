package com.example.user.contactslistapp.ui.contactlist;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.contactslistapp.R;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;

import java.util.List;

public class ContractListFragment extends Fragment {

    RecyclerView recyclerView;
    private ContactListAdapter contactListAdapter;
    private ContactListCustomViewModel viewModel2;

    public ContractListFragment() {
        // Required empty public constructor
    }

    public static ContractListFragment newInstance() {
        ContractListFragment fragment = new ContractListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.contacts_recycler_view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel2 = new ContactListCustomViewModel(getContext());
        viewModel2.setToastString("This is a live string");

        viewModel2.getToastString().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        viewModel2.getContactsList().observe(getActivity(), new Observer<List<ContactDBModel>>() {
            @Override
            public void onChanged(@Nullable List<ContactDBModel> contactDBModels) {
                contactListAdapter.addItems(contactDBModels);
            }
        });
        initRecyclerView();
        }

    private void initRecyclerView() {
        contactListAdapter = new ContactListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(contactListAdapter);
    }
}

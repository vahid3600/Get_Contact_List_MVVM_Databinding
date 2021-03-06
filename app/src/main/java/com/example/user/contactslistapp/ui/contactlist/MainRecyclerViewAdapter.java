package com.example.user.contactslistapp.ui.contactlist;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.user.contactslistapp.R;
import com.example.user.contactslistapp.databinding.ItemContactBinding;
import com.example.user.contactslistapp.data.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MainRecyclerViewAdapter.class.getSimpleName();
    private List<ContactModel> data;

    public MainRecyclerViewAdapter() {
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_contact,
                new FrameLayout(viewGroup.getContext()),
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ContactModel contactModel = data.get(position);
        viewHolder.setViewModel(new DataItemViewModel(contactModel));
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemContactBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            Log.e(TAG, "bind: " );
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            Log.e(TAG, "unbind: " );
            if (binding != null) {
                binding.unbind();
            }
        }

        void setViewModel(DataItemViewModel viewModel) {
            Log.e(TAG, "setViewModel: " );
            if (binding != null) {
                binding.setViewModel(viewModel);
            }
        }
    }

    public void updateData(@Nullable List<ContactModel> data) {
        Log.e(TAG, "updateData: " );
        if (data == null || data.isEmpty()) {
            this.data.clear();
        } else {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
}

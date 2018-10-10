package com.example.user.contactslistapp.ui.contactlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.contactslistapp.R;
import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<ContactDBModel> contactsList;

    ContactListAdapter() {
        contactsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_contact, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.onBind(contactsList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    void addItems(List<ContactDBModel> contactsList) {
        this.contactsList = contactsList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView phoneTextView;
        private ImageView avatarImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.contact_name);
            phoneTextView = itemView.findViewById(R.id.contact_phone);
            avatarImageView = itemView.findViewById(R.id.contact_avatar);
        }

        void onBind(ContactDBModel contactDBModel) {
            nameTextView.setText(contactDBModel.getContactName());
            phoneTextView.setText(contactDBModel.getContactPhone());
            Glide.with(itemView.getContext()).load(contactDBModel.getContactAvatarUri()).into(avatarImageView);
        }
    }
}

package com.dehaat.dehaatassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.fragment.AuthorListFragment;
import com.dehaat.dehaatassignment.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private Context mContext;
    private List<Author> data;
    private AuthorListFragment.AuthorClickListener mListener;

    public AuthorAdapter(Context context, List<Author> authorList, AuthorListFragment.AuthorClickListener listener) {
        this.mContext = context;
        this.data = authorList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        AuthorViewHolder vh = new AuthorViewHolder(inflater.inflate(R.layout.view_author, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        holder.fillView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class AuthorViewHolder extends RecyclerView.ViewHolder {

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void fillView(Author author) {

        }
    }
}

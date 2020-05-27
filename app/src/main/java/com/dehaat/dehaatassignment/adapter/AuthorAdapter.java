package com.dehaat.dehaatassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public AuthorAdapter(Context context, AuthorListFragment.AuthorClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new AuthorViewHolder(inflater.inflate(R.layout.view_author, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        holder.fillView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void setData(List<Author> authorList) {
        this.data = authorList;
        notifyDataSetChanged();
    }

    class AuthorViewHolder extends RecyclerView.ViewHolder {

        private AuthorListFragment.AuthorClickListener mListener;
        private TextView authorName;
        private TextView authorBio;
        private String name;

        AuthorViewHolder(@NonNull View itemView, AuthorListFragment.AuthorClickListener listener) {
            super(itemView);
            this.mListener = listener;
            authorName = itemView.findViewById(R.id.name);
            authorBio = itemView.findViewById(R.id.bio);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null && name != null) {
                        mListener.onClickAuthor(name);
                    }
                }
            });
        }

        void fillView(Author author) {
            if (author == null) return;
            this.name = author.getAuthorName();
            authorName.setText(author.getAuthorName());
            authorBio.setText(author.getAuthorBio());
        }
    }
}

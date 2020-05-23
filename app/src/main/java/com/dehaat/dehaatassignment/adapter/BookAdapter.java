package com.dehaat.dehaatassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BooksViewHolder> {

    private Context mContext;
    private List<Book> data;

    public BookAdapter(Context context, List<Book> books) {
        this.mContext = context;
        this.data = books;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new BooksViewHolder(inflater.inflate(R.layout.view_author_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.fillView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null? data.size() : 0;
    }

    class BooksViewHolder extends RecyclerView.ViewHolder {

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void fillView(Book book) {
            if(book == null) return;
        }
    }
}

package com.dehaat.dehaatassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BooksViewHolder> {

    private Context mContext;
    private List<Book> data;

    public BookAdapter(Context context) {
        this.mContext = context;
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

    public void setData(List<Book> bookList) {
        this.data = bookList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class BooksViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

        public void fillView(Book book) {
            if (book == null) return;
            title.setText(book.getTitle());
            description.setText(book.getDescription());
        }
    }
}

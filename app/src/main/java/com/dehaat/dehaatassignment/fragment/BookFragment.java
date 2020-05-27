package com.dehaat.dehaatassignment.fragment;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.adapter.BookAdapter;
import com.dehaat.dehaatassignment.model.Book;
import com.dehaat.dehaatassignment.viewmodel.BookViewModel;

import java.util.List;

public class BookFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private Context mContext;
    private BookViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String authorName = null;
        if (getArguments() != null) {
            authorName = getArguments().getString("author_name");
        }
        mContext = getActivity();
        mViewModel = new ViewModelProvider(this, new BookViewModel.Factory((Application) mContext.getApplicationContext())).get(BookViewModel.class);
        mRecyclerView = getView().findViewById(R.id.rv_book_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new BookAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        mViewModel.getBookList(authorName).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                mAdapter.setData(books);
            }
        });

    }
}

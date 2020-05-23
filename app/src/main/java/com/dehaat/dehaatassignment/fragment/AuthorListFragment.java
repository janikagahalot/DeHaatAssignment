package com.dehaat.dehaatassignment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.adapter.AuthorAdapter;
import com.dehaat.dehaatassignment.model.Author;

import java.util.List;

public class AuthorListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AuthorAdapter mAdapter;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_author_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mContext = getActivity();
        mRecyclerView = getView().findViewById(R.id.rv_author);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AuthorAdapter(mContext, null, new AuthorClickListener() {
            @Override
            public void onClickAuthor() {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface AuthorClickListener {
        void onClickAuthor();
    }
}

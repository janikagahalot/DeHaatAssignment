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
import com.dehaat.dehaatassignment.activity.MainActivity;
import com.dehaat.dehaatassignment.adapter.AuthorAdapter;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.viewmodel.AuthorViewModel;

import java.util.List;

public class AuthorListFragment extends Fragment {

    public static final String FRAGMENT_NAME = "list";
    private RecyclerView mRecyclerView;
    private AuthorAdapter mAdapter;
    private Context mContext;
    private AuthorViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(this, new AuthorViewModel.Factory((Application) mContext.getApplicationContext())).get(AuthorViewModel.class);
        mRecyclerView = getView().findViewById(R.id.rv_author);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AuthorAdapter(mContext, new AuthorClickListener() {

            @Override
            public void onClickAuthor(String authorName) {
                openBookActivity(authorName);
            }
        });
        mRecyclerView.setAdapter(mAdapter);


        mViewModel.getAuthorList().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(@Nullable final List<Author> words) {
                // Update the cached copy of the author in the adapter.
                mAdapter.setData(words);
            }
        });
    }

    private void openBookActivity(String authorName) {
        BookFragment fragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("author_name", authorName);
        fragment.setArguments(bundle);
        ((MainActivity) mContext).replaceFragment(fragment, R.id.content_frame, true, BookFragment.FRAGMENT_NAME);

    }

    public void clearDatabase() {
        mViewModel.clearDatabase();
    }

    public interface AuthorClickListener {
        void onClickAuthor(String authorName);
    }
}

package com.dehaat.dehaatassignment.fragment;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.adapter.AuthorAdapter;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.AuthorsResponse;
import com.dehaat.dehaatassignment.rest.AppRestClient;
import com.dehaat.dehaatassignment.rest.AppRestClientService;
import com.dehaat.dehaatassignment.viewmodel.AuthorViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AuthorAdapter mAdapter;
    private Context mContext;
    private AuthorViewModel authorViewModel;

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
        authorViewModel = new ViewModelProvider(this, new AuthorViewModel.Factory((Application) mContext.getApplicationContext())).get(AuthorViewModel.class);
        mRecyclerView = getView().findViewById(R.id.rv_author);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AuthorAdapter(mContext, new AuthorClickListener() {
            @Override
            public void onClickAuthor() {

            }
        });
        mRecyclerView.setAdapter(mAdapter);


        authorViewModel.getAuthorList().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(@Nullable final List<Author> words) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setData(words);
            }
        });
    }

    public interface AuthorClickListener {
        void onClickAuthor();
    }
}

package com.dehaat.dehaatassignment.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.fragment.AuthorListFragment;
import com.dehaat.dehaatassignment.fragment.BookFragment;
import com.dehaat.dehaatassignment.util.FragmentActionListener;

public class MainActivity extends AppCompatActivity implements FragmentActionListener {

    public static final String USER_PREF = "userPref";
    public static final int LOGIN_COMPLETED = 10;

    private TextView logoutTextView;
    private String selectedAuthorName;
    private String defaultAuthorName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_COMPLETED) {
            openAuthorList();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Orientation changed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedAuthor", selectedAuthorName == null ? defaultAuthorName : selectedAuthorName);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedAuthorName = savedInstanceState.getString("selectedAuthor", null);
    }

    private void init(Bundle savedInstanceState) {
        logoutTextView = findViewById(R.id.tv_logout);
        SharedPreferences prefs = getSharedPreferences(USER_PREF, MODE_PRIVATE);
        String authToken = prefs.getString("auth_token", null);//"No name defined" is the default value.
        if (authToken == null) {
            openLoginActivity();
        } else {
            if (findViewById(R.id.activity_main_portrait) != null) {
                if (savedInstanceState == null) {
                    openAuthorList();
                }
            } else if (findViewById(R.id.activity_main_landscape) != null) {
                openAuthorList();
                openBookActivity(savedInstanceState.getString("selectedAuthor", null));
            }
        }

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_COMPLETED);
    }

    public void replaceFragment(Fragment fragment, int id, boolean addToBackStack, String backStackName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(id, fragment, backStackName);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackName);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment, int id, boolean addToBackStack, String backStackName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment, backStackName);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackName);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void openAuthorList() {
        AuthorListFragment fragment = new AuthorListFragment();
        fragment.setFragmentActionListener(this);
        addFragment(fragment, R.id.content_frame, false, AuthorListFragment.FRAGMENT_NAME);
    }

    private void openBookActivity(String authorName) {
        BookFragment fragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("author_name", authorName);
        fragment.setArguments(bundle);
        if (findViewById(R.id.activity_main_landscape) == null) {
            replaceFragment(fragment, R.id.content_frame, true, BookFragment.FRAGMENT_NAME);
        } else {
            replaceFragment(fragment, R.id.content_frame2, true, BookFragment.FRAGMENT_NAME);
        }
    }

    public void logout() {
        deleteAuthToken();
        clearDatabase();
        clearBackStack();
        openLoginActivity();
    }

    private void clearDatabase() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AuthorListFragment resultsFragment = (AuthorListFragment) fragmentManager.findFragmentByTag(AuthorListFragment.FRAGMENT_NAME);
        if (resultsFragment != null && resultsFragment.isAdded()) {
            resultsFragment.clearDatabase();
        }
    }

    private void clearBackStack() {
        try {
            getSupportFragmentManager()
                    .popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
        }
    }

    /*
      Removes the auth token and logs out the user
     */
    private void deleteAuthToken() {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.USER_PREF, MODE_PRIVATE).edit();
        editor.putString("auth_token", null);
        editor.apply();
    }

    @Override
    public void onAuthorClicked(String authorName) {
        selectedAuthorName = authorName;
        openBookActivity(authorName);
    }

    @Override
    public void setDefaultAuthorName(String authorName) {
        defaultAuthorName = authorName;
    }
}

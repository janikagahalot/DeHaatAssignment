package com.dehaat.dehaatassignment.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.fragment.AuthorListFragment;

public class MainActivity extends AppCompatActivity {

    public static final String USER_PREF = "userPref";
    public static final int LOGIN_COMPLETED = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_COMPLETED) {
            openAuthorList();
        }
    }

    private void init() {
        SharedPreferences prefs = getSharedPreferences(USER_PREF, MODE_PRIVATE);
        String authToken = prefs.getString("auth_token", null);//"No name defined" is the default value.
        if (authToken == null) {
            openLoginActivity();
        } else {
            openAuthorList();
        }
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

    private void openAuthorList() {
        replaceFragment(new AuthorListFragment(), R.id.content_frame, true, "list");
    }

    public void logout() {

    }
}

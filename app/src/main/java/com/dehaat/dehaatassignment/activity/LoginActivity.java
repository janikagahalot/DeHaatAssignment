package com.dehaat.dehaatassignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.model.LoginResponse;
import com.dehaat.dehaatassignment.model.LoginResponseData;
import com.dehaat.dehaatassignment.model.User;
import com.dehaat.dehaatassignment.rest.AppRestClient;
import com.dehaat.dehaatassignment.rest.AppRestClientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginBtn;
    private AppRestClientService appRestClientService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.bt_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginBtnClick();
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void onLoginBtnClick() {
        if(!isEmailValid() || !isPasswordValid()) return;
        appRestClientService = AppRestClient.getInstance().getAppRestClientService();
        Call<LoginResponse> loginResponseCall = appRestClientService.login(new User(emailEditText.getText().toString(), passwordEditText.getText().toString()));
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse == null) return;
                onSuccessfulResponse(loginResponse.getData());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid() {
        String email = emailEditText.getText().toString();
        if (email.matches(emailPattern) && email.length() > 0) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isPasswordValid() {
        String password = passwordEditText.getText().toString();
        if (password.length() > 6) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void onSuccessfulResponse(LoginResponseData data) {
        if (data == null || data.getStatus().equals("fail")) {
            Toast.makeText(LoginActivity.this, "Login fail, try again", Toast.LENGTH_SHORT).show();
            return;
        }
        if (data.getStatus().equals("success")) {
            saveAuthToken(data.getAuthToken());
            Intent intent = new Intent();
            setResult(MainActivity.LOGIN_COMPLETED, intent);
            finish();
        }

    }

    private void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.USER_PREF, MODE_PRIVATE).edit();
        editor.putString("auth_token", authToken);
        editor.apply();
    }


}

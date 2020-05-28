package com.dehaat.dehaatassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginResponseData implements Parcelable {

    private String status;
    @SerializedName("auth_token")
    private String authToken;

    protected LoginResponseData(Parcel in) {
        status = in.readString();
        authToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(authToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginResponseData> CREATOR = new Creator<LoginResponseData>() {
        @Override
        public LoginResponseData createFromParcel(Parcel in) {
            return new LoginResponseData(in);
        }

        @Override
        public LoginResponseData[] newArray(int size) {
            return new LoginResponseData[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}

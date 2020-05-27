package com.dehaat.dehaatassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthorsResponse implements Parcelable {

    @SerializedName("data")
    public List<AuthorBookDetailResponse> data;


    protected AuthorsResponse(Parcel in) {
        data = in.createTypedArrayList(AuthorBookDetailResponse.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuthorsResponse> CREATOR = new Creator<AuthorsResponse>() {
        @Override
        public AuthorsResponse createFromParcel(Parcel in) {
            return new AuthorsResponse(in);
        }

        @Override
        public AuthorsResponse[] newArray(int size) {
            return new AuthorsResponse[size];
        }
    };
}

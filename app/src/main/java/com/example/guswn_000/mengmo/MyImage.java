package com.example.guswn_000.mengmo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guswn_000 on 2017-06-11.
 */

public class MyImage implements Parcelable
{
    private String title;

    public MyImage(String title) {
        this.title = title;
    }

    protected MyImage(Parcel in) {
        title = in.readString();
    }

    public static final Creator<MyImage> CREATOR = new Creator<MyImage>() {
        @Override
        public MyImage createFromParcel(Parcel in) {
            return new MyImage(in);
        }

        @Override
        public MyImage[] newArray(int size) {
            return new MyImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

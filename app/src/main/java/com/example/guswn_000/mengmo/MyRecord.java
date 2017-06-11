package com.example.guswn_000.mengmo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guswn_000 on 2017-06-10.
 */

public class MyRecord implements Parcelable
{
    private String title;

    public MyRecord(String title) {
        this.title = title;
    }

    protected MyRecord(Parcel in) {
        title = in.readString();
    }

    public static final Creator<MyRecord> CREATOR = new Creator<MyRecord>() {
        @Override
        public MyRecord createFromParcel(Parcel in) {
            return new MyRecord(in);
        }

        @Override
        public MyRecord[] newArray(int size) {
            return new MyRecord[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

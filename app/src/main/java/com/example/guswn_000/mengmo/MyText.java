package com.example.guswn_000.mengmo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guswn_000 on 2017-06-11.
 */

public class MyText implements Parcelable
{
    private String title;
    private String content;
    private String date;

    public MyText(String title)
    {
        this.title = title;
    }

    public MyText(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    protected MyText(Parcel in)
    {
        title = in.readString();
        content = in.readString();
        date = in.readString();
    }

    public static final Creator<MyText> CREATOR = new Creator<MyText>() {
        @Override
        public MyText createFromParcel(Parcel in) {
            return new MyText(in);
        }

        @Override
        public MyText[] newArray(int size) {
            return new MyText[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

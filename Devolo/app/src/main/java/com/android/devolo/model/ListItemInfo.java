package com.android.devolo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elitsa on 10.2.2018
 */

public class ListItemInfo implements Parcelable {

    private String title;
    private String username;
    private String imageUrl;
    private boolean isSelected;

    public ListItemInfo(String title, String username, String imageUrl) {
        this.title = title;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    protected ListItemInfo(Parcel in) {
        title = in.readString();
        username = in.readString();
        imageUrl = in.readString();
        isSelected = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(username);
        dest.writeString(imageUrl);
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListItemInfo> CREATOR = new Parcelable.Creator<ListItemInfo>() {
        @Override
        public ListItemInfo createFromParcel(Parcel in) {
            return new ListItemInfo(in);
        }

        @Override
        public ListItemInfo[] newArray(int size) {
            return new ListItemInfo[size];
        }
    };
}
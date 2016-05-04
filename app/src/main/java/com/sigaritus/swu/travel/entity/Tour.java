package com.sigaritus.swu.travel.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVFile;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Tour implements Parcelable {
    private String description;
    private String fees;

    private String maxNum;
    private String username;
    private String cover;
    private String duration;

    private String title;

    private String meetPlace;
    private String avatar;

    private String attention;
    private String contact;

    private String createdAt;
    private String updateAt;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.fees);
        dest.writeString(this.maxNum);
        dest.writeString(this.username);
        dest.writeString(this.cover);
        dest.writeString(this.duration);
        dest.writeString(this.title);
        dest.writeString(this.meetPlace);
        dest.writeString(this.avatar);
        dest.writeString(this.attention);
        dest.writeString(this.contact);
        dest.writeString(this.createdAt);
        dest.writeString(this.updateAt);
    }

    public Tour() {
    }

    protected Tour(Parcel in) {
        this.description = in.readString();
        this.fees = in.readString();
        this.maxNum = in.readString();
        this.username = in.readString();
        this.cover = in.readParcelable(AVFile.class.getClassLoader());
        this.duration = in.readString();
        this.title = in.readString();
        this.meetPlace = in.readString();
        this.avatar = in.readParcelable(AVFile.class.getClassLoader());
        this.attention = in.readString();
        this.contact = in.readString();
        this.createdAt = in.readString();
        this.updateAt = in.readString();
    }

    public static final Creator<Tour> CREATOR = new Creator<Tour>() {
        public Tour createFromParcel(Parcel source) {
            return new Tour(source);
        }

        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };
}

package com.mm.kyys.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 27740 on 2017/1/23.
 */

public class Doctor implements Parcelable {

    private String name;
    private String photo;
    private int reception;//接诊量
    private String summary;//简介
    private String job;

    public Doctor() {
    }

    public Doctor(String name, String photo, int reception, String summary, String job) {
        this.name = name;
        this.photo = photo;
        this.reception = reception;
        this.summary = summary;
        this.job = job;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", reception=" + reception +
                ", summary='" + summary + '\'' +
                ", job='" + job + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getReception() {
        return reception;
    }

    public void setReception(int reception) {
        this.reception = reception;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.photo);
        dest.writeInt(this.reception);
        dest.writeString(this.summary);
        dest.writeString(this.job);
    }

    protected Doctor(Parcel in) {
        this.name = in.readString();
        this.photo = in.readString();
        this.reception = in.readInt();
        this.summary = in.readString();
        this.job = in.readString();
    }

    public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel source) {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}

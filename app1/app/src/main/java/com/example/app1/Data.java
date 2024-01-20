
package com.example.app1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;


public class Data implements Parcelable, Serializable {
    private String dataimg;
    private String dataname;
    private String dataprice;
    private String datadesc;
    private String key;
    private String section;

    // Default constructor with no arguments
    public Data() {
    }

    protected Data(Parcel in) {
        dataimg = in.readString();
        dataname = in.readString();
        dataprice = in.readString();
        datadesc = in.readString();
        key = in.readString();
        section = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSection() {
        return section;
    }

    public String getDatadesc() {
        return datadesc;
    }

    public String getDataname() {
        return dataname;
    }

    public String getDataprice() {
        return dataprice;
    }

    public String getDataimg() {
        return dataimg;
    }

    public Data(String section, String dataname, String dataprice, String datadesc, String dataImg) {
        this.section = section;
        this.dataname = dataname;
        this.dataprice = dataprice;
        this.datadesc = datadesc;
        this.dataimg = dataImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dataimg);
        dest.writeString(dataname);
        dest.writeString(dataprice);
        dest.writeString(datadesc);
        dest.writeString(key);
        dest.writeString(section);
    }
}








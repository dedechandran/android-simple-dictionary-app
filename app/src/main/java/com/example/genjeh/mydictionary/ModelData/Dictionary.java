package com.example.genjeh.mydictionary.ModelData;

import android.os.Parcel;
import android.os.Parcelable;

public class Dictionary implements Parcelable {
    private int dictID;
    private String dictWord;
    private String dictDesc;

    public Dictionary() {
    }

    public int getDictID() {
        return dictID;
    }

    public void setDictID(int dictID) {
        this.dictID = dictID;
    }

    public String getDictWord() {
        return dictWord;
    }

    public void setDictWord(String dictWord) {
        this.dictWord = dictWord;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dictID);
        dest.writeString(this.dictWord);
        dest.writeString(this.dictDesc);
    }

    protected Dictionary(Parcel in) {
        this.dictID = in.readInt();
        this.dictWord = in.readString();
        this.dictDesc = in.readString();
    }

    public static final Parcelable.Creator<Dictionary> CREATOR = new Parcelable.Creator<Dictionary>() {
        @Override
        public Dictionary createFromParcel(Parcel source) {
            return new Dictionary(source);
        }

        @Override
        public Dictionary[] newArray(int size) {
            return new Dictionary[size];
        }
    };
}

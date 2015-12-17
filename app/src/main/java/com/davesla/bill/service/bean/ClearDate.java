package com.davesla.bill.service.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by hwb on 15/12/14.
 */
@SuppressLint("ParcelCreator")
@AVClassName("ClearDate")
public class ClearDate extends AVObject {
    public ClearDate(Parcel in){
        super(in);
    }

    public static final Creator CREATOR = AVObjectCreator.instance;

    public ClearDate() {

    }

    public void setDate(Date date) {
        put("date",date);
    }

    public void setIndex(int index) {
        put("index",index);
    }

    public Date getDate() {
        return getDate("date");
    }

    public int getIndex() {
        return getInt("index");
    }
}

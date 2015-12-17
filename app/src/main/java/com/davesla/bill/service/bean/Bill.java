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
@AVClassName("Bill")
public class Bill extends AVObject{
    public Bill(){
    }

    public Bill(Parcel in){
        super(in);
    }

    public static final Creator CREATOR = AVObjectCreator.instance;

    public String getCategoryName() {
        return getString("categoryName");
    }

    public void setCategoryName(String categoryName) {
        put("categoryName",categoryName);
    }

    public double getCost() {
        return getDouble("cost");
    }

    public void setCost(float cost) {
        put("cost",cost);
    }

    public String getUserName() {
        return getString("userName");
    }

    public void setUserName(String userName) {
        put("userName",userName);
    }

    public String getDetail() {
        return getString("detail");
    }

    public void setDetail(String detail) {
       put("detail",detail);
    }

    public Date getClearDate() {
        return getDate("clearDate");
    }

    public void setClearDate(Date clearDate) {
       put("clearDate",clearDate);
    }
}

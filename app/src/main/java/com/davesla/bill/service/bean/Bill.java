package com.davesla.bill.service.bean;

import android.annotation.SuppressLint;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by hwb on 15/12/14.
 */
@SuppressLint("ParcelCreator")
@AVClassName("Bill")
public class Bill extends AVObject{
    private String categoryName;
    private double cost;
    private String userName;
    private String detail;
    private Date clearDate;

    public String getCategoryName() {
        return getString("categoryName");
    }

    public void setCategoryName(String categoryName) {
        put("category",categoryName);
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

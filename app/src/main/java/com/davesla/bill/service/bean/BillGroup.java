package com.davesla.bill.service.bean;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hwb on 15/12/14.
 */
public class BillGroup {
    public String title;
    public Date start, end;
    public ArrayList<Bill> bills;
    public ArrayList<String> users;
    public ArrayList<Double> costs;
    public String[] groupMembers = {"何卫兵","王正昕","封光"};

}

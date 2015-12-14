package com.davesla.bill.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.davesla.bill.service.bean.Bill;
import com.davesla.bill.service.bean.BillGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hwb on 15/12/14.
 */
public class BillService {
    public interface OnGetBillGroupsHandler {
        void onSucceed(ArrayList<BillGroup> billGroups);

        void onFailed(AVException e);
    }

    public static void getBillGroups(final OnGetBillGroupsHandler onGetBillGroupsHandler) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Bill");
        query.orderByDescending("clearDate");
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    ArrayList<BillGroup> billGroups = new ArrayList<>();
                    BillGroup billGroup = new BillGroup();
                    billGroup.bills = new ArrayList<>();
                    billGroup.users = new ArrayList<>();
                    billGroup.costs = new ArrayList<>();
                    billGroup.title = "第1期账单";
                    Bill firstBill = ((Bill) avObjects.get(0));
                    Date currentDate = firstBill.getClearDate();
                    billGroup.start = firstBill.getCreatedAt();
                    billGroup.end = currentDate;
                    HashMap<String, Double> costMap = new HashMap<String, Double>();

                    for (int i = 0; i < avObjects.size(); i++) {
                        AVObject avObject = avObjects.get(i);
                        Bill bill = (Bill) avObject;
                        if (costMap.containsKey(bill.getUserName())) {
                            double value = costMap.get(bill.getUserName()) + bill.getCost();
                            costMap.put(bill.getUserName(), value);
                        } else {
                            costMap.put(bill.getUserName(), bill.getCost());
                        }

                        if (bill.getClearDate().getTime() == currentDate.getTime()) {
                            billGroup.bills.add(bill);
                        } else {
                            Iterator iter = costMap.entrySet().iterator();
                            while (iter.hasNext()) {
                                Map.Entry entry = (Map.Entry) iter.next();
                                Object key = entry.getKey();
                                Object val = entry.getValue();
                                billGroup.users.add((String) key);
                                billGroup.costs.add((Double) val);
                            }
                            billGroups.add(billGroup);
                            currentDate = bill.getClearDate();
                            costMap = new HashMap<String, Double>();

                            billGroup = new BillGroup();
                            billGroup.title = "第" + (billGroups.size() + 1) + "期账单";
                            billGroup.start = bill.getCreatedAt();
                            billGroup.end = bill.getClearDate();
                            billGroup.bills = new ArrayList<>();
                            billGroup.users = new ArrayList<>();
                            billGroup.costs = new ArrayList<>();
                            billGroup.bills.add(bill);
                        }
                    }
                    Iterator iter = costMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Object key = entry.getKey();
                        Object val = entry.getValue();
                        billGroup.users.add((String) key);
                        billGroup.costs.add((Double) val);
                    }
                    billGroups.add(billGroup);
                    onGetBillGroupsHandler.onSucceed(billGroups);

                } else {
                    onGetBillGroupsHandler.onFailed(e);
                }
            }
        });
    }
}
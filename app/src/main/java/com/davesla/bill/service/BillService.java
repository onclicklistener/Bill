package com.davesla.bill.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
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
    public interface OnRemoveHandler {
        void onSucceed();

        void onFailed(AVException e);
    }

    public static void remove(Bill bill, final OnRemoveHandler onRemoveHandler) {
        bill.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    onRemoveHandler.onSucceed();
                } else {
                    onRemoveHandler.onFailed(e);
                }
            }
        });
    }

    public interface OnClearHandler {
        void onSucceed();

        void onFailed(AVException e);
    }

    public static void clear(ArrayList<Bill> bills, final OnClearHandler onClearHandler) {
        final Date nowDate = new Date(System.currentTimeMillis());
        for (Bill bill : bills) {
            bill.setClearDate(nowDate);
        }

        Bill.saveAllInBackground(bills, new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    onClearHandler.onSucceed();
                } else {
                    onClearHandler.onFailed(e);
                }
            }
        });
    }


    public interface OnAddBillHandler {
        void onSucceed();

        void onFailed(AVException e);
    }

    public static void addBill(Bill bill, final OnAddBillHandler onAddBillHandler) {
        bill.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    onAddBillHandler.onSucceed();
                } else {
                    onAddBillHandler.onFailed(e);
                }
            }
        });
    }

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
                    Bill firstBill = ((Bill) avObjects.get(0));
                    Date currentDate = firstBill.getClearDate();
                    billGroup.start = firstBill.getCreatedAt();
                    billGroup.end = currentDate;
                    HashMap<String, Double> costMap = new HashMap<String, Double>();

                    for (int i = 0; i < avObjects.size(); i++) {
                        AVObject avObject = avObjects.get(i);
                        Bill bill = (Bill) avObject;

                        if (bill.getClearDate().getTime() == currentDate.getTime()) {
                            if (costMap.containsKey(bill.getUserName())) {
                                double value = costMap.get(bill.getUserName()) + bill.getCost();
                                costMap.put(bill.getUserName(), value);
                            } else {
                                costMap.put(bill.getUserName(), bill.getCost());
                            }
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
                            billGroup.start = bill.getCreatedAt();
                            billGroup.end = bill.getClearDate();
                            billGroup.bills = new ArrayList<>();
                            billGroup.users = new ArrayList<>();
                            billGroup.costs = new ArrayList<>();
                            if (costMap.containsKey(bill.getUserName())) {
                                double value = costMap.get(bill.getUserName()) + bill.getCost();
                                costMap.put(bill.getUserName(), value);
                            } else {
                                costMap.put(bill.getUserName(), bill.getCost());
                            }
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
                    if (billGroup.end.getTime() == -28800000) {
                        billGroups.add(0, billGroup);
                    } else {
                        billGroups.add(billGroup);
                    }
                    for (int i = 0; i < billGroups.size(); i++) {
                        BillGroup bill = billGroups.get(i);
                        bill.title = "第" + (billGroups.size() - i) + "期账单";
                        ArrayList<String> users = bill.users;
                        if (users.size() < bill.groupMembers.length) {
                            for (String name : bill.groupMembers) {
                                boolean isExist = false;
                                for (String user : users) {
                                    if (name.equals(user)) {
                                        isExist = true;
                                        break;
                                    }
                                }
                                if (!isExist) {
                                    bill.users.add(name);
                                    bill.costs.add(0d);
                                }
                            }
                        }
                    }
                    onGetBillGroupsHandler.onSucceed(billGroups);

                } else {
                    onGetBillGroupsHandler.onFailed(e);
                }
            }
        });
    }
}

package com.davesla.bill.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.davesla.bill.R;
import com.davesla.bill.adapter.BillAdapter;
import com.davesla.bill.bean.event.OnAddEvent;
import com.davesla.bill.bean.event.OnClearEvent;
import com.davesla.bill.service.BillService;
import com.davesla.bill.service.bean.BillGroup;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;


public class HomeActivityFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BillService.OnGetBillGroupsHandler {
    SuperRecyclerView recyclerView;
    BillAdapter billAdapter;

    private ArrayList<BillGroup> billGroups = new ArrayList<>();

    private static Handler handler = new Handler();

    public HomeActivityFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (SuperRecyclerView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(billAdapter = new BillAdapter(recyclerView, getBaseActivity(), billGroups));

        recyclerView.setRefreshListener(this);

        BillService.getBillGroups(this);
    }

    @Override
    public void onRefresh() {
        BillService.getBillGroups(this);
    }

    @Override
    public void onSucceed(final ArrayList<BillGroup> billGroups) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                HomeActivityFragment.this.billGroups.clear();
                HomeActivityFragment.this.billGroups.addAll(billGroups);
                billAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onFailed(AVException e) {
        System.out.println("exception:" + e);
    }

    public void onEventMainThread(OnAddEvent event) {
        BillService.getBillGroups(this);
    }

    public void onEventMainThread(OnClearEvent event) {
        BillService.getBillGroups(this);
    }
}

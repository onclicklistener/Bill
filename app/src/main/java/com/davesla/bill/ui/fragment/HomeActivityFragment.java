package com.davesla.bill.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davesla.bill.R;
import com.davesla.bill.adapter.BillAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;


public class HomeActivityFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    SuperRecyclerView recyclerView;
    BillAdapter billAdapter;

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
        recyclerView.setAdapter(billAdapter = new BillAdapter(getBaseActivity()));

        recyclerView.setRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        billAdapter.notifyDataSetChanged();
    }
}

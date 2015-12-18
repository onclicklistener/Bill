package com.davesla.bill.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.davesla.bill.R;
import com.davesla.bill.adapter.DetailAdapter;
import com.davesla.bill.bean.event.OnClearEvent;
import com.davesla.bill.bean.event.OnDeleteEvent;
import com.davesla.bill.service.BillService;
import com.davesla.bill.service.bean.Bill;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class DetailActivity extends BaseActivity {
    private static final String BILL_EXTRA = "BILL_EXTRA";
    private static final String CLEAR_EXTRA = "CLEAR_EXTRA";
    private static final String INDEX_EXTRA = "INDEX_EXTRA";

    private ArrayList<Bill> bills;
    private boolean isClear;

    private SuperRecyclerView recyclerView;
    private Button btnClear;
    private TextView textTotal;
    private DetailAdapter adapter;

    private BarChart barChart;

    private static Handler handler = new Handler();

    @Override
    protected void setContent() {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void initView() {
        barChart = (BarChart) findViewById(R.id.chart);
        recyclerView = (SuperRecyclerView) findViewById(R.id.list);
        btnClear = (Button) findViewById(R.id.btn_clear);
        textTotal = (TextView) findViewById(R.id.tv_total);
    }

    @Override
    protected void initData() {
        isClear = getIntent().getBooleanExtra(CLEAR_EXTRA, false);
        bills = getIntent().getParcelableArrayListExtra(BILL_EXTRA);
        colorStatusBar();
        initChart();
        setData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter = new DetailAdapter(recyclerView, this, bills));

        recyclerView.setupSwipeToDismiss(new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return !isClear;
            }

            @Override
            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                final int position = reverseSortedPositions[0];
                Bill bill = bills.get(position);
                BillService.remove(bill, new BillService.OnRemoveHandler() {
                    @Override
                    public void onSucceed() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bills.remove(position);
                                setData();
                                adapter.notifyDataSetChanged();
                                EventBus.getDefault().post(new OnDeleteEvent());
                            }
                        });
                    }

                    @Override
                    public void onFailed(AVException e) {

                    }
                });
            }
        });

        btnClear.setEnabled(!isClear);
        btnClear.setText(isClear ? "已结算" : "结算");
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillService.clear(bills, new BillService.OnClearHandler() {
                    @Override
                    public void onSucceed() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                isClear = true;
                                showToast("已结算");
                                btnClear.setText("已结算");
                                btnClear.setEnabled(false);
                                EventBus.getDefault().post(new OnClearEvent());
                            }
                        });

                    }

                    @Override
                    public void onFailed(AVException e) {
                        showToast("结算出错:" + e.toString());
                    }
                });
            }
        });
    }

    private void initChart() {
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setHighlightPerTapEnabled(false);
        barChart.setDrawValueAboveBar(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setTextColor(getResourceColor(R.color.textGray));
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setTextColor(getResourceColor(R.color.textGray));
        barChart.getAxisRight().setTextColor(getResourceColor(R.color.textGray));
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawLabels(false);

        barChart.getAxisLeft().setDrawGridLines(false);

    }

    private void setData() {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        float total = 0f;
        for (Bill bill : bills) {
            total += bill.getCost();
        }

        textTotal.setText("¥" + df.format(total));

        LinkedHashMap<String, Double> billMap = new LinkedHashMap<>();
        billMap.put("纯净水", 0d);
        billMap.put("生活用品", 0d);
        billMap.put("电费", 0d);
        billMap.put("水费", 0d);
        billMap.put("食品", 0d);
        billMap.put("燃气费", 0d);
        billMap.put("其他", 0d);

        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        for (Bill bill : bills) {
            billMap.put(bill.getCategoryName(), billMap.get(bill.getCategoryName()) + bill.getCost());
        }
        ArrayList<String> names = new ArrayList<String>();

        Iterator<Map.Entry<String, Double>> iterator = billMap.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Double> entry = iterator.next();
            names.add(entry.getKey());
            barEntries.add(new BarEntry((entry.getValue()).intValue(), index));
            index++;
        }

        int colors[] = new int[]{getResources().getColor(R.color.colorAccent)};
        BarDataSet barDataSet = new BarDataSet(barEntries, "各项支出");
        barDataSet.setValueTextColor(getResourceColor(R.color.textGray));
        barDataSet.setValueTextSize(8f);
        barDataSet.setColors(colors);
        barDataSet.setDrawValues(true);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(barDataSet);

        BarData data = new BarData(names, dataSets);

        barChart.setData(data);
        barChart.invalidate();

        barChart.animateY(1500);
        barChart.getLegend().setEnabled(false);
    }


    public static void start(Context context, ArrayList<Bill> bills, boolean isClear, int index) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putParcelableArrayListExtra(BILL_EXTRA, bills);
        intent.putExtra(CLEAR_EXTRA, isClear);
        intent.putExtra(INDEX_EXTRA, index);
        context.startActivity(intent);
    }
}

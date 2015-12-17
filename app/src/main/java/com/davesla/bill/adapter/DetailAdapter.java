package com.davesla.bill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.davesla.bill.R;
import com.davesla.bill.service.bean.Bill;
import com.davesla.bill.ui.activity.BaseActivity;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by hwb on 15/12/8.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private ArrayList<Integer> colors = new ArrayList<>();
    private SuperRecyclerView superRecyclerView;
    private ArrayList<Bill> bills = new ArrayList<>();

    private BaseActivity context;

    public DetailAdapter(SuperRecyclerView superRecyclerView, BaseActivity context, ArrayList<Bill> bills) {
        this.superRecyclerView = superRecyclerView;
        this.context = context;
        this.bills = bills;
        colors.add(context.getResourceColor(R.color.colorElc));
        colors.add(context.getResourceColor(R.color.colorFood));
        colors.add(context.getResourceColor(R.color.colorGas));
        colors.add(context.getResourceColor(R.color.colorIssue));
        colors.add(context.getResourceColor(R.color.colorOthers));
        colors.add(context.getResourceColor(R.color.colorWater));
        colors.add(context.getResourceColor(R.color.colorPureWater));
    }

    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        Bill bill = bills.get(position);
        holder.layoutRoot.setBackgroundColor(colors.get(position % colors.size()));
        holder.category.setText(bill.getCategoryName());
        holder.detail.setText(bill.getDetail());
        holder.cost.setText("￥" + bill.getCost());
        if (bill.getUserName().equals("何卫兵")) {
            holder.avatar.setImageResource(R.drawable.ic_avatar_bing);
        } else if (bill.getUserName().equals("王正昕")) {
            holder.avatar.setImageResource(R.drawable.ic_avatar_xin);
        } else {
            holder.avatar.setImageResource(R.drawable.ic_avatar_feng);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        holder.date.setText("添加于 " + format.format(bill.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layoutRoot;
        TextView date, category, detail, cost;
        ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_root);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            category = (TextView) itemView.findViewById(R.id.tv_category);
            detail = (TextView) itemView.findViewById(R.id.tv_remarks);
            cost = (TextView) itemView.findViewById(R.id.tv_cost);
            avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }

    }


}

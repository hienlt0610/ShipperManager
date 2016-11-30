package edu.hutech.shippermanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.model.Order;

/**
 * Created by jerem on 21/11/2016.
 */

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context context;
    private List<Order> orders;
    private int layoutRes;
    public OrderAdapter(Context context, int resource, List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.orders = objects;
        this.layoutRes = resource;
    }

    public void addOrderItem(Order order){
        this.orders.add(order);
        Collections.sort(this.orders, new Comparator<Order>() {
            @Override
            public int compare(Order order, Order t1) {
                if(order.isRunning() && !t1.isRunning()){
                    return -1;
                }
                else if(!order.isRunning() && t1.isRunning()){
                    return 1;
                }
                else{
                    if(order.isStatus() && !t1.isStatus()){
                        return 1;
                    }else if(!order.isStatus() && t1.isStatus()){
                        return -1;
                    }
                    else{
                        return 0;
                    }
                }
            }
        });
        this.notifyDataSetChanged();
    }

    public boolean updateOrder(Order newOrder){
        if(newOrder == null) return false;
        int indexOldOrder = orders.indexOf(newOrder);
        if(indexOldOrder == -1) return false;
        orders.set(indexOldOrder,newOrder);
        notifyDataSetChanged();
        return true;
    }

    public boolean deleteOrder(Order order) {
        if(order == null) return false;
        boolean isDeteled = orders.remove(order);
        notifyDataSetChanged();
        return isDeteled;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Order order;
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutRes, parent, false);

            // well set up the ViewHolder
            holder = new ViewHolder();
            holder.tvDiaChi = (TextView) convertView.findViewById(R.id.tvDiaChi);
            holder.tvTinhTrang = (TextView) convertView.findViewById(R.id.tvTinhTrang);
            // store the holder with the view.
            convertView.setTag(holder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            holder = (ViewHolder) convertView.getTag();
        }
        order = orders.get(position);
        holder.tvDiaChi.setText(order.getReceiver().getAddress());
        String statusString;
        int statusColor;
        if(order.isStatus()){
            statusString = "Đã giao";
            statusColor = R.drawable.order_status_success_background;
        }
        else if(order.isRunning()){
            statusString = "Đang chạy";
            statusColor = R.drawable.order_status_running;
        }else{
            statusString = "Chưa giao";
            statusColor = R.drawable.order_status_unsuccess;
        }

        holder.tvTinhTrang.setText(statusString);
        holder.tvTinhTrang.setBackgroundResource(statusColor);
        return convertView;
    }



    public class ViewHolder{
        TextView tvDiaChi, tvTinhTrang;
    }
}

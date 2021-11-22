package com.example.friendswar;

import static com.example.friendswar.AddOrderPage.nextAddOrder;
import static com.example.friendswar.MainActivity.addOrder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    ArrayList<String> datas;
    public ListOrderAdapter(ArrayList<String> arr) {
        datas = arr;
    }
    @NonNull
    @Override
    public ListOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_name_item, parent, false);
        return new ListOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrderAdapter.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(position + 1));
        holder.name.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return addOrder.getOrderEdit().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        ImageButton delete;
        public ViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.list_item_id);
            name = (TextView) itemView.findViewById(R.id.list_item_name);
            delete = (ImageButton) itemView.findViewById(R.id.delete_btn);

            itemView.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addOrder.deleteOrder(getAdapterPosition());
                    notifyDataSetChanged();
                    ArrayList<String> p = addOrder.getOrderEdit();
                    if(p.size()<=0) {
                        nextAddOrder.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
}

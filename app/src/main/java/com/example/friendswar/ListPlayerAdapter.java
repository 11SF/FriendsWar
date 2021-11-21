package com.example.friendswar;

import static com.example.friendswar.MainActivity.pcl;
import static com.example.friendswar.AddPlayers.next;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListPlayerAdapter extends RecyclerView.Adapter<ListPlayerAdapter.ViewHolder> {
    ArrayList<Player> datas;
    public ListPlayerAdapter(ArrayList<Player> arr) {
        datas = arr;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_name_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(position + 1));
        holder.name.setText(datas.get(position).getPlayerName());

    }

    @Override
    public int getItemCount() {
        return datas.size();
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
                    ArrayList<Player> arr = pcl.getPlayers();
                    Player deletePlayer = arr.get(getAdapterPosition());
                    pcl.deletePlayer(deletePlayer.getPlayerId());
                    System.out.println(pcl.getPlayers().size());
                    notifyDataSetChanged();
                    ArrayList<Player> p = pcl.getPlayers();
                    if(p.size()<=0) {
                        next.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
}

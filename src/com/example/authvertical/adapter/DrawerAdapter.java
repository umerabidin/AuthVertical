package com.example.authvertical.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.authvertical.R;
import com.example.authvertical.utils.RoleEvent;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    ArrayList<String> tabs;
    Context context;
    RoleEvent roleEvent;
    DrawerItemClick mItemClick;

    public interface DrawerItemClick {
        void onClickItem(RoleEvent roleEvent, int position);
    }

    public DrawerAdapter(Context context, ArrayList<String> tabs, DrawerItemClick mItemClick) {
        this.context = context;
        this.tabs = tabs;
        this.mItemClick = mItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_menu_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (roleEvent != null) {
            if (position == tabs.size() - 1) {
                viewHolder.divider.setVisibility(View.GONE);
            }
            viewHolder.tvItem.setText(tabs.get(position));
            viewHolder.tvItem.setOnClickListener(v -> mItemClick.onClickItem(roleEvent, position));

        }
    }


    public void updateAdapter(ArrayList<String> list, RoleEvent roleEvent) {
        if (tabs != null) {
            tabs.clear();
            tabs.addAll(list);
            this.roleEvent = roleEvent;
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return tabs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
            divider = itemView.findViewById(R.id.divider);
        }
    }

}

package com.deepak.kcl.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deepak.kcl.R;
import com.deepak.kcl.ViewHolders.AdvChildViewHolder;
import com.deepak.kcl.ViewHolders.AdvHeaderViewHolder;
import com.deepak.kcl.models.AdvanceChild;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class AdvanceAdapter extends ExpandableRecyclerViewAdapter<AdvHeaderViewHolder, AdvChildViewHolder> {


    private Context mContext;

    public AdvanceAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        mContext = context;
    }

    @Override
    public AdvHeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heading_expan_advance,parent,false);
        return new AdvHeaderViewHolder(view);
    }

    @Override
    public AdvChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expan_advance,parent,false);
        return new AdvChildViewHolder(view1);
    }

    @Override
    public void onBindChildViewHolder(AdvChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        AdvanceChild advanceChild = (AdvanceChild) group.getItems().get(childIndex);

        holder.setAdvDate(advanceChild.getDate());
        holder.setAdvAmount(advanceChild.getAmount());
        holder.setAdvBranch(advanceChild.getBranch());
        holder.setAdvDesc(advanceChild.getDesc());
    }

    @Override
    public void onBindGroupViewHolder(AdvHeaderViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setHeadName(group.getTitle());
    }
}

package com.deepak.kcl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deepak.kcl.R;
import com.deepak.kcl.ViewHolders.SplitAdvHeadViewHolder;
//import com.deepak.kcl.ViewHolders.SplitAdvItemViewHolder;
import com.deepak.kcl.ViewHolders.SplitAdvItemViewHolder;
import com.deepak.kcl.models.SplitAdvChild;
import com.deepak.kcl.models.SplitAdvData;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class SplitAdvanceAdapter extends ExpandableRecyclerViewAdapter<SplitAdvHeadViewHolder, SplitAdvItemViewHolder> {

    private Context mContext;

    public SplitAdvanceAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        mContext = context;
    }

    @Override
    public SplitAdvHeadViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heading_spliting_advance,parent,false);
        return new SplitAdvHeadViewHolder(view);
    }

    @Override
    public SplitAdvItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spliting_advance,parent,false);
        return new SplitAdvItemViewHolder(view1);
    }

    @Override
    public void onBindChildViewHolder(SplitAdvItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        SplitAdvData splitAdvData = (SplitAdvData) group.getItems().get(childIndex);

        holder.setSplitDate(splitAdvData.getSplit_date());
        holder.setSplitType(splitAdvData.getSplit_type());
        holder.setSplitAmount(splitAdvData.getAmount());
        holder.setSplitBranch(splitAdvData.getBranch_name());
        holder.setSplitDesc(splitAdvData.getDescription());
    }

    @Override
    public void onBindGroupViewHolder(SplitAdvHeadViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setHeadName(group.getTitle());
    }
}

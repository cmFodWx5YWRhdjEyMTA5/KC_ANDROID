package com.deepak.kcl.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class SplitAdvItemViewHolder extends ChildViewHolder {

    private TextView txtDate,txtSplit,txtAmount,txtBranch,txtDesc;

    public SplitAdvItemViewHolder(View itemView) {
        super(itemView);

        txtDate = itemView.findViewById(R.id.item_split_txtDate);
        txtSplit = itemView.findViewById(R.id.item_split_txtType);
        txtAmount = itemView.findViewById(R.id.item_split_txtAmt);
        txtBranch = itemView.findViewById(R.id.item_split_txtBranch);
        txtDesc = itemView.findViewById(R.id.item_split_txtDesc);
    }

    public void setSplitDate(String dt)
    {
        txtDate.setText(dt);
    }

    public void setSplitType(String type)
    {
        txtSplit.setText(type);
    }

    public void setSplitAmount(String amt)
    {
        txtAmount.setText(amt);
    }

    public void setSplitBranch(String branch)
    {
        txtBranch.setText(branch);
    }

    public void setSplitDesc(String desc)
    {
        txtDesc.setText(desc);
    }
}

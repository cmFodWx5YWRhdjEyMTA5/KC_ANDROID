package com.deepak.kcl.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class AdvChildViewHolder extends ChildViewHolder {

    private TextView txtDate,txtAmount,txtDesc;
    public AdvChildViewHolder(View itemView) {
        super(itemView);

        txtDate = itemView.findViewById(R.id.txt_adv_date);
        txtAmount = itemView.findViewById(R.id.txt_adv_amt);
        txtDesc = itemView.findViewById(R.id.txt_adv_desc);
    }

    public void setAdvDate(String dt)
    {
        txtDate.setText(dt);
    }

    public void setAdvAmount(String amt)
    {
        txtAmount.setText(amt);
    }

    public void setAdvDesc(String desc)
    {
        txtDesc.setText(desc);
    }
}

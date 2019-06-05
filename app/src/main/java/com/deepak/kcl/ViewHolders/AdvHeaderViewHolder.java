package com.deepak.kcl.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class AdvHeaderViewHolder extends GroupViewHolder {

    private TextView txtHeadAdv;
    public AdvHeaderViewHolder(View itemView) {
        super(itemView);
        txtHeadAdv = (TextView) itemView.findViewById(R.id.head_advance);
    }

    public void setHeadName(String name)
    {
        txtHeadAdv.setText(name);
    }
}

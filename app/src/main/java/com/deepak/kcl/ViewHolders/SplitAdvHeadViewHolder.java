package com.deepak.kcl.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class SplitAdvHeadViewHolder extends GroupViewHolder {

    private TextView txtHeadSplitAdv;
    public SplitAdvHeadViewHolder(View itemView) {
        super(itemView);

        txtHeadSplitAdv = (TextView) itemView.findViewById(R.id.heading_txt_advType);
    }

    public void setHeadName(String name)
    {
        txtHeadSplitAdv.setText(name);
    }
}

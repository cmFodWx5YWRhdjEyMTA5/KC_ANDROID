package com.deepak.kcl.ViewHolders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

@Parent
@SingleTop
@Layout(R.layout.heading_spliting_advance)
public class SplitAdvHeadViewHolder {

    private static String TAG = "SplitAdvHeadViewHolder";
    @com.mindorks.placeholderview.annotations.View(R.id.heading_txt_advType)
    TextView txtHeadAdv;

    @com.mindorks.placeholderview.annotations.View(R.id.heading_txt_splitAdvAmt)
    TextView txtHeadAdvAmt;

    private Context mContext;
    private String mHeaderText;
    private String mHeaderAmount;

    public SplitAdvHeadViewHolder(Context mContext, String mHeaderText, String mHeaderAmount) {
        this.mContext = mContext;
        this.mHeaderText = mHeaderText;
        this.mHeaderAmount = mHeaderAmount;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG, "onResolve");
        txtHeadAdv.setText(mHeaderText);
        txtHeadAdvAmt.setText(mHeaderAmount);
    }

    @Expand
    private void onExpand(){
        Log.d(TAG, "onExpand");
    }

    @Collapse
    private void onCollapse(){
        Log.d(TAG, "onCollapse");
    }
}

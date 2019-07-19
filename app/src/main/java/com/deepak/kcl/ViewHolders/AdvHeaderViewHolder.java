package com.deepak.kcl.ViewHolders;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

@Parent
@SingleTop
@Layout(R.layout.heading_expan_advance)
public class AdvHeaderViewHolder {

    private static String TAG = "HeaderView";

    @View(R.id.head_advance)
    TextView txtHeadAdv;

    private Context mContext;
    private String mHeaderText;

    public AdvHeaderViewHolder(Context mContext, String mHeaderText) {
        this.mContext = mContext;
        this.mHeaderText = mHeaderText;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG, "onResolve");
        txtHeadAdv.setText(mHeaderText);
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
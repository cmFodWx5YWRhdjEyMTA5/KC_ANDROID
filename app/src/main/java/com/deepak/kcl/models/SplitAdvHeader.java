package com.deepak.kcl.models;

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
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class SplitAdvHeader extends ExpandableGroup {

    public SplitAdvHeader(String title,String Amount, List items) {
        super(title, items);
    }
}

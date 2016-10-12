package com.jimmy.wbclient.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by Administrator on 2016/10/11.
 */
public class Pull2RefreshListView extends PullToRefreshListView {
    public Pull2RefreshListView(Context context) {
        super(context);
    }

    public Pull2RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Pull2RefreshListView(Context context, Mode mode) {
        super(context, mode);
    }
}

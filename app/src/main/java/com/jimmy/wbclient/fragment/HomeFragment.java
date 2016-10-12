package com.jimmy.wbclient.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jimmy.wbclient.BaseFragment;
import com.jimmy.wbclient.R;
import com.jimmy.wbclient.adapter.StatusAdapter;
import com.jimmy.wbclient.api.SimpleRequestListener;
import com.jimmy.wbclient.utils.TitleBuilder;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link BaseFragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private static int RESPONSE_COUNT = 10;//单页返回的记录条数
    private static int CUR_PAGE = 1;// 返回结果的页码，默认为1
    private View mHomeView;
    private PullToRefreshListView mLV_Refresh;
    private boolean mIsLoadMore;
    private List<Status> mStatuses = new ArrayList<>();//微博正文数组
    private StatusAdapter mStatusAdapter;
    private View mFootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //初始化视图
        mHomeView = inflater.inflate(R.layout.fragment_home, container, false);

        initView();
        initData();

        return mHomeView;
    }

    private void initView() {
        new TitleBuilder(mHomeView).setTitleText("首页").build();
        mLV_Refresh = (PullToRefreshListView) mHomeView.findViewById(R.id.prlv_home);

        mLV_Refresh.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(1);
            }
        });
        mLV_Refresh.setOnLastItemVisibleListener(
                new PullToRefreshBase.OnLastItemVisibleListener() {
                    @Override
                    public void onLastItemVisible() {
                        loadData(CUR_PAGE + 1);
                    }
                });

        mFootView = View.inflate(mActivity, R.layout.footview_loading, null);
    }

    private void addFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() == 1) {
            lv.addFooterView(footView);
        }
    }

    private void removeFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() > 1) {
            lv.removeFooterView(footView);
        }
    }

    public void initData() {
        mStatuses = new ArrayList<Status>();
        mStatusAdapter = new StatusAdapter(mActivity, mStatuses);
        mLV_Refresh.setAdapter(mStatusAdapter);

        loadData(1);
    }

    private void loadData(final int requestPage) {
        if (mIsLoadMore) {
            return;
        }
        mIsLoadMore = true;
        mMyStaAPI.homeTimeline(RESPONSE_COUNT, requestPage,
                new SimpleRequestListener(mActivity, mProDia) {

                    @Override
                    public void onComplete(String response) {
                        super.onComplete(response);

                        if (requestPage == 1) {//若请求页面是1就清空本地微博
                            mStatuses.clear();
                        }
                        CUR_PAGE = requestPage;

                        addData(StatusList.parse(response));
                    }

                    @Override
                    public void onAllDone() {
                        super.onAllDone();

                        mIsLoadMore = false;
                        mLV_Refresh.onRefreshComplete();
                    }
                });
    }

    private void addData(StatusList resBean) {
        for (Status status : resBean.statusList) {
            if (!mStatuses.contains(status)) {
                mStatuses.add(status);
            }
        }
        mStatusAdapter.notifyDataSetChanged();

        if (CUR_PAGE < resBean.total_number) {//若当前显示的页面少于返回的页面则显示加载更多
            addFootView(mLV_Refresh, mFootView);
        } else {
            removeFootView(mLV_Refresh, mFootView);
        }
    }

}

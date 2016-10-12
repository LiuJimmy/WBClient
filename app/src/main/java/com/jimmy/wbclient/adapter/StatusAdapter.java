package com.jimmy.wbclient.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimmy.wbclient.R;
import com.jimmy.wbclient.activity.ImageBrowserActivity;
import com.jimmy.wbclient.activity.UserInfoActivity;
import com.jimmy.wbclient.utils.DateUtil;
import com.jimmy.wbclient.utils.PicUtil;
import com.jimmy.wbclient.utils.StringUtil;
import com.jimmy.wbclient.widget.WrapHeightGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class StatusAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity mActivity;
    private List<Status> mStatuses;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    public StatusAdapter(Activity activity, List<Status> statuses) {
        mActivity = activity;
        mStatuses = statuses;
        mInflater = LayoutInflater.from(activity);
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return mStatuses.size();
    }

    @Override
    public Status getItem(int position) {
        return mStatuses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_status, parent, false);

            /**
             * 设置组件
             */
            holder = new ViewHolder();
            holder.mStatusLyout = (LinearLayout) convertView.findViewById(R.id.ll_status_content);
            //微博作者信息栏
            holder.mLLStatusAuthor = (LinearLayout) convertView.findViewById(R.id.ll_status_author);
            holder.mIVStatusAuthorHead = (ImageView) convertView.findViewById(R.id
                    .iv_status_author_head);
            holder.mTVStatusAuthorName = (TextView) convertView.findViewById(R.id
                    .tv_status_author_name);
            holder.mTVStatusAuthorCaption = (TextView) convertView.findViewById(R.id
                    .tv_status_author_caption);
            holder.mIVStatusHeadArrow = (ImageView) convertView.findViewById(R.id
                    .iv_status_head_arrow);
            //微博图文区域
            holder.mTVStatusContent = (TextView) convertView.findViewById(R.id.tv_status_content);
            holder.mStatusMediaLyout = (FrameLayout) convertView.findViewById(R.id
                    .fl_status_media_layout);
            holder.mIVStatusPic = (ImageView) convertView.findViewById(R.id.iv_status_pic);
            holder.mGVStatusPics = (WrapHeightGridView) convertView.findViewById(R.id
                    .whgridView_status_pics);
            //微博转发、评论、点赞区域
            holder.mStautsBottomBar = (LinearLayout) convertView.findViewById(R.id
                    .ll_status_bottom_bar);
            holder.mIVStatusShare = (ImageView) convertView.findViewById(R.id
                    .iv_status_share_bottom);
            holder.mTVStatusShareCount = (TextView) convertView.findViewById(R.id
                    .tv_status_share_bottom);
            holder.mIVStatusComment = (ImageView) convertView.findViewById(R.id
                    .iv_status_comment_bottom);
            holder.mTVStatusCommentCount = (TextView) convertView.findViewById(R.id
                    .tv_status_comment_bottom);
            holder.mIVStatusLike = (ImageView) convertView.findViewById(R.id.iv_status_like_bottom);
            holder.mTVStatusLikeCount = (TextView) convertView.findViewById(R.id
                    .tv_status_like_bottom);

            //转发微博
            holder.mRetweetedStatusLayout = (LinearLayout) convertView.findViewById(R.id
                    .ll_retweeted_status);
            holder.mTVRetweetedContent = (TextView) convertView.findViewById(R.id
                    .tv_retweeted_content);
            holder.mRetweetedStatusMedia = (FrameLayout) convertView.findViewById(R.id
                    .fl_retweeted_status_media);
            holder.mReteetedStatusPic = (ImageView) holder.mRetweetedStatusMedia.findViewById(R
                    .id.iv_status_pic);
            holder.mReteetedGVStatusPics = (WrapHeightGridView) holder.mRetweetedStatusMedia
                    .findViewById(R.id.whgridView_status_pics);

            convertView.setTag(holder);

//            holder.mStatusLyout.setOnClickListener(this);

            holder.mLLStatusAuthor.setOnClickListener(this);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /**
         * 绑定数据
         */
        Status status = getItem(position);//当前要显示的微博
        User user = status.user;
        mImageLoader.displayImage(user.profile_image_url, holder.mIVStatusAuthorHead);
        holder.mTVStatusAuthorName.setText(user.name);
        String caption = DateUtil.createDate(status.created_at);
        if (!TextUtils.isEmpty(status.source)) {
            caption += " 来自 " + Html.fromHtml(status.source);
        }
        holder.mTVStatusAuthorCaption.setText(caption);

        SpannableString spnStr = StringUtil.getWeiboContent(mActivity, holder.mTVStatusContent,
                status.text);
        holder.mTVStatusContent.setText(spnStr);//原创微博文字内容及图片
        setStatusImage(status, holder.mStatusMediaLyout, holder.mIVStatusPic, holder.mGVStatusPics);

        Status retweetedStatus = status.retweeted_status;//转发微博
        if (retweetedStatus != null) {
            holder.mRetweetedStatusLayout.setVisibility(View.VISIBLE);
            User retweetedUser = retweetedStatus.user;
            holder.mTVRetweetedContent.setText("@" + retweetedUser.name + ":" + retweetedStatus
                    .text);
            setStatusImage(retweetedStatus, holder.mRetweetedStatusMedia, holder
                    .mReteetedStatusPic, holder.mReteetedGVStatusPics);
        } else {
            holder.mRetweetedStatusLayout.setVisibility(View.GONE);
        }

        holder.mTVStatusShareCount.setText(status.reposts_count > 0 ? status.reposts_count + "" :
                "转发");
        holder.mTVStatusCommentCount.setText(status.comments_count > 0 ? status.comments_count +
                "" : "评论");
        holder.mTVStatusLikeCount.setText(status.attitudes_count > 0 ? status.attitudes_count +
                "" : "点赞");

        return convertView;
    }

    private void setStatusImage(final Status status, FrameLayout mediaContainer, ImageView
            ivStatusPic, GridView gvStatusPics) {
        final ArrayList<String> picUrls = PicUtil.getBmidUtil(status.pic_urls);//获取微博中等配图地址
        if (picUrls != null && picUrls.size() != 0) {       /////若图片地址不为空切数量不为0，显示微博图片区域
            mediaContainer.setVisibility(View.VISIBLE);
            if (picUrls.size() == 1) {          //若是单图，隐藏九宫格，显示单图
                ivStatusPic.setVisibility(View.VISIBLE);
                gvStatusPics.setVisibility(View.GONE);
                mImageLoader.displayImage(status.bmiddle_pic, ivStatusPic);
                ivStatusPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        previewImage(status.pic_urls, 0);//图片点击事件
                    }
                });
            } else {                            //否则隐藏单图，显示九宫格
                ivStatusPic.setVisibility(View.GONE);
                gvStatusPics.setVisibility(View.VISIBLE);
                StatusGridImgsAdapter gridImgsAdapter = new StatusGridImgsAdapter(picUrls,
                        mActivity, gvStatusPics);
                gvStatusPics.setAdapter(gridImgsAdapter);
                gvStatusPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long
                            id) {
                        previewImage(status.pic_urls, position);
                    }
                });
            }
        } else {
            mediaContainer.setVisibility(View.GONE); ////否则隐藏
        }
    }

    private void previewImage(ArrayList<String> picUrls, int index) {
        Intent intent = new Intent(mActivity, ImageBrowserActivity.class);
        intent.putExtra(ImageBrowserActivity.KEY_IMAGE_URIS, picUrls);
        intent.putExtra(ImageBrowserActivity.KEY_CUR_INDEX, index);
        mActivity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_status_author://点击进入微博作者信息界面
                mActivity.startActivity(new Intent(mActivity, UserInfoActivity.class));
                break;
        }
    }

    class ViewHolder {
        //微博跟布局
        LinearLayout mStatusLyout;
        //微博作者信息部分
        LinearLayout mLLStatusAuthor;
        ImageView mIVStatusAuthorHead;
        TextView mTVStatusAuthorName;
        TextView mTVStatusAuthorCaption;
        ImageView mIVStatusHeadArrow;
        //微博文字内容部分
        TextView mTVStatusContent;
        //微博图片部分
        FrameLayout mStatusMediaLyout;
        ImageView mIVStatusPic;
        WrapHeightGridView mGVStatusPics;
        //转发微博
        LinearLayout mRetweetedStatusLayout;
        TextView mTVRetweetedContent;
        FrameLayout mRetweetedStatusMedia;
        ImageView mReteetedStatusPic;
        WrapHeightGridView mReteetedGVStatusPics;

        //微博转发、评论、点赞区域
        LinearLayout mStautsBottomBar;
        ImageView mIVStatusShare;
        TextView mTVStatusShareCount;
        ImageView mIVStatusComment;
        TextView mTVStatusCommentCount;
        ImageView mIVStatusLike;
        TextView mTVStatusLikeCount;
    }


}

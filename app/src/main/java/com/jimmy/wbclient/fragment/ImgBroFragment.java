package com.jimmy.wbclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jimmy.wbclient.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImgBroFragment extends Fragment {

    private static final String KEY_INDEX = "key_index";

    public static ImgBroFragment newInstance(String uri) {
        Bundle args = new Bundle();
        args.putString(KEY_INDEX, uri);
        ImgBroFragment fragment = new ImgBroFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img_bro, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        String picUrl = getArguments().getString(KEY_INDEX);//原始图地址
//        LogUtil.show("图片地址:", picUrl);
        ImageLoader.getInstance().displayImage(picUrl, imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

}

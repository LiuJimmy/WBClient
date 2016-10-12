package com.jimmy.wbclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.wbclient.api.MyStatusesAPI;
import com.jimmy.wbclient.api.SimpleRequestListener;
import com.jimmy.wbclient.constants.ComConstants;
import com.jimmy.wbclient.model.Emotion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/11.
 */
public class HttpUtil {
    private static final String KEY_FACE = "face_value";
    private static ArrayList<Emotion> sEmoList = new ArrayList<>();
    private static Context sContext;
    private static Bitmap sBitmap;

    /**
     * 获取指定value的表情bitmap，首先读取本地保存的json文件中的value(若没有，则跟新本地EmojiJSON文件),
     * 则根据value的url获取表情btimap
     *
     * @param context
     * @param value
     * @return
     */
    public static Bitmap getEmojiByName(final Context context, String value) {
        sContext = context;
        boolean isHas = loadEmojiJSON().contains(value);
        if (!isHas) {
            updateEmojiJSON();
        }

        Bitmap bitmap = null;
        sEmoList = new Gson().fromJson(loadEmojiJSON(), new TypeToken<ArrayList<Emotion>>() {
        }.getType());
        for (Emotion e : sEmoList) {
            String key_phrase = e.getPhrase();
            String key_value = e.getValue();
            if (key_phrase.equals(value) || key_value.equals(value)) {
                bitmap = requestFace(e.getUrl());
            }
        }

        return bitmap;
    }

    private static Bitmap requestFace(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(5000);
                    con.setReadTimeout(5000);
                    in = con.getInputStream();
                    sBitmap = BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtil.show(sContext, "网络出错,无法获取该表情");
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        return sBitmap;
    }

    public static void updateEmojiJSON() {
        MyStatusesAPI api = new MyStatusesAPI(sContext);
        api.emotions(new SimpleRequestListener(sContext, null) {
            @Override
            public void onComplete(String s) {
                super.onComplete(s);

                if (!TextUtils.isEmpty(s)) {
                    saveEmojiJSON(s); //保存下载的json数据
                } else {
                    ToastUtil.show(sContext, "网络错误,无法更新表情");
                }
            }
        });
    }

    private static String loadEmojiJSON() {
        StringBuilder jsonStr = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStream is = sContext.openFileInput(ComConstants.DATA_EMOJI_JSON);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.show(sContext, "加载本地表情出错");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonStr.toString();
    }

    private static void saveEmojiJSON(String s) {
        BufferedWriter writer = null;
        try {
            OutputStream os = sContext.openFileOutput(ComConstants.DATA_EMOJI_JSON,
                    Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.show(sContext, "保存表情文件出错");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

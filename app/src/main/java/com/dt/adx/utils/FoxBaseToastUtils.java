package com.dt.adx.utils;

import android.content.Context;
import android.widget.Toast;

import com.mediamain.android.FoxSDK;

public class FoxBaseToastUtils {

    public static void showShort(Context context, CharSequence text) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static void showShort(CharSequence text) {
        Toast.makeText(FoxSDK.getContext(),text,Toast.LENGTH_SHORT).show();
    }
}

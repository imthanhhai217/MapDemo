package com.jaroidx.mapdemo;

import android.content.Context;
import android.util.TypedValue;

public class DeviceUtils {
    public static int getDisplayScale(Context mContext, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mContext.getResources().getDisplayMetrics());
    }
}

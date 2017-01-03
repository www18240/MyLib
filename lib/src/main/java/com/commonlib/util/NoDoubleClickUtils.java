package com.commonlib.util;

/**
 * Created by 吴杰 on 2016.12.29.
 */

public class NoDoubleClickUtils {
    private static long lastClickTime;
    private final static int SPACE_TIME = 500;

    public static void initLastClickTime() {
        lastClickTime = 0;
    }

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime >SPACE_TIME) {
            isClick2 = false;
        lastClickTime = currentTime;
        } else {
            isClick2 = true;
        }
        return isClick2;
    }
}
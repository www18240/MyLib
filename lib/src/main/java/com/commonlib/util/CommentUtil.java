package com.commonlib.util;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.commonlib.basebean.BaseParamBean;
import com.commonlib.constant.SPConstant;
import com.google.gson.Gson;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/4/7.
 * <p/>
 * 工具类：提供方法
 */
public class CommentUtil {


    private static final String TAG = "CommentUtil";
    public static final String USER_JID_KEY = "user_jid_key";
    private static HanyuPinyinOutputFormat format;


    /*
        获取状态栏的高度

     */
    public static int getStatusBarHeight(View view) {

        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }



    /*
        把一个字符串带汉字的特殊符号进行拼音化处理
        char str[] = "传智&&额e 播客".toCharArray();

        处理字符串：汉字转拼音，字母转大写，其他符号转#符号，空格不处理
     */
    public static String hanziTextToPinyinFormat(String text) {

        if (TextUtils.isEmpty(text)) return "";
        //转一个存一个，
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = text.toCharArray();

        for (char ch : chars) {

            //空格不处理
            if (Character.isWhitespace(ch)) continue;

            //是汉字就处理
            //判断汉字的正则表达"".matches("[\\u4E00-\\u9FA5]");
            if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]")) {

                stringBuilder.append(hanziToPinyin(ch));
                //如果是字母转大写
            } else if (Character.isLetter(ch)) {

                stringBuilder.append(Character.toUpperCase(ch));
                //其他字符用#代替
            } else {

                stringBuilder.append("#");

            }

        }


        return stringBuilder.toString();
    }


    //工具类：汉字转拼音:一个汉字转拼音:PinyinHelper遇到空格和特殊字符会报错
    public static String hanziToPinyin(char chinese) {

        if (format == null) {

            //输出格式
            format = new HanyuPinyinOutputFormat();
            //不要声调
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            //大写
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }

        try {

            return PinyinHelper.toHanyuPinyinStringArray(chinese, format)[0];
            //Log.i(TAG, strings[0] + " " + strings[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Object evaluateColor(float fraction, Object startValue, Object endValue) {

        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }

    //当fraction从0---->1,  返回值从startValue-----到endValue过渡
    public static Float evaluateFloat(float fraction, Number startValue, Number endValue) {

        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    private static ExecutorService sPool = Executors.newCachedThreadPool();
    private static Toast sToast;

    //子线程执行
    public static void runOnThread(Runnable task) {

        sPool.execute(task);
    }

    //主线程执行
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static Handler getMainHandler() {

        return sHandler;
    }

    //UI现成执行
    public static void runOnUIThread(Runnable task) {

        sHandler.post(task);
    }

    //安全的Toast
    public static void showSafeToast(final Context context, final String text) {

        runOnUIThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //toast弹出文本，下一个弹出的文本覆盖上一个的文本
    public static void showSingleToast(Context context, String text) {

        if (sToast == null) {

            sToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }

        sToast.setText(text);
        sToast.show();
    }


    //dip---px
    public static float dpToPx(Context context, float dp) {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }


    public static String getCurrentTime() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
    public static <T extends BaseParamBean> RequestBody getRequestBody(T parambean){
        Gson gson=new Gson();
        //  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        return  RequestBody.create(MediaType.parse("Content-Type, application/json"),  gson.toJson(parambean));
    }
    public  static  String getUserName(Context context){

        return  (String) SPUtils.get(context, SPConstant.USER_NAME, "");
    }
    public  static  String getTokenNumber(Context context){
        return (String) SPUtils.get(context, SPConstant.TOKEN_NUMBER, "");

    }

    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }
}

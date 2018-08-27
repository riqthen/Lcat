package com.riqthen.library;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by h on 2017/4/26.
 * Update on 2018/8/20
 */
public class Lcat {

    /**
     * 关闭log: tag设置为"none"
     */
    private static final int E = Log.ERROR;      //E(默认)
    private static final int V = Log.VERBOSE;    //V
    private static final int D = Log.DEBUG;      //D
    private static final int I = Log.INFO;       //I
    private static final int W = Log.WARN;       //W

    public static boolean IS_DEBUG = true;      //默认显示log

    public static void v(Object tag, Object... objects) {
        if (objects.length == 0) {
            print(V, 3, "", tag);
        } else {
            print(V, 3, tag.toString().trim(), objects);
        }
    }

    private static void print(int priority, int deep, String tag, Object... objects) {
        if (IS_DEBUG && !tag.equalsIgnoreCase("none")) {
            try {
                String threadName = Thread.currentThread().getName();
                String lineIndicator = getLineIndicator(deep);
                String length = "";
                if (objects.length == 1) {
                    String string;
                    if (objects[0] instanceof Object[]) {  //如果o是数组，即String[]、Integer[]
                        length = "长度:" + ((Object[]) objects[0]).length;
                        string = length + " " + Arrays.toString((Object[]) objects[0]);
                    } else {    //如果o是普通类型
                        if (objects[0] instanceof String) {
                            String s = (String) objects[0];
                            String message;
                            try {
                                if (s.trim().startsWith("{")) {
                                    JSONObject jsonObject = new JSONObject(s);
                                    message = "\n" + jsonObject.toString(4);
                                } else if (s.trim().startsWith("[")) {
                                    JSONArray jsonArray = new JSONArray(s);
                                    message = "\n" + jsonArray.toString(4);
                                } else {
                                    message = s;
                                }
                            } catch (JSONException e) {
                                message = s;
                            }
                            string = message;
                        } else if (objects[0] instanceof List) {
                            length = "长度:" + ((List) objects[0]).size();
                            string = objects[0].toString();
                        } else if (objects[0] instanceof Map) {
                            length = "长度:" + ((Map) objects[0]).size();
                            string = objects[0].toString();
                        } else if (objects[0] instanceof Set) {
                            length = "长度:" + ((Set) objects[0]).size();
                            string = objects[0].toString();
                        } else {
                            string = objects[0].toString();
                        }
                    }
                    Log.println(priority, TextUtils.isEmpty(tag) ? "-" : tag, "Thread: " + threadName + "／" + lineIndicator + tag + " ----> " + length + " " + string);
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Object o : objects) {
                        if (o instanceof Object[]) {
                            sb.append("  ").append(Arrays.toString((Object[]) o));
                        } else {
                            sb.append("  ").append(o.toString());
                        }
                    }
                    Log.println(priority, TextUtils.isEmpty(tag) ? "-" : tag, "Thread: " + threadName + "／" + lineIndicator + tag + " ---->" + sb.toString());
                }
            } catch (Exception e) {
                print(E, 4, "打印错误", e);
            }
        }
    }

    //获取行所在的方法指示
    private static String getLineIndicator(int deep) {
        //2代表方法的调用深度：0-getLineIndicator，1-performPrint，2-print
        StackTraceElement element = ((new Exception()).getStackTrace())[deep];
        String packageName = element.getClassName().substring(0, element.getClassName().lastIndexOf("."));
        return packageName + "／" +
                //                element.getMethodName() + "()／" +
                "(" + element.getFileName() + ":" + element.getLineNumber() + ")：";
    }

    public static void d(Object tag, Object... objects) {
        if (objects.length == 0) {
            print(D, 3, "", tag);
        } else {
            print(D, 3, tag.toString().trim(), objects);
        }
    }

    public static void i(Object tag, Object... objects) {
        if (objects.length == 0) {
            print(I, 3, "", tag);
        } else {
            print(I, 3, tag.toString().trim(), objects);
        }
    }

    public static void w(Object tag, Object... objects) {
        if (objects.length == 0) {
            print(W, 3, "", tag);
        } else {
            print(W, 3, tag.toString().trim(), objects);
        }
    }

    public static void e(Object tag, Object... objects) {
        if (objects.length == 0) {
            print(E, 3, "", tag);
        } else {
            print(E, 3, tag.toString().trim(), objects);
        }
    }
}


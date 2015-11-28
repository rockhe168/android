package com.rock.com.quickstart.business.controller;

/**
 * Created by xhhe on 2015/11/24.
 */
public class BusinessCommonParameter {
    // 当前应用类型
    public static String CURRENT_APP = "";
    // 版本
    public static String VERSION = "612.000";
    // 系统编号为32
    public static String SYSTEMCODE = "32";
    // SSL系统编号为30
    public static String SYSTEMCODE_SSL = "";
    // 语言 01为中文
    public final static String LANGUAGE = "01";
    public final static int SID_JINLI = 8081;
    //中兴手机预装渠道
    public final static int SID_ZHONGXINGYZ = 8096;

    public final static String TELEPHONE_SELF = "4008209662";

    // 自有渠道
    public final static int SID_SELF = 8892;

    // 系统sourceId
    public static String SOURCEID = SID_SELF + "";

    public static int SOURCEID_INT = SID_SELF;

    //5.8 Add 灰度发布版本号 5.8初始为1010 架构沈天乐确认
    public static  int APP_GRAY_RELEASE_NUM = 1100;

    public static final String __defaultClientID = "00000000000000000000";

    private static boolean shakeDevice = false;

    public static synchronized boolean getShakeDevice() {
        return shakeDevice;
    }

    public static synchronized void setShakeDevice(boolean value) {
        shakeDevice = value;
    }

}

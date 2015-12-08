package com.wenjackp.volley.rest.example;

import com.wenjackp.android.lib.utils.EmptyUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/10/10.
 */
public class ConstantUtils {

    private static final String AUTH_CODE = "OnxA/jxTBSM91jGmGyREdSjObIc4Z8d2hA/95UiyOSLBUSTAYHKq75hcxsHuN5VsKCJQqB6QpbSH77xgY9lWTBs0nNajsDLpfBAVdB0bqO+RrbEhCgms7bsfclnY+XFn";

    /**
     * 验证码 登录后需要覆盖
     */
    public static String mAuthCode = AUTH_CODE;

    public static final String ACTION_LOGIN_OUT = "LoginOut";

    /**
     * 获取所有
     */
    public static int GET_ALL = -1;

    /**Http请求成功*/
    public static final int HTTP_REQUEST_SUCCESS = 200;

    /**提示信息 304有效*/
    public static final int HTTP_INFO_MSG = 304;

    /**错误信息 500 有效 发布后关闭*/
    public static final int HTTP_ERROR_MSG = 500;

    /**列表最大长度*/
    public static final int MAX_LIST_COUNT = 100;


    public static final int PHOTO_REQUEST_CAMERA = 10; // 拍照请求码
    public static final int PHOTO_REQUEST_GALLERY = 11; // 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 12; // 裁剪

    public static final int TYPE_STORE_NAME = 1;
    public static final int TYPE_ADDRESS = 2;
    public static final int TYPE_SHOP_NAME = 3;
    public static final int TYPE_SHOP_MOBILE = 4;
    public static final int TYPE_MEMBER_NAME = 5;
    public static final int TYPE_MEMBER_MOBILE = 6;
    public static final int TYPE_PHONE_NUM = 7;
    public static final int TYPE_REMARK = 8;
    public static final int TYPE_EMAIL = 9;
    public static final int TYPE_QQ = 10;
    public static final int TYPE_ADDR_DARA = 123;

    public static final int RUSULT_CODE_EDIT = 1001;

    public static final int EVENTBUS_ADD_CUSTOMER = 601;
    public static final int EVENTBUS_LOGIN_OUT = 602;
    public static final int EVENTBUS_GET_DATA = 603;
    public static final int EVENTBUS_ADDRESS = 604;
    public static final int EVENTBUS_LOGO_UPDATE = 605;


    public static final int EVENT_STATUS_FAVORITES_SCHEME = 2;

    /**检测是否为手机号码*/
    public static final String CHECK_IS_PHONE = "^(13|15|18)\\d{9}$";

    public static final String SHARE_CONTENT = "遇见未来的家，720全景图，方案，预约免费设计";

//    public static boolean requestSuccess(DefaultModel model){
//        if(model.Status == HTTP_REQUEST_SUCCESS){
//            return true;
//        }
//        return false;
//    }

    public static int getListSize(List<?> mDatas){
        return EmptyUtils.emptyOfList(mDatas) ? 0 : mDatas.size();
    }

    public static boolean isListForMax(List<?> mArray,int currentAdapterCount){
        return EmptyUtils.emptyOfList(mArray) || currentAdapterCount >= ConstantUtils.MAX_LIST_COUNT;
    }

    public static String valideNull(String content){
        if(EmptyUtils.emptyOfString(content) || content.equals("null")){
            return "";
        }
        return content;
    }

    public static boolean isPhoneNumber(String input){
        if(EmptyUtils.emptyOfString(input)){
            return false;
        }

        Pattern mPattern = Pattern.compile(CHECK_IS_PHONE);
        Matcher matcher =  mPattern.matcher(input);
        return matcher.matches();
    }

//    public static void registerEvent(Object obj){
//        if(!EventBus.getDefault().isRegistered(obj)){
//            EventBus
//                    .getDefault()
//                    .register(obj);
//        }
//    }
//
//    public static void unRegisteredEvent(Object obj){
//        if(EventBus.getDefault().isRegistered(obj)){
//            EventBus
//                    .getDefault()
//                    .unregister(obj);
//        }
//    }

}

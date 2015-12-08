package com.wenjackp.volley.rest.example;

/**
 * Created by Administrator on 2015/10/10.
 */
public class UrlUtils {

    /**注册门店账号信息*/
    public static final String CreateUser = "ShopManager/CreateUser";

    /**注册门店账号信息*/
    public static final String CommonDeptRegisterFinally = "COMMON/CommonDeptRegisterFinally";

    /**获取全景图列表*/
    public static final String ACTION_GET_QJT_LIST = "ShopManager/GetQJTList";

    public static final  String SEND_MESSAGE = "SMS/SendMessage";

    public static final  String SEND_Verification = "SMS/SendVerification";

    public static final  String MEMBER_CREATE = "MemberInfo/MemberCreate";

    /**通用登录*/
    public static final  String USER_LOGIN = "COMMON/UserLogin";

    /**根据Id获取会员信息*/
    public static final String Get_Member_Info = "MemberInfo/GetMemberInfo";


    /*获取会员的关注数，收藏数，预约数*/
    public static final String Get_MemberInfo_Nume = "MemberInfo/GetMemberInfoNume";

    /*获取店长信息*/
    public static final String GetEmployeeInfo = "ShopManager/GetEmployeeInfo";

    /*上传文件*/
    public static final String PostFile = "COMMON/PostFile";

    /*上传或者修改会员头像*/
    public static final String UploadHeadIco = "MemberInfo/UploadHeadIcoByPath";

    /*会员资料修改*/
    public static final String ModifyMemberInfo= "MemberInfo/MemberInfoModify";



    /*修改登录密码*/
    public static final String ModifyMemberPassword= "COMMON/UserPasswordModify";

    /*修改店长信息*/
    public static final String ModifyEmployeeInfo= "ShopManager/ModifyEmployeeInfo";

    /*找回密码*/
    public static final String GetMemberPwd= "COMMON/GeUserPwd";



    /*获取我关注的设计师*/
    public static final String GetMyEmployeeList= "MemberInfo/GetMyEmployeeList";

    /*获取我关注的店铺或装企*/
    public static final String GetMyLikeDeptList= "MemberInfo/GetMyLikeDeptList";

    /*获取我的客户列表*/
    public static final String GetCustomerList= "ShopManager/GetCustomerList";

    /*获取我的客户详细*/
    public static final String GetCustomerInfo= "ShopManager/GetCustomerInfo";

    /*获取客户收藏方案*/
    public static final String GetCustomerBeCollectionSchemeList= "ShopManager/GetCustomerBeCollectionSchemeList";

    /*获取客户设计方案*/
    public static final String GetCustomerShopMakeSchemeList= "ShopManager/GetCustomerShopMakeSchemeList";


    /*添加或修改客户信息*/
    public static final String EditCustomerInfo= "ShopManager/EditCustomerInfo";

    /*获取店铺信息*/
    public static final String GetDepartmentInfo= "ShopManager/GetDepartmentInfo";

    /*修改店铺信息*/
    public static final String UpdateDepartmentInfo= "ShopManager/UpdateDepartmentInfo";

    /*获取店铺所属行业*/
    public static final String GetSubCategoryListByCode= "COMMON/GetSubCategoryListByCode";

    /**获取消息中心*/
    public static final String GetCommonLogList="COMMON/GetCommonLogList";

    /**获取全国省市区列表*/
    public static final String TK_GetDistrictByParentId="COMMON/TK_GetDistrictByParentId";

    /**意见反馈*/
    public static final String CommonFeedBack="COMMON/CommonFeedBack";





    /**获取方案列表*/
    public static final String ACTION_GET_SHOP_SCHEME_LIST = "designscheme/GetSchemeList";

    /**商家中心首页(获取平台方案,全景图)*/
    public static final String ACTION_GET_PLATEFORM_SCHEME_LIST = "ShopManager/GetPlateFormSchemeList";

    /**获取首页商品列表*/
    public static final String ACTION_GET_PRODUCT_LIST = "Product/List";

    /**设计师收藏*/
    public static final String ACTION_DESIGNER_FAVORITES = "Employee/Favories";

    /**通过方案获取商品列表接口*/
    public static final String ACTION_GET_PRODUCT_BY_SCHEME_ID = "Product/GetProductBySchemeId";

    /**获取方案详细内容*/
    public static final String ACTION_GET_DESIGN_DETAILS = "DesignScheme/GetSchemeDetail";

    /**获取产品信息*/
    public static final String ACTION_GET_PRODUCT = "Product/GetProduct";

    /**获取商家商品列表*/
    public static final String ACTION_GET_SHOP_PRODUCT_LIST = "ShopManager/GetProductList";

    /**获取设计师详细信息*/
    public static final String ACTION_GET_DESIGNER_DETAILS = "Employee/GetEmployee";

    /**获取设计师的设计列表*/
    public static final String ACTION_GET_DESIGNER_DESIGN_LIST = "Employee/GetDesignSchemeList";

    /**获取省市数据*/
    public static final String ACTION_GET_CITY_LIST = "Common/GetDistrictList";

    /**预约设计*/
    public static final String ACTION_BOOKING_DESIGN = "common/MakingAppointment";

    /**获取三维家方案分类*/
    public static final String ACTION_SCHEME_CATEGORY = "DesignScheme/GetSchemeCategory";

    /**获取方案列表(通用版)*/
    public static final String ACTION_GET_SCHEME_LIST = "DesignScheme/GetSchemeList";

    /**添加或修改客户信息(包含客户喜欢的风格CustomerLikeStyle)*/
    public static final String ACTION_EDIT_CUSTOMER_INFO = "ShopManager/EditCustomerInfo";

    /**获取店铺信息*/
    public static final String ACTION_GET_DEPARTMENT_INFO = "ShopManager/GetDepartmentInfo";

    /**获取我的客户信息(即预约我的)*/
    public static final String ACTION_GET_BOOKING_CUSTOMER_LIST = "ShopManager/GetCustomerList";

    /**收藏方案到客户(被动式收藏)*/
    public static final String ACTION_COLLECT_SCHEME_FOR_CUSTOMER = "ShopManager/CollectSchemeForCustomer";
}

package com.example.huoapp.http;

import android.text.TextUtils;
import android.webkit.WebView;

import com.example.huoapp.util.MD5;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by tinle on 2017/11/6.
 */

public class Api {


    public static String getUrl(String apiName){
        return null;
    }

    public static Map<String,String> getCommonHttpParams(Object apiName){
        WeakHashMap<String,String> httpParams = new WeakHashMap<>();
        httpParams.put("app_id", SdkConstant.HS_APPID);
        httpParams.put("client_id", SdkConstant.HS_CLIENTID);
        httpParams.put("from", SdkConstant.FROM);
        long timestamp=System.currentTimeMillis()/1000;
        httpParams.put("timestamp", timestamp+"");
        //MD5(game/gametype+timestamp+client_key)
        httpParams.put("sign", MD5.md5(new StringBuffer(String.valueOf(apiName)).append(timestamp).append(SdkConstant.HS_CLIENTKEY).toString()));
        httpParams.put("agentgame",SdkConstant.HS_AGENT);
        /*if(!TextUtils.isEmpty(LoginControl.getToken())){
            httpParams.put("user_token",LoginControl.getToken());
        }*/

        return httpParams;
    }

    //游戏分类
    public static final String gametypeApi="game/gametype";
    //广告图
    public static final String slideListApi="slide/list";
    public static final String gameListApi ="game/list";
    public static final String gameDetail="game/detail";
    public static final String aboutus="system/aboutus";

    public static final String giftListApi="gift/list";
    public static final String couponListApi="coupon/list";
    public static final String giftDetailApi="gift/detail";
    public static final String couponDetailApi="coupon/detail";
    public static final String searchIndexApi="search/index";
    public static final String ranklistApi="integral/ranklist";
    public static final String hompageApi="homepage";
    public static final String goodsListApi="goods/list";
    public static final String wealNoteApi="weal/note";
    public static final String addressListApi="address/list";
    public static final String userHeadImgApi="user/portrait/update";
    public static final String newsList="news/list";//新闻列表
    public static final String newsDetail="news/webdetail/";//新闻详情，后面要加上id

    //需要加密接口
    public static final String appinit="system/appinit";
    public static final String loginApi="user/login";
    public static final String startupApi="system/startup";
    public static final String registerUserNameApi="user/register";
    public static final String registerMobileApi="user/registermobile";
    public static final String smsSendApi="sms/send";
    public static final String passwordUpdateApi="user/passwd/update";
    public static final String passwordFindApi="user/passwd/find";
    public static final String phoneBindApi ="user/phone/bind";
    public static final String userDetailApi="user/detail";
    public static final String nickNameUpdateApi="user/info/update";
    public static final String addressDetailApi="user/address/detail";
    public static final String userMsgDetailApi="user/msg/detail";
    public static final String userIntegralApi="user/integral/get";
    public static final String getHelpInfo="system/get_help_info";
    public static final String registerEmailApi="user/email/bind";
    public static final String emailCodeSend="user/email/send";
    public static final String emailUnbind="user/email/removebind";
    public static final String loginThird="user/loginoauth";//第三登录

    public static final String addressUpdateApi="user/address/update";
    public static final String userGmlistApi="user/game/gmlist";
    public static final String guestbookWriteApi="guestbook/write";
    public static final String userGiftListApi="user/gift/list";
    public static final String userCouponListApi="user/coupon/list";
    public static final String userGameCouponListApi="user/game/gmlist";//简版app中的游戏币列表
    public static final String userGoodsListApi="user/goods/list";
    public static final String userGiftAddApi="user/gift/add";
    public static final String userGoodsAddApi="user/goods/add";
    public static final String userGetinvlistApi="user/integral/getinvlist";
    public static final String userActlistApi="integral/actlist";
    public static final String userPhoneVerifyApi="user/phone/verify";
    public static final String gameMoneyChargeApi="gamemoney/charge";
    public static final String gamePreorderApi="gamemoney/preorder";
    public static final String gamePayTypeApi ="gamemoney/pay";
    public static final String queryorderApi ="gamemoney/queryorder";
    //领取代金券
    public static final String userCouponAddApi="user/coupon/add";
    //用户消息列表
    public static final String userMsgListApi="user/msg/list";
    //用户消息删除
    public static final String userMsgDeleteApi="user/msg/delete";

    //用户签到列表
    public static final String userSignApi="user/sign/list";
    //用户签到
    public static final String userSignAddApi="user/sign/add";
    public static final String gameDownApi="game/down";
    public static final String tguserDetailApi="tguser/detail";
    public static final String shareNotifyApi="share/notify";
    public static final String shareDetailApi="share/detail";
    public static final String accountInfoApi="user/name/check";
    public static final String sendUserEmailApi="user/email/send";
    public static final String logoutApi="user/logout";

    //平台协议
    public static final String agreementPlatformUrl="agreement/platagreement";
    //注册协议
    public static final String agreementRegisterUrl="agreement/regagreement";
    //注册协议
    public static final String agreementrightUrl="agreement/rightagreement";// 用户协议与版权说明

    public static final String userPTBChargeRecord="user/recharge/rclist";// 平台币充值记录
    public static final String userGameChargeRecord="user/gm/rclist";// 游戏充值记录
    public static final String userSpendRecord="user/consume/cslist";// 消费记录
    public static final String chargeGame="user/wallet/add";// web游戏充值
    public static final String chargePingtaibi="user/ptb/add";// web平台币充值

    public static final String H5_GAME_INDEX_URL="api/v7/web/h5/index";// h5游戏主页

    //http://doc7.huosdk.com/76?page_id=1315 小号交易相关接口
    public static final String dealAccountList="deal/account/list";// 买号列表
    public static final String dealAccountSellList="deal/account/sell_list";// 卖号列表
    public static final String dealAccountCollectList="deal/account/collect_list";// 我的收藏列表
    public static final String dealAccountBuyList="deal/account/buy_list";// 我的已购买列表
    public static final String dealAccountRead="deal/account/read";// 出售账号详情
    public static final String dealAccountAdd="deal/account/add";// 提交卖号信息
    public static final String dealAccountOperationCollect="deal/account/operation_collect";// 角色商品添加\取消收藏
    public static final String dealAccountCancel="deal/account/cancel";// 取消出售
    public static final String dealAccountEdit="deal/account/edit";// 编辑提交卖号信息
    public static final String dealAccountGetGames="deal/account/get_games";// 获取用户的游戏列表
    public static final String dealAccountGetAccounts="deal/account/get_accounts";// 获取游戏的账号列表
    //wap
    public static final String accountBuy="account/buy";// 购买小号支付页面
    public static final String shareRead="share/read";// 分享展示页

    /**
     * 直接加载网页内容数据
     * @param webView
     * @param content
     */
    public static void loadMobileHtmlContent(WebView webView, String content){
        String htmlHead="<!Doctype html><html xmlns=http://www.w3.org/1999/xhtml><head><meta http-equiv=Content-Type content=\"text/html;charset=utf-8\"></head>";
        String htmlTail="</html>";
        StringBuilder htmlSb=new StringBuilder(htmlHead).append(content).append(htmlTail);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        webView.loadData(htmlSb.toString(),"text/html; charset=UTF-8",null);
    }

}

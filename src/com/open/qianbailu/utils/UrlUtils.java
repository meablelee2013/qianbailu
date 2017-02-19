package com.open.qianbailu.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * *****************************************************************************
 * *****************************************************************************
 * ******************
 * 
 * @author :fengguangjing
 * @createTime: 16/11/16
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: 
 *               ****************************************************************
 *               ***************************************************************
 *               *********************************************
 */
public class UrlUtils {
   /** 浏览器代理 **/
	public static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31";
	public static final String tencentAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75 Safari/537.36 QQBrowser/4.1.4132.400";
	public static final String qianbailuAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";
	
	/***m地址**/
	public static final String QIAN_BAI_LU_M = "http://m.222lu.co/";
	/***电影首页**/
	public static final String QIAN_BAI_LU_M_VLIST = "http://m.222lu.co/vlist.php";
	/***电影分页**/
	public static final String QIAN_BAI_LU_M_VLIST_CLASSID = "http://m.222lu.co/vlist.php?classid=1";
	/***图库**/
	public static final String QIAN_BAI_LU_M_VLIST_B = "http://m.222lu.co/blist.php?bclassid=11";
	/***小说**/
	public static final String QIAN_BAI_LU_M_VLIST_S = "http://m.222lu.co/blist.php?bclassid=1";
	/***图库详细**/
	public static final String QIAN_BAI_LU_M_VLIST_B_CLASSID   = "http://m.222lu.co/list.php?page=2&classid=2&bclassid=1";
	/***图片地址**/
	public static final String QIAN_BAI_LU_HTTP = "http:";
	public static final String QIAN_BAI_LU_HTTPS = "https:";
	/***图库详细**/
	public static final String QIAN_BAI_LU_SHOW = "http://m.222lu.co/show.php?id=388511&classid=13";
	/***小说详细**/
	public static final String QIAN_BAI_LU_XIAO_SHUO = "http://m.222lu.co/show.php?id=393114&classid=2";
	/***电影详细**/
	public static final String QIAN_BAI_LU_MOVIE = "http://m.222lu.co/vshow.php?id=11174";
	
	public static final String COOKIE = "__cfduid=df0a47eed3d6f56b0ce2b54928ced11051483189834; CNZZDATA1000003418=380336421-1483185838-%7C1483852222";
	/***地址转换器**/
	public static final String QIAN_BAI_LU_DOWNLOAD = "http://search.cplusplus.me/downconvert.html";
	/***搜索地址**/
	public static final String QIAN_BAI_LU_SEARCH = "http://m.222lu.co/search.php";
	/***搜索例子**/
	public static final String QIAN_BAI_LU_SEARCH_RESULT ="https://1100.so/wap.php?q=东京热";
	/***搜索url拼装**/
	public static final String QIAN_BAI_LU_SEARCH_WAB = "https://1100.so/wap.php";
	
	//网页
	/***HOST地址**/
	public static final String QIAN_BAI_LU_HOST = "2222av.co";
	/***pc地址**/
	public static final String QIAN_BAI_LU = "http://2222av.co";
	/***电影首页**/
	public static final String PC_QIAN_BAI_LU_VLIST = "http://2222av.co/vod/index.html";
	/***电影1**/
	public static final String PC_QIAN_BAI_LU_VLIST_CLASSID ="http://2222av.co/list/1.html";
	/***电影详细**/
	public static final String PC_QIAN_BAI_LU_MOVIE = "http://2222av.co/vod/11193.html";
	/***电影详细vod**/
	public static final String PC_QIAN_BAI_LU_VOD = "http://2222av.co/vod/11400.html#pl";
	/***图库**/
	public static final String PC_QIAN_BAI_LU_M_VLIST_B = "http://2222av.co/html/tupian/index.html";
	/***图库详细**/
	public static final String PC_QIAN_BAI_LU_M_VLIST_B_CLASSID   = "http://2222av.co/html/tupian/toupai/";
	/***看图图库详细**/
	public static final String PC_QIAN_BAI_LU_SHOW = "http://2222av.co/html/tupian/yazhou/2016/0923/388511.html";
	/***小说**/
	public static final String PC_QIAN_BAI_LU_VLIST_S = "http://2222av.co/html/article/index.html";
	/***小说列表**/
	public static final String PC_QIAN_BAI_LU_VLIST_J = "http://2222av.co/html/article/jiqing/index.html";
	/***小说详细**/
	public static final String PC_QIAN_BAI_LU_XIAO_SHUO = "http://2222av.co/html/article/jiqing/2017/0107/393310.html";
	/***电影在线**/
	public static final String PC_QIAN_BAI_LU_ONLINE ="http://2222av.co/list/14.html";
	/***pc搜索地址**/
	public static final String PC_QIAN_BAI_LU_SEARCH = "https://1100.so/";
	/***pc搜索例子**/
	public static final String PC_QIAN_BAI_LU_SEARCH_RESULT ="https://1100.so/?q=%E4%B8%9C%E4%BA%AC%E7%83%AD";
	/***网站公告**/
	public static final String PC_QIAN_BAI_LU_NOTICE = "http://2222av.co/detail/gg.html";
	/***热搜排行**/
	public static final String PC_QIAN_BAI_LU_HOT = "http://2222av.co/detail/hot.html";
	/***看片留言**/
	public static final String PC_QIAN_BAI_LU_GUEST_BOOK = "http://2222av.co/detail/guestbook.html";
	
	public static String getCookie(){
		return COOKIE;
	}
	/**
	 * JsonObjectRequest 请求设置Headers
	 */
	public static Map<String,String> getHeaders(){
	 Map<String, String> headers = new HashMap<String, String>();
	 headers.put("Cookie",
	 "3g_guest_id=-9045538589999304704; cuid=5032023480; sd_userid=27201462782213238; sd_cookie_crttime=1462782213238; eas_sid=y1i4W655K8T8X9U3N3p7C7U2x7; pac_uid=1_624926379; qq_slist_autoplay=on; tvfe_boss_uuid=e776aacde64effb9; h_uid=H01560819fdc; mobileUV=1_158907f70d3_bbd13; ts_refer=enrz.com/fhm/2016/12/02/73248.html; _as_crazy3044487=2016-12-8-2-; pgv_pvi=6908002304; pgv_si=s4405479424; ptui_loginuin=624926379@qq.com; pt2gguin=o0624926379; uin=o0624926379; skey=@4EK94rAAo; ptisp=ctc; RK=CesXfneTOj; luin=o0624926379; lskey=00010000dc8afe64b0ce27f57820dcefd38a902ab9d67698fc42f999b4d492033045767f379c6947e7546ae8; ptcz=c307e47376dee800ee4a82794866f608297b218323a8b12fd611bbd8f75f86b6; login_remember=qq; uid=33415391; _qddaz=QD.h8tbub.d2q19w.iwg3c671; _qddamta_800079372=3-0; _qdda=3-1.4ngq3; _qddab=3-zbc4bc.iwg3c675; ad_play_index=52; _as_crazy3052371=2016-12-8-1-; qv_als=xAYAySR21BA7HIk4A11481185160GHgZLw==; ptag=|v_qq_com; pgv_info=ssid=s8679421312; ts_last=v.qq.com/u/history/; pgv_pvid=6914624368; o_cookie=624926379; ts_uid=3813777356; main_login=qq; encuin=f2caf7e2c580066b6f356522325b0902|624926379; lw_nick=%E5%BE%A1%E5%AE%88|624926379|//q4.qlogo.cn/g?b=qq&k=wSLCLgsnNYsxT924yLUn3Q&s=40&t=663|1");
	 headers.put("Accept","*/*");
	 headers.put("Accept-Encoding","gzip, deflate, sdch");
	 headers.put("Accept-Language","zh-CN,zh;q=0.8");
	 headers.put("Connection","keep-alive");
//	 headers.put("Host","data.video.qq.com");
//	 headers.put("Referer","http://v.qq.com/x/search/?q=%E9%94%A6%E7%BB%A3%E6%9C%AA%E5%A4%AE&stag=101&smartbox_ab=");
//	 headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
	 headers.put("User-Agent",tencentAgent);
	 return headers;
	 }
	
	/**
	 * webview 请求设置Cookies
	 */
	public static String getWebCookies() {
		String cookie = "3g_guest_id=-9045538589999304704; cuid=5032023480; sd_userid=27201462782213238; sd_cookie_crttime=1462782213238; eas_sid=y1i4W655K8T8X9U3N3p7C7U2x7; pac_uid=1_624926379; qq_slist_autoplay=on; tvfe_boss_uuid=e776aacde64effb9; h_uid=H01560819fdc; mobileUV=1_158907f70d3_bbd13; ts_refer=enrz.com/fhm/2016/12/02/73248.html; RK=CesXfneTOj; ptui_loginuin=624926379@qq.com; pt2gguin=o0624926379; luin=o0624926379; lskey=000100000426cf179de687a53cb07fc9f627768a534dd985707ade55845085252bc89239dd583b28feea12e5; ptcz=c307e47376dee800ee4a82794866f608297b218323a8b12fd611bbd8f75f86b6; login_remember=qq; uid=33415391; _qddaz=QD.h8tbub.d2q19w.iwg3c671; _qddab=3-zhwcm3.iwo9s4ym; oversea_limit=0; ptag=|u; main_login=qq; encuin=f2caf7e2c580066b6f356522325b0902|624926379; lw_nick=%E5%BE%A1%E5%AE%88|624926379|//q4.qlogo.cn/g?b=qq&k=wSLCLgsnNYsxT924yLUn3Q&s=40&t=663|1; o_cookie=624926379; pgv_info=ssid=s9656053119; ts_last=v.qq.com/x/cover/avuik2dix9zqv8p.html; pgv_pvid=6914624368; ts_uid=3813777356; qv_als=5WieZfTgwMtJC/ZhA114816976900iuUPw==; ad_play_index=54";
		return cookie;
	}
}

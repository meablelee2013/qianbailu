package com.open.qianbailu.jsoup;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

import com.open.qianbailu.json.m.XiaoShuoJson;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class PCQianBaiLuXiaoShuoService extends CommonService {
	public static final String TAG = PCQianBaiLuXiaoShuoService.class.getSimpleName();

	public static XiaoShuoJson parseXiaoSHuo(String href,int pagerno) {
		XiaoShuoJson mXiaoShuoJson = new XiaoShuoJson();
		try {
			if(pagerno>1){
				//_2.html
				href= href.replace(".html", "")+"_"+pagerno+".html";
			}
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href)
			// .userAgent(UrlUtils.qianbailuAgent)
					.timeout(10000).get();
			// Log.i(TAG, doc.toString());
			// System.out.println(doc.toString());

			/**
<div class="pn_news">
<ul>
<li><em>按←键进入上一撸：<a id="pre" href="/html/article/jiqing/2017/0107/393309.html">调教美妇之姑侄寻欢</a></em></li>
<li><em>按→键进入下一撸：<a id="next" href="/html/article/jiqing/2017/0110/393425.html">办公室横着走的女人</a></em></li></ul>
  </div>

			 */
			try {
				Element ljTitleElement = doc.select("div.pn_news").first();
				if (ljTitleElement != null) {
					Element aElement = ljTitleElement.select("a").first();
					if (aElement != null) {
						mXiaoShuoJson.setPreHref(UrlUtils.QIAN_BAI_LU + aElement.attr("href"));
						mXiaoShuoJson.setPreTitle("按←键进入上一撸：" + aElement.text());
					} else {
						mXiaoShuoJson.setPreTitle(ljTitleElement.text());
					}
					
					try {
						Element aElement1 = ljTitleElement.select("a").get(1);
						if (aElement1 != null) {
							mXiaoShuoJson.setNextHref(UrlUtils.QIAN_BAI_LU + aElement1.attr("href"));
							mXiaoShuoJson.setNextTitle("按→键进入下一撸：" + aElement1.text());
						} else {
							mXiaoShuoJson.setNextTitle(ljTitleElement.text());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			 

			Element divElement = doc.select("div.art_box").first();
			if (divElement != null) {

				/**
				<div class="art_box">
      <h2><a href="/">千百撸</a>&nbsp;&nbsp;&raquo;&nbsp;&nbsp;
      <a href="/index.html">首页</a>&nbsp;>&nbsp;<a href="/html/article/">小说</a>&nbsp;>&nbsp;
      <a href="/html/article/jiqing/">激情小说</a>   理力者THE MINDER</h2>
      <div class="artbody imgbody"> 

				 */
				try {
					mXiaoShuoJson.setNewsTitle(divElement.select("h2").first().text());
					mXiaoShuoJson.setNeswTime("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Element artbodylement = doc.select("div.artbody").first();
			try {
				mXiaoShuoJson.setDetailText(artbodylement.select("p").first().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mXiaoShuoJson;
	}
}

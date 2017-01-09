package com.open.qianbailu.jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.json.m.ShowJson;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class PCQianBaiLuShowImageService extends CommonService {
	public static final String TAG = PCQianBaiLuShowImageService.class.getSimpleName();

	public static ShowJson parsePicture(String href) {
		ShowJson mShowJson = new ShowJson();
		List<ShowBean> list = new ArrayList<ShowBean>();
		try {
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
			 * <div class="ljTitle">上一篇:<a href='show.php?id=388506&classid=13' style='color:#6f6f6f;'>割り切りバイト ささの遥[22P]</a></div>
			<div class="ljTitle">下一篇:很抱歉已经没有了</div>
			 */
			try {
				Element ljTitleElement = doc.select("div.pn_news").first();
				if(ljTitleElement!=null){
					Element aElement  = ljTitleElement.select("a").first();
					if(aElement!=null){
						mShowJson.setPreHref(UrlUtils.QIAN_BAI_LU+aElement.attr("href"));
						mShowJson.setPreTitle("按←键进入上一撸："+aElement.text());
					}else{
						mShowJson.setPreTitle(ljTitleElement.text());
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			
			Element divElement = doc.select("div.art_box").first();
			if (divElement != null) {
				try {
					mShowJson.setNewsTitle(divElement.select("h2").first().text());
					mShowJson.setNeswTime("");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				Elements imgElements = divElement.select("img");
				if (imgElements != null && imgElements.size() > 0) {
					ShowBean movieBean;
					for (int j = 0; j < imgElements.size(); j++) {
						try {
							movieBean = new ShowBean();
							try {
								// <img src="//i1.1100lu.xyz/1100/201604/19/puwcfmvpoqg.jpg" alt=""/></p><p>
								Element aElement = imgElements.get(j).select("img").first();
								if (aElement != null) {
									try {
										String src = UrlUtils.QIAN_BAI_LU_HTTP+aElement.attr("src");
										src = src.replace("http://i1.1100lu.xyz", "http://mi1.100av.org/m").replace("http://i2.1100lu.xyz", "http://mi2.100av.org/m");
										movieBean.setSrc(src);
										String title = aElement.attr("alt");
										Log.i(TAG, "j==" + j + ";src==" + src + ";title==" + title);
										movieBean.setAlt(title);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							list.add(movieBean);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		mShowJson.setList(list);
		return mShowJson;
	}
}

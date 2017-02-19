package com.open.qianbailu.jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.qianbailu.bean.m.MovieBean;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class PCQianBaiLuNewService extends CommonService {
	public static final String TAG = PCQianBaiLuNewService.class.getSimpleName();
	public static int maxpageno;
	
	public static List<MovieBean> parsePicture(String href ) {
		List<MovieBean> list = new ArrayList<MovieBean>();
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
//			System.out.println(doc.toString());
			 
			/**
		 <li><span><font color="red">02-18</font></span><em>
		 <a href="/vod/11400.html">亚洲情色</a></em><i class="on">1</i>
		 <a href="/vod/11400.html" target="_blank"
		  title="最新pacopacomama 021617_027 阿姨进一步能成为天气~[古川澄江]">
		 最新pacopacomama 021617_027 阿姨进一步能成为天气~[古川</a></li>
			 */
			Element divElement = doc.select("div.hotbox ").first();
			if (divElement != null  ) {
					Elements aElements = divElement.select("li");
					if (aElements != null && aElements.size() > 0) {
						try {
							MovieBean movieBean;
							for (int i = 0; i < aElements.size(); i++) {
								movieBean = new MovieBean();
								try {
									Element aElement = aElements.get(i).select("a").first();
									if (aElement != null) {
										try {
											String hrefa = aElement.attr("href");
											Log.i(TAG, "i==" + i + ";hrefa==" + hrefa );
											movieBean.setLinkurl(UrlUtils.QIAN_BAI_LU+hrefa);
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										try {
											String title = aElements.get(i).toString();
											Log.i(TAG, "i==" + i +  ";title=="+title);
											movieBean.setTitle(title);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								list.add(movieBean);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

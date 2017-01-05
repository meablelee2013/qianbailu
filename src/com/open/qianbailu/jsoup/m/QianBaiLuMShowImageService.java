package com.open.qianbailu.jsoup.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class QianBaiLuMShowImageService extends CommonService {
	public static final String TAG = QianBaiLuMShowImageService.class.getSimpleName();

	public static List<ShowBean> parsePicture(String href) {
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
			 */
			Element divElement = doc.select("div.detailText").first();
			if (divElement != null) {
				Elements imgElements = divElement.select("img");
				if (imgElements != null && imgElements.size() > 0) {
					ShowBean movieBean;
					for (int j = 0; j < imgElements.size(); j++) {
						try {
							movieBean = new ShowBean();
							try {
								Element aElement = imgElements.get(j).select("img").first();
								if (aElement != null) {
									try {
										String src = aElement.attr("src");
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
		return list;
	}
}

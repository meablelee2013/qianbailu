package com.open.qianbailu.jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.bean.m.NavMChildBean;
import com.open.qianbailu.bean.m.PicKuFilmBean;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class PCQianBaiLuBIndicatorService extends CommonService {
	public static final String TAG = PCQianBaiLuBIndicatorService.class.getSimpleName();

	public static List<NavMBean> parseMHome(String href, int type) {
		List<NavMBean> list = new ArrayList<NavMBean>();
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
			try {
				Elements moduleElements = doc.select("div.hotbox");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						NavMBean navbean = new NavMBean();
						try {
							// <div class="title"><a
							// href="/html/tupian/toupai/">偷拍自拍</a></div>
							// <p class="bti"><s>排名</s><em>名称</em><i>时间</i></p>

							Element titleLeftElement = moduleElements.get(i).select("div.title").first();
							if (titleLeftElement != null) {
								String titlea = titleLeftElement.text();
								Log.i(TAG, "i==" + i + ";titlea==" + titlea);
								navbean.setTitle(titlea);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						// <li><span>10-28</span><i class="on">1</i>
						// <a href="/html/tupian/toupai/2016/1028/390433.html"
						// target="_blank">最近网络疯传萝莉小奶</a></li>

						Elements listElements = moduleElements.get(i).select("li");
						if (listElements != null && listElements.size() > 0) {
							List<NavMChildBean> listc = new ArrayList<NavMChildBean>();
							NavMChildBean childbean;
							for (int j = 0; j < listElements.size(); j++) {
								childbean = new NavMChildBean();
								try {
									Element aElement = listElements.get(j).select("a").first();
									String hrefa = aElement.attr("href");
									childbean.setType(type);
									childbean.setHref(UrlUtils.QIAN_BAI_LU_M + hrefa);
									Log.i(TAG, "i==" + i + ";j==" + j + ";ahref==" + hrefa);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								try {
									String s = "";
									String ion = "";
									try {
										  s =listElements.get(j).select("span").first().text();
										  ion =listElements.get(j).select("i.on").first().text();
									} catch (Exception e) {
										 e.printStackTrace();
									}
								
									Element aElement = listElements.get(j).select("a").first();
									String title = aElement.text();
									childbean.setTitle(" "+s+"          "+ion+"          "+title);
									Log.i(TAG, "i==" + i + ";j==" + j + ";title==" + title);
								} catch (Exception e) {
									e.printStackTrace();
								}

								listc.add(childbean);
							}
							navbean.setList(listc);
						}

						list.add(navbean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

package com.open.qianbailu.jsoup.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class QianBaiLuMNavService extends CommonService {
	public static final String TAG = QianBaiLuMNavService.class.getSimpleName();

	public static List<NavMBean> parseMNav(String href) {
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
			Log.i(TAG, doc.toString());
			try {
				/**
				 * <div class="nav float" style="height:auto"> 01-02
				 * 12:02:37.130: I/QianBaiLuMNavService(32655):
				 * <ul class="nav_ul">
				 * 01-02 12:02:37.130: I/QianBaiLuMNavService(32655):
				 * <li><a href="vlist.php">电影</a></li>
				 * 01-02 12:02:37.130: I/QianBaiLuMNavService(32655):
				 * <li><a href="blist.php?bclassid=11">图库</a></li>
				 * 01-02 12:02:37.130: I/QianBaiLuMNavService(32655):
				 * <li><a href="blist.php?bclassid=1">小说</a></li>
				 * 01-02 12:02:37.130: I/QianBaiLuMNavService(32655):
				 * </ul>
				 * 01-02 12:02:37.130: I/QianBaiLuMNavService(32655): </div>
				 */
				NavMBean navbean = new NavMBean();
				navbean.setTitle("首页");
				navbean.setHref(UrlUtils.QIAN_BAI_LU_M);
				list.add(navbean);
				
				Element ulElement = doc.select("ul.nav_ul").first();
				if (ulElement != null) {
					Elements liElements = ulElement.select("li");
					if (liElements != null && liElements.size() > 0) {
						for (int i = 0; i < liElements.size(); i++) {
							navbean = new NavMBean();
							Element aElement = liElements.get(i).select("a")
									.first();
							try {
								if (aElement != null) {
									String hrefa = aElement.attr("href");
									String titlea = aElement.text();
									Log.i(TAG, "i=="+i+";hrefa=="+hrefa+";titlea=="+titlea);
									navbean.setTitle(titlea);
									navbean.setHref(hrefa);
									list.add(navbean);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
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

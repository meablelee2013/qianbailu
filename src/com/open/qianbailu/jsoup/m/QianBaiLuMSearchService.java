package com.open.qianbailu.jsoup.m;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.qianbailu.bean.SearchBean;
import com.open.qianbailu.jsoup.CommonService;

public class QianBaiLuMSearchService extends CommonService {
	public static final String TAG = QianBaiLuMSearchService.class.getSimpleName();

	public static ArrayList<SearchBean> parseSearchHot(String href ) {
		ArrayList<SearchBean> list = new ArrayList<SearchBean>();
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
			Element ulElement = doc.select("ul.searchpages").first();
			/**
			 * <li><a href=
			 * "https://1100.so/wap.php?q=%E4%B8%9C%E4%BA%AC%E7%83%AD&type=3"
			 * style="clolr:blue;" class="yes">东京热</a></li><li><a href=
			 * "https://1100.so/wap.php?q=%E4%B8%80%E6%9C%AC%E9%81%93&type=3"
			 * style="clolr:blue;" class="yes">一本道</a></li><li><a
			 * href="https://1100.so/wap.php?q=3P&type=2" style="clolr:blue;"
			 * class="yes">3P</a></li><li><a href=
			 * "https://1100.so/wap.php?q=%E5%B0%8F%E5%A7%A8%E5%AD%90&type=1"
			 * style="clolr:blue;" class="yes">小姨子</a></li> </ul> </div>
			 */
			Elements liElements = ulElement.select("li");
			if (liElements != null && liElements.size() > 0) {
				try {
					SearchBean sBean;
					for (int i = 0; i < liElements.size(); i++) {
						sBean = new SearchBean();
						try {
							Element aElement = liElements.get(i).select("a").first();
							if (aElement != null) {
								try {
									String hrefa = aElement.attr("href");
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
									sBean.setHref(hrefa);
									sBean.setTitle(aElement.text());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						list.add(sBean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<SearchBean> parseSearchResult(String href ,int pagerno) {
		ArrayList<SearchBean> list = new ArrayList<SearchBean>();
		try {
			if(pagerno>1){
				href = href  +"&p="+pagerno;
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
			//<div class="moduleCont"><div class="list">
			//<a href=http://m.100av.us/vshow.php?id=11171>[01-05]最新<span style='color:red'>东京热</span>
			//Tokyo Hot n1213 W姦 平...</a></div></div>
			Elements liElements = doc.select("div.moduleCont");
			if (liElements != null && liElements.size() > 0) {
				try {
					SearchBean sBean;
					for (int i = 0; i < liElements.size(); i++) {
						sBean = new SearchBean();
						try {
							Element aElement = liElements.get(i).select("a").first();
							if (aElement != null) {
								try {
									String hrefa = aElement.attr("href");
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
									sBean.setHref(hrefa);
									sBean.setSeq(i);
									sBean.setTitle(aElement.text().replace("<span style='color:red'>", "").replace("</span>", ""));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						list.add(sBean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

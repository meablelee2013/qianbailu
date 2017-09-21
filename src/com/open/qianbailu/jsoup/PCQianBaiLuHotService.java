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

public class PCQianBaiLuHotService extends CommonService {
	public static final String TAG = PCQianBaiLuHotService.class.getSimpleName();

	public static List<NavMBean> parseHot(String href ) {
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
			/**
			 * <div class="hotbox">
        <div class="title"><a href="/list/1.html">亚洲情色</a></div>
        <p class="bti"><s>排名</s><em>名称</em><i>撸量</i></p>
        <ul>
        <li><span>909699</span><i class="on">1</i>
        <a href="/vod/11076.html" target="_blank">最新HEYZO 1353 他人妻味</a></li>
        <li><span>903398</span><i class="on">2</i><a href="/vod/11075.html" target="_blank">最新东京热 Tokyo Hot</a></li><li><span>844365</span><i class="on">3</i><a href="/vod/10996.html" target="_blank">最新HEYZO 1342 他人妻味</a></li><li><span>688513</span><i class="on">4</i><a href="/vod/11008.html" target="_blank">最新一本道 121316_44</a></li><li><span>620759</span><i class="on">5</i><a href="/vod/11054.html" target="_blank">最新HEYZO 1361 1号女主</a></li><li><span>580811</span><i class="on">6</i><a href="/vod/11127.html" target="_blank">最新HEYZO 1357 続生中~</a></li><li><span>579368</span><i class="on">7</i><a href="/vod/11052.html" target="_blank">最新HEYZO 1350 突然!同</a></li><li><span>548169</span><i class="on">8</i><a href="/vod/11110.html" target="_blank">最新一本道 122416_45</a></li><li><span>505129</span><i class="on">9</i><a href="/vod/11018.html" target="_blank">最新东京热 Tokyo Hot</a></li><li><span>491599</span><i class="on">10</i><a href="/vod/11112.html" target="_blank">最新加勒比 122416-33</a></li>        </ul>
        <p class="more"><a href="/list/1.html">查看更多>></a></p>
</div>

			 */
			try {
				Elements moduleElements = doc.select("div.hotbox");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						NavMBean navbean = new NavMBean();
						try {
							Element titleLeftElement = moduleElements.get(i).select("div.title").first();
							if (titleLeftElement != null) {
								String titlea = titleLeftElement.text();
								Log.i(TAG, "i==" + i + ";titlea==" + titlea);
								navbean.setTitle(titlea);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						//<li><span>909699</span><i class="on">1</i>
				        //<a href="/vod/11076.html" target="_blank">最新HEYZO 1353 他人妻味</a></li>
						Elements listElements = moduleElements.get(i).select("li");
						if (listElements != null && listElements.size() > 0) {
							List<NavMChildBean> listc = new ArrayList<NavMChildBean>();
							NavMChildBean childbean;
							for (int j = 0; j < listElements.size(); j++) {
								childbean = new NavMChildBean();
								childbean.setType(6);
								try {
									Element aElement = listElements.get(j).select("a").first();
									String hrefa = aElement.attr("href");
									childbean.setHref(UrlUtils.QIAN_BAI_LU + hrefa);
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

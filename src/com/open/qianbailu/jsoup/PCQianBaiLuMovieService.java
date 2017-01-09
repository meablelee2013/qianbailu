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

public class PCQianBaiLuMovieService extends CommonService {
	public static final String TAG = PCQianBaiLuMovieService.class.getSimpleName();

	public static List<MovieBean> parseMovie(String href, int pagerno) {
		List<MovieBean> list = new ArrayList<MovieBean>();
		try {
			if (pagerno > 1) {
				href = href.replace(".html", "") + "-" + pagerno+".html";
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
			 * <li>
			 * <a href="/vod/11193.html"
			 * title="最新东京热 Tokyo Hot k1417 饵食牝~[萩原果歩]" target="_blank"
			 * class="p"> <img
			 * src="//i3.1100lu.xyz/vod/2017-01-07/5870ab16ea270.jpg"
			 * alt="最新东京热 Tokyo Hot k1417 饵食牝~[萩原果歩]" /></a> <div class="info">
			 * <h2><a href="/vod/11193.html"
			 * title="最新东京热 Tokyo Hot k1417 饵食牝~[萩原果歩]" target="_blank">最新东京热
			 * Tokyo </a><em></em></h2>
			 * <p>
			 * <i>更新：2017-01-07</i>
			 * </p>
			 * <p>
			 * <i>类型：亚洲情色</i>
			 * </p>
			 * <p>
			 * <i>撸量：0</i>
			 * </p>
			 * <span><a href="/vod/11193.html#kan" target="_blank">观看</a><a
			 * href="/vod/11193.html#down" target="_blank">下载</a></span></div></li>
			 */
			Element divElement = doc.select("ul.mlist").first();
			Elements divElements = divElement.select("li");
			if (divElements != null && divElements.size() > 0) {
				try {
					MovieBean movieBean;
					for (int i = 0; i < divElements.size(); i++) {
						movieBean = new MovieBean();
						try {
							String hrefa = divElements.get(i).select("a").first().attr("href");
							Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
							movieBean.setLinkurl(UrlUtils.QIAN_BAI_LU + hrefa);
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							String src = divElements.get(i).select("img").first().attr("src");
							Log.i(TAG, "i==" + i + ";src==" + src);
							movieBean.setThumb(UrlUtils.QIAN_BAI_LU_HTTP + src);
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							String movieTitle = divElements.get(i).select("a").first().attr("title");
							Log.i(TAG, "i==" + i + ";movieTitle==" + movieTitle);
							movieBean.setTitle(movieTitle);
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
								try {
									/**
									 * <p>
								 * <i>更新：2017-01-07</i>
								 * </p>
								 * <p>
								 * <i>类型：亚洲情色</i>
								 * </p>
								 * <p>
								 * <i>撸量：0</i>
								 * </p>
									 */
									String type = divElements.get(i).select("p").first().text().replace("<i>", "").replace("</i>", "");
									Log.i(TAG, "i==" + i + ";type==" + type);
									movieBean.setVmtype(type);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									String time = divElements.get(i).select("p").get(1).text().replace("<i>", "").replace("</i>", "");
									Log.i(TAG, "i==" + i + ";time==" + time);
									movieBean.setProduceyear(time);
								} catch (Exception e) {
									e.printStackTrace();
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

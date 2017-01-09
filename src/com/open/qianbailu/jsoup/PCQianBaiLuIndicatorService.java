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

public class PCQianBaiLuIndicatorService extends CommonService {
	public static final String TAG = PCQianBaiLuIndicatorService.class.getSimpleName();

	public static List<NavMBean> parseMNav(String href,String title) {
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
			// System.out.println(doc.toString());
			try {
				/**
				 * <div class="navmenu dy">
				 * <ul>
				 * <li class="homea"><a class="sdy">电影</a></li>
				 * <li class="item"><a href="/vod/index.html" title="电影"
				 * target="_blank">电影首页</a></li>
				 * <li class="item"><a href="/list/1.html" title="亚洲情色"
				 * target="_blank">亚洲情色</a></li>
				 * <li class="item"><a href="/list/2.html" title="制服丝袜"
				 * target="_blank">制服丝袜</a></li>
				 * <li class="item"><a href="/list/3.html" title="欧美性爱"
				 * target="_blank">欧美性爱</a></li>
				 * <li class="item"><a href="/list/4.html" title="网友自拍"
				 * target="_blank">网友自拍</a></li>
				 * <li class="item"><a href="/list/5.html" title="经典三级"
				 * target="_blank">经典三级</a></li>
				 * <li class="item"><a href="/list/6.html" title="乱伦虐待"
				 * target="_blank">乱伦虐待</a></li>
				 * <li class="item"><a href="/list/7.html" title="另类变态"
				 * target="_blank">另类变态</a></li>
				 * <li class="item"><a href="/list/8.html" title="成人动漫"
				 * target="_blank">成人动漫</a></li>
				 * <li class="item"><a href="/list/14.html" title="在线视频"
				 * target="_blank">在线视频</a></li>
				 * </ul>
				 * </div>
				 */
				NavMBean navbean;
				Elements divElements = doc.select("div.navmenu");
				if (divElements != null && divElements.size() > 0) {
					for (int j = 0; j < divElements.size(); j++) {
						Element liElement = divElements.get(j).select("li").first();
						if(liElement!=null){
							if(liElement.select("a").first().text().equals(title)){
								Elements liElements = divElements.get(j).select("li");
								if (liElements != null && liElements.size() > 0) {
									for (int i = 1; i < liElements.size(); i++) {
										navbean = new NavMBean();
										Element aElement = liElements.get(i).select("a").first();
										try {
											if (aElement != null) {
												String hrefa = aElement.attr("href");
												Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
												navbean.setHref(UrlUtils.QIAN_BAI_LU + hrefa);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}

										try {
											if (aElement != null) {
												String titlea = aElement.text();
												Log.i(TAG, "i==" + i + ";titlea==" + titlea);
												navbean.setTitle(titlea);
												list.add(navbean);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
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

	public static List<NavMBean> parseMHome(String href) {
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
				Elements moduleElements = doc.select("div.moduleTitle");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						NavMBean navbean = new NavMBean();
						try {
							Element titleLeftElement = moduleElements.get(i).select("div.titleLeft").first();
							if (titleLeftElement != null) {
								String titlea = titleLeftElement.text();
								Log.i(TAG, "i==" + i + ";titlea==" + titlea);
								navbean.setTitle(titlea);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element linkElement = moduleElements.get(i).select("div.link").first();
							if (linkElement != null) {
								String hrefa = linkElement.select("a").first().attr("href");
								Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
								navbean.setHref(UrlUtils.QIAN_BAI_LU_M + hrefa);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						// movieHome
						Elements picKuFilmElements = moduleElements.get(i).parent().select("div.movieHome");
						if (picKuFilmElements != null && picKuFilmElements.size() > 0) {
							List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
							PicKuFilmBean filmbean;
							for (int y = 0; y < picKuFilmElements.size(); y++) {
								filmbean = new PicKuFilmBean();
								try {
									/**
									 * <a href="vshow.php?id=11137"> 01-03
									 * 10:18:05.736: I/System.out(11697): <div
									 * class="movieHome"> 01-03 10:18:05.736:
									 * I/System.out(11697): <img src=
									 * "//mi3.1100lu.xyz/m/vod/2016-12-31/586697fc8645a.jpg"
									 * > 01-03 10:18:05.736:
									 * I/System.out(11697): <div
									 * class="movieCover"> 01-03 10:18:05.736:
									 * I/System.out(11697): 最新pacopacomama
									 * 122816_232 露 01-03 10:18:05.736:
									 * I/System.out(11697): </div> 01-03
									 * 10:18:05.736: I/System.out(11697): </div>
									 * </a>
									 */
									Element imgElement = picKuFilmElements.get(y).select("img").first();
									String src = imgElement.attr("src");
									filmbean.setSrc(UrlUtils.QIAN_BAI_LU_HTTP + src);
									Log.i(TAG, "i==" + i + ";y==" + y + ";src==" + src);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element aElement = picKuFilmElements.get(y).select("div.movieCover").first();
									String picTitle = aElement.text();
									filmbean.setTitle(picTitle);
									Log.i(TAG, "i==" + i + ";y==" + y + ";picTitle==" + picTitle);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element aElement = picKuFilmElements.get(y).parent();
									String ahref = aElement.attr("href");
									filmbean.setHref(UrlUtils.QIAN_BAI_LU_M + ahref);
									Log.i(TAG, "i==" + i + ";y==" + y + ";ahref==" + ahref);
								} catch (Exception e) {
									e.printStackTrace();
								}
								picKuL.add(filmbean);
							}
							navbean.setPicKuL(picKuL);
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

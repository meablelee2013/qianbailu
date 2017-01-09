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
				Elements moduleElements = doc.select("div.box");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						NavMBean navbean = new NavMBean();
						try {
							// <h2><a href="#" target="_self">亚洲情色</a></h2>
					          //<span><a href="#">共<i>6254</i>部影片>></a></span></div>

							Element titleLeftElement = moduleElements.get(i).select("h2").first();
							if (titleLeftElement != null) {
								String titlea = titleLeftElement.text();
								Log.i(TAG, "i==" + i + ";titlea==" + titlea);
								navbean.setTitle(titlea);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						// movieHome
						Element ulElement=  moduleElements.get(i).select("ul.img-list").first();
						Elements picKuFilmElements = ulElement.select("li");
						if (picKuFilmElements != null && picKuFilmElements.size() > 0) {
							List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
							PicKuFilmBean filmbean;
							for (int y = 0; y < picKuFilmElements.size(); y++) {
								filmbean = new PicKuFilmBean();
								filmbean.setType(1);
								try {
									/**
									<li>
<a class="play-img" href="/vod/11193.html" title="最新东京热 Tokyo Hot k1417 饵食牝~[萩原果歩]" target="_blank">
<img src="//i3.1100lu.xyz/vod/2017-01-07/5870ab16ea270.jpg" alt="最新东京热 Tokyo Hot k1417 饵食牝~[萩原果歩]" /><em>
<font color="red">01-07</font></em></a>
<b><a href="/vod/11193.html" title="最新东京热 Tokyo Hot k1417 饵食牝~[萩原果歩]" target="_blank">最新东京热 </a></b>
<p>0撸量</p>
</li>
									 */
									Element imgElement = picKuFilmElements.get(y).select("img").first();
									String src = imgElement.attr("src");
									filmbean.setSrc(UrlUtils.QIAN_BAI_LU_HTTP + src);
									Log.i(TAG, "i==" + i + ";y==" + y + ";src==" + src);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element aElement = picKuFilmElements.get(y).select("img").first();
									String picTitle = aElement.attr("alt");
									filmbean.setTitle(picTitle);
									Log.i(TAG, "i==" + i + ";y==" + y + ";picTitle==" + picTitle);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element aElement = picKuFilmElements.get(y).select("a").first();
									String ahref = aElement.attr("href");
									filmbean.setHref(UrlUtils.QIAN_BAI_LU + ahref);
									Log.i(TAG, "i==" + i + ";y==" + y + ";ahref==" + ahref);
								} catch (Exception e) {
									e.printStackTrace();
								}
								picKuL.add(filmbean);
							}
							navbean.setPicKuL(picKuL);
						}

						//链接
						/**
						 * <div class="shot">
          <h3><a href="#" target="_blank">制服丝袜推荐榜</a></h3>
          <ul>
<li><i>1</i><a href="/vod/11195.html" target="_blank">最新加勒比 010417</a><span><font color="red">01-07</font></span></li>
<li><i>2</i><a href="/vod/11194.html" target="_blank">最新天然素人 01051</a><span><font color="red">01-07</font></span></li><li><i>3</i><a href="/vod/11186.html" target="_blank">最新一本道 010417</a><span><font color="red">01-07</font></span></li><li><i>4</i><a href="/vod/11183.html" target="_blank">最新天然素人 01041</a><span><font color="red">01-07</font></span></li><li><i>5</i><a href="/vod/11171.html" target="_blank">最新东京热 Tokyo </a><span>01-05</span></li><li><i>6</i><a href="/vod/11168.html" target="_blank">最新HEYZO 1366 拟</a><span>01-05</span></li><li><i>7</i><a href="/vod/11166.html" target="_blank">最新加勒比 010117</a><span>01-05</span></li><li><i>8</i><a href="/vod/11165.html" target="_blank">最新HEYZO 1365 初</a><span>01-05</span></li><li><i>9</i><a href="/vod/11162.html" target="_blank">最新一本道 010117</a><span>01-05</span></li><li><i>10</i><a href="/vod/11158.html" target="_blank">最新HEYZO 1363 先</a><span>01-05</span></li><li><i>11</i><a href="/vod/11157.html" target="_blank">最新pacopacomama</a><span>01-05</span></li><li><i>12</i><a href="/vod/11153.html" target="_blank">最新东京热 Tokyo </a><span>01-05</span></li><li><i>13</i><a href="/vod/11138.html" target="_blank">最新一本道 123016</a><span>01-05</span></li><li><i>14</i><a href="/vod/11121.html" target="_blank">最新加勒比 122616</a><span>12-31</span></li>          </ul>
        </div>
        <!--/shot-->

						 */
						try {
							Element shotElement = moduleElements.get(i).select("div.shot").first();
							if(shotElement!=null){
								Elements liElements = shotElement.select("li");
								if(liElements!=null && liElements.size()>0){
									List<NavMChildBean> listc = new ArrayList<NavMChildBean>();
									NavMChildBean cbean;
									for(int k=0;k<liElements.size();k++){
										cbean = new NavMChildBean();
										try {
											Element aElement = liElements.get(k).select("a").first();
											String ahref = aElement.attr("href");
											cbean.setHref(UrlUtils.QIAN_BAI_LU + ahref);
											Log.i(TAG, "i==" + i + ";k==" + k + ";ahref==" + ahref);
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											Element aElement = liElements.get(k).select("a").first();
											String atitle = aElement.text();
											cbean.setTitle(atitle);
											Log.i(TAG, "i==" + i + ";k==" + k + ";atitle==" + atitle);
										} catch (Exception e) {
											e.printStackTrace();
										}
										listc.add(cbean);
									}
									navbean.setList(listc);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
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

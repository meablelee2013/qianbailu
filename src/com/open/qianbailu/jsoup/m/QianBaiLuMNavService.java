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
import com.open.qianbailu.bean.m.NavMChildBean;
import com.open.qianbailu.bean.m.PicKuFilmBean;
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
//			Log.i(TAG, doc.toString());
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
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa
											+ ";titlea==" + titlea);
									navbean.setTitle(titlea);
									navbean.setHref(UrlUtils.QIAN_BAI_LU_M+hrefa);
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
//			Log.i(TAG, doc.toString());
//			System.out.println(doc.toString());
			try {
				Elements moduleElements = doc.select("div.moduleTitle");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						NavMBean navbean = new NavMBean();
						try {
							Element titleLeftElement = moduleElements.get(i).select("div.titleLeft")
								.first();
							if (titleLeftElement != null) {
								String titlea = titleLeftElement.text();
								Log.i(TAG, "i==" + i    
										+ ";titlea==" + titlea);
								navbean.setTitle(titlea);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						try {
							Element linkElement = moduleElements.get(i).select("div.link")
								.first();
							if (linkElement != null) {
								String hrefa = linkElement.select("a").first().attr("href");
								Log.i(TAG, "i==" + i + ";hrefa==" + hrefa
										 );
								navbean.setHref(UrlUtils.QIAN_BAI_LU_M+hrefa);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//picKuFilm
						Elements picKuFilmElements = moduleElements.get(i).nextElementSibling().select("div.picKuFilm");
						if(picKuFilmElements!=null && picKuFilmElements.size()>0){
							List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
							PicKuFilmBean filmbean;
							for(int y=0;y<picKuFilmElements.size();y++){
								filmbean = new PicKuFilmBean();
								try {
									/**
									 *  <div class="picKuFilm"> 
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):          <a href="vshow.php?id=10241"> <img src="//mi3.1100lu.xyz/m/vod/2016-09-02/57c85f28a92e8.jpg"> </a> 
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):         </div> 
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):         <a href="vshow.php?id=10241" class="picTitle">最新加勒比 083116-244 ルナ玩交友游戏</a> 
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):        </div> 
									 */
									Element imgElement = picKuFilmElements.get(y).select("img").first();
									String src = imgElement.attr("src");
									filmbean.setSrc(UrlUtils.QIAN_BAI_LU_HTTP+src);
									Log.i(TAG, "i=="+i+";y=="+y+";src=="+src);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								try {
									Element aElement = picKuFilmElements.get(y).nextElementSibling().select("a.picTitle").first();
									String picTitle = aElement.text();
									String ahref = aElement.attr("href");
									filmbean.setTitle(picTitle);
									filmbean.setHref(UrlUtils.QIAN_BAI_LU_M+ahref);
									Log.i(TAG, "i=="+i+";y=="+y+";picTitle=="+picTitle+";ahref=="+ahref);
								} catch (Exception e) {
									e.printStackTrace();
								}
								picKuL.add(filmbean);
							}
							navbean.setPicKuL(picKuL);
						}
						
						/**
						 * <div class="list"> 
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):        <a href="vshow.php?id=11134">最新gachin娘!gachi1083 熟成生11~[ナ</a>
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):        <a href="vshow.php?id=11133">最新加勒比 122816-335 かり美びあんず</a>
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):        <a href="vshow.php?id=11132">最新 C0930 hitozuma1196 [须田山 阳子</a> 
01-02 12:02:37.130: I/QianBaiLuMNavService(32655):       </div> 
						 */
						try {
							Elements listElements = moduleElements.get(i).nextElementSibling().select("div.list");
							List<NavMChildBean> clist = new ArrayList<NavMChildBean>();
							for(int j=0;j<listElements.size();j++){
								Elements aElements = listElements.get(j).select("a"); 
									if(aElements!=null && aElements.size()>0){
										NavMChildBean cbean;
										for(int y=0;y<aElements.size();y++){
											cbean = new NavMChildBean();
											try {
												Element aElement = aElements.get(y).select("a").first();
												String aTitle = aElement.text();
												String ahref = aElement.attr("href");
												cbean.setTitle(aTitle);
												cbean.setHref(UrlUtils.QIAN_BAI_LU_M+ahref);
												Log.i(TAG, "i=="+i+";y=="+y+";aTitle=="+aTitle+";ahref=="+ahref);
											
											} catch (Exception e) {
												e.printStackTrace();
											}
											clist.add(cbean);
										}
										navbean.setList(clist);
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

package com.open.qianbailu.jsoup;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
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
import com.open.qianbailu.utils.UrlUtils;

public class PCQianBaiLuNavService extends CommonService {
	public static final String TAG = PCQianBaiLuNavService.class.getSimpleName();

	public static List<NavMBean> parseMNav(String href) {
		List<NavMBean> list = new ArrayList<NavMBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Log.i(TAG, "url = " + href);

			// Connection connection = Jsoup.connect(url);
			// connection.header("Host", "crm.yazuo.com");
			// connection.userAgent(UserAgent);
			// connection.header("Accept",
			// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			// connection.header("Accept-Language", "zh-CN,zh;q=0.8");
			// connection.header("Accept-Encoding", "gzip, deflate");
			// connection.header("Referer", "http://crm.yazuo.com/");
			// connection.header("Upgrade-Insecure-Requests", "1");
			// connection.header("Origin", "http://crm.yazuo.com");
			// connection.header("Content-Type",
			// "application/x-www-form-urlencoded");
			// connection.header("Connection", "keep-alive");
			// connection.header("Cache-Control", "max-age=0");
			// connection.header("Cookie", cookie);
			// connection.data("Mobile", "XXXXXXX");
			// connection.data("Upwd", "XXXX");
			// connection.data("remember_me", "1");
			// connection.method(Connection.Method.POST);
			// connection.followRedirects(false);//此行必须添加

			// Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
			// Accept-Encoding:gzip, deflate, sdch
			// Accept-Language:zh-CN,zh;q=0.8
			// Cache-Control:max-age=0
			// Connection:keep-alive
			// Cookie:__cfduid=df0a47eed3d6f56b0ce2b54928ced11051483189834;
			// CNZZDATA1000003418=380336421-1483185838-%7C1483852222
			// Host:1111av.co
			// If-Modified-Since:Sat, 07 Jan 2017 08:57:29 GMT
			// Upgrade-Insecure-Requests:1
			// User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64)
			// AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102
			// Safari/537.36
			// System.setProperty("http.proxyHost", "my.proxyhost.com");
			// System.setProperty("http.proxyPort", "1234");
			// try {
			// InetAddress address = InetAddress.getByName(href);
			// } catch (UnknownHostException e) {
			// e.printStackTrace();
			// }
			Document doc = Jsoup.connect(href)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate, sdch")
					.header("Accept-Language", "zh-CN,zh;q=0.8")
					.header("Cache-Control", "max-age=0")
					.header("Connection", "keep-alive")
					.header("Cookie", UrlUtils.COOKIE).
					header("Host", UrlUtils.QIAN_BAI_LU_HOST)
					.header("If-Modified-Since", new Date().toGMTString()).header("Upgrade-Insecure-Requests", "1")
					// .userAgent(UrlUtils.qianbailuAgent)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
					.header("Referer", UrlUtils.QIAN_BAI_LU_HOST)
					.followRedirects(false)
					.timeout(10000).get();
			// Log.i(TAG, doc.toString());
			try {
				/**
				 * <li class="homea"><a class="stp">图片</a></li>
				 */
				NavMBean navbean = new NavMBean();
				navbean.setTitle("首页");
				navbean.setHref(UrlUtils.QIAN_BAI_LU);
				list.add(navbean);

				Element ulElement = doc.select("div.newnav").first();
				if (ulElement != null) {
					Elements liElements = ulElement.select("li.homea");
					if (liElements != null && liElements.size() > 0) {
						for (int i = 0; i < liElements.size(); i++) {
							navbean = new NavMBean();
							Element aElement = liElements.get(i).select("a").first();
							try {
								if (aElement != null) {
									String titlea = aElement.text();
									Log.i(TAG, "i==" + i + ";titlea==" + titlea);
									navbean.setTitle(titlea);
									navbean.setHref(href);
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
			// Log.i(TAG, doc.toString());
			// System.out.println(doc.toString());
			try {
				Element titleLeftElement = doc.select("div.trailers").first();
				if (titleLeftElement != null) {
					NavMBean navbean = new NavMBean();
					String titlea = titleLeftElement.select("div.title").first().text();
					Log.i(TAG, "titlea==" + titlea);
					navbean.setTitle(titlea);
					
					/**
					 *  <li>
<a class="play-img" title="身材非常曼妙的90后梦幻情人让我捆着玩" href="/vod/11064.html" target="_blank">
<img alt="身材非常曼妙的90后梦幻情人让我捆着玩" src="//i3.1100lu.xyz/vod/2016-12-23/585c0a1eccc92.jpg"><em>网友自拍</em></a>
<b><a title="身材非常曼妙的90后梦幻情人让我捆着玩" href="/vod/11064.html" target="_blank">身材非常曼妙的9</a></b>
</li><
					 */
					Elements liElements = titleLeftElement.select("li");
					if (liElements != null && liElements.size() > 0) {
						List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
						PicKuFilmBean filmbean;
						for (int y = 0; y < liElements.size(); y++) {
							filmbean = new PicKuFilmBean();
							filmbean.setType(1);
							try {
								Element imgElement = liElements.get(y).select("img").first();
								String src = imgElement.attr("src");
								String picTitle = imgElement.attr("alt");
								filmbean.setTitle(picTitle);
								if(src==null || src.length()==0){
									src = imgElement.attr("data-cfsrc");
								}
								filmbean.setSrc(UrlUtils.QIAN_BAI_LU_HTTP + src.replace("i3.1100lu.xyz", "mi3.1100lu.xyz"));
								Log.i(TAG,  ";y==" + y + ";src==" + src);
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element aElement = liElements.get(y).select("img").first().parent();
								String ahref = aElement.attr("href");
								filmbean.setHref(UrlUtils.QIAN_BAI_LU + ahref);
								Log.i(TAG,  ";y==" + y + ";ahref==" + ahref);
							} catch (Exception e) {
								e.printStackTrace();
							}
							picKuL.add(filmbean);
						}
						navbean.setPicKuL(picKuL);
					}
					list.add(navbean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Elements moduleElements = doc.select("div.box");
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

						// picKuFilm
						Element picKuFilmElement = moduleElements.get(i).select("div.clearfix").first();
						Elements picKuFilmElements = picKuFilmElement.select("li");
						if (picKuFilmElements != null && picKuFilmElements.size() > 0) {
							List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
							PicKuFilmBean filmbean;
							for (int y = 0; y < picKuFilmElements.size(); y++) {
								filmbean = new PicKuFilmBean();
								filmbean.setType(1);
								try {
									/**
									 * <li>
									 * <a class="play-img"
									 * href="/vod/10241.html"
									 * title="最新加勒比 083116-244 ルナ玩交友游戏~[ルナ]"
									 * target="_blank"> <img
									 * alt="最新加勒比 083116-244 ルナ玩交友游戏~[ルナ]" src=
									 * "//i3.1100lu.xyz/vod/2016-09-02/57c85f28a92e8.jpg"
									 * ><em>亚洲情色</em> </a> <b><a title=
									 * "//i3.1100lu.xyz/vod/2016-09-02/57c85f28a92e8.jpg"
									 * href="/vodhtml/9521.html"
									 * target="_blank">最新加勒比 08</a> </b>
									 * <p>
									 * 09-02
									 * </p>
									 * </li>
									 * 
									 * 
									 * <li>
									 * <a href="/vod/7125.html" title=
									 * "最新heyzo0908君岛アンナ【きみじまあんな】 セレブなアンナを思いのままに"
									 * target="_blank"> <img src=
									 * "//i3.1100lu.xyz/vod/2016-03-13/56e585875ea7e.jpg"
									 * alt=
									 * "最新heyzo0908君岛アンナ【きみじまあんな】 セレブなアンナを思いのままに"
									 * /></a>
									 * <p>
									 * <a href="/vod/7125.html" title=
									 * "最新heyzo0908君岛アンナ【きみじまあんな】 セレブなアンナを思いのままに"
									 * target="_blank">最新heyzo0908君岛ア</a>
									 * </p>
									 * </li>
									 */
									Element imgElement = picKuFilmElements.get(y).select("img").first();
									String src = imgElement.attr("src");
									String picTitle = imgElement.attr("alt");
									filmbean.setTitle(picTitle);
									if(src==null || src.length()==0){
										src = imgElement.attr("data-cfsrc");
									}
									filmbean.setSrc(UrlUtils.QIAN_BAI_LU_HTTP + src.replace("i3.1100lu.xyz", "mi3.1100lu.xyz"));
									Log.i(TAG, "i==" + i + ";y==" + y + ";src==" + src);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element aElement = picKuFilmElements.get(y).select("img").first().parent();
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

						/**
						 * <li><i class="n">1</i><a
						 * href="/html/article/xingai/2017/0110/393480.html"
						 * title="夜深女人裸睡更健康" target="_blank">夜深女人裸睡更健康</a></li>
						 */
						try {
							Element listElement = moduleElements.get(i).select("div.shot").first();
							//<dl>
					          //<dt><a href="/html/tupian/oumei/index.html" target="_blank">最新欧美色图</a></dt>
					        //  </dl>

							Elements listElements = listElement.select("li");
							List<NavMChildBean> clist = new ArrayList<NavMChildBean>();
							for (int j = 0; j < listElements.size(); j++) {
								NavMChildBean cbean = new NavMChildBean();
								
								try {
									String dt = listElement.select("dt").first().select("a").first().text();
									if(dt.contains("最新小说")){
										cbean.setType(4);
									}else if(dt.contains("色图")){
										cbean.setType(5);
									} 
								} catch (Exception e) {
									e.printStackTrace();
									cbean.setType(6);
								}
								try {
									Element aElement = listElements.get(j).select("a").first();
									String aTitle = aElement.attr("title");
									String ahref = aElement.attr("href");
									cbean.setTitle(aTitle);
									cbean.setHref(UrlUtils.QIAN_BAI_LU + ahref);
									Log.i(TAG, "i==" + i +  ";aTitle==" + aTitle + ";ahref==" + ahref);

								} catch (Exception e) {
									e.printStackTrace();
								}
								clist.add(cbean);
								navbean.setList(clist);
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

	public static List<NavMBean> parseMShowFoot(String href, int type) {
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

						/**
						 * <div class="list"> 01-02 12:02:37.130:
						 * I/QianBaiLuMNavService(32655): <a
						 * href="vshow.php?id=11134">最新gachin娘!gachi1083
						 * 熟成生11~[ナ</a> 01-02 12:02:37.130:
						 * I/QianBaiLuMNavService(32655): <a
						 * href="vshow.php?id=11133">最新加勒比 122816-335
						 * かり美びあんず</a> 01-02 12:02:37.130:
						 * I/QianBaiLuMNavService(32655): <a
						 * href="vshow.php?id=11132">最新 C0930 hitozuma1196 [须田山
						 * 阳子</a> 01-02 12:02:37.130:
						 * I/QianBaiLuMNavService(32655): </div>
						 */
						try {
							Elements listElements = moduleElements.get(i).nextElementSibling().select("div.list");
							List<NavMChildBean> clist = new ArrayList<NavMChildBean>();
							for (int j = 0; j < listElements.size(); j++) {
								Elements aElements = listElements.get(j).select("a");
								if (aElements != null && aElements.size() > 0) {
									NavMChildBean cbean;
									for (int y = 0; y < aElements.size(); y++) {
										cbean = new NavMChildBean();
										try {
											Element aElement = aElements.get(y).select("a").first();
											String aTitle = aElement.text();
											String ahref = aElement.attr("href");
											cbean.setTitle(aTitle);
											cbean.setType(type);
											cbean.setHref(UrlUtils.QIAN_BAI_LU_M + ahref);
											Log.i(TAG, "i==" + i + ";y==" + y + ";aTitle==" + aTitle + ";ahref==" + ahref);

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

	public static List<NavMBean> parseMDianyingFoot(String href) {
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

						// picKuFilm
						Elements picKuFilmElements = moduleElements.get(i).nextElementSibling().select("div.movieHome");
						if (picKuFilmElements != null && picKuFilmElements.size() > 0) {
							List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
							PicKuFilmBean filmbean;
							for (int y = 0; y < picKuFilmElements.size(); y++) {
								filmbean = new PicKuFilmBean();
								try {
									/**
									 * <div class="movieHome"> <a
									 * href='vshow.php?id=10241'> <img src=
									 * "//mi3.1100lu.xyz/m/vod/2016-09-02/57c85f28a92e8.jpg"
									 * /></a> <div class="movieCover">最新加勒比
									 * 083116-244 ルナ玩交友游戏</div> </div>
									 */
									Element imgElement = picKuFilmElements.get(y).select("img").first();
									String src = imgElement.attr("src");
									if(src==null || src.length()==0){
										src = imgElement.attr("data-cfsrc");
									}
									filmbean.setSrc(UrlUtils.QIAN_BAI_LU_HTTP + src.replace("//i3.1100lu.xyz", "//mi3.1100lu.xyz"));
									Log.i(TAG, "i==" + i + ";y==" + y + ";src==" + src);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element aElement = picKuFilmElements.get(y).select("div.movieCover").first();
									String picTitle = aElement.text();
									filmbean.setTitle(picTitle);
									Log.i(TAG, "i==" + i + ";y==" + y + ";picTitle==");
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element aElement = picKuFilmElements.get(y).select("a").first();
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

						if (navbean.getTitle().equals("热门推荐")) {
							list.add(navbean);
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

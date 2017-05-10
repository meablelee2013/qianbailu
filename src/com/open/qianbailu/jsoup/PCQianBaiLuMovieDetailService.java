package com.open.qianbailu.jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.qianbailu.bean.m.NavMChildBean;
import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.json.m.MovieDetailJson;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class PCQianBaiLuMovieDetailService extends CommonService {
	public static final String TAG = PCQianBaiLuMovieDetailService.class.getSimpleName();

	public static MovieDetailJson parsePicture(String href) {
		MovieDetailJson mMovieDetailJson = new MovieDetailJson();
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

			try {
				Element divElement = doc.select("div.pic").first();
				if (divElement != null) {
					String imgrsc = divElement.select("img").first().attr("src");
					if(imgrsc==null || imgrsc.length()==0){
						imgrsc = divElement.select("img").first().attr("data-cfsrc");
					}
					if (imgrsc.contains(UrlUtils.QIAN_BAI_LU_HTTP) || imgrsc.contains(UrlUtils.QIAN_BAI_LU_HTTPS)) {

					} else {
						imgrsc = UrlUtils.QIAN_BAI_LU_HTTP + imgrsc;
					}
					//http://mi1.103av.org/m/1100/vod/201705/06/vod/pr501ufy2p4.jpg"
					////i3.107av.net/vod/2017-05-07/590dfd988579f.jpg
					////i3.107av.net/vod/2017-05-07/590dfda74dc3f.jpg
					mMovieDetailJson.setMovieDetaiImg(imgrsc.replace("i3.1100lu.xyz", "mi3.1100lu.xyz"));
					mMovieDetailJson.setSec_info_intro(divElement.select("img").first().attr("alt"));

					/**
					 * <li>
					 * <span>影片类型：</span><a target="_blank"
					 * href="/list/6.html" title="乱伦虐待">乱伦虐待</a></li> <li>
					 * 
					 * <span>看片帮助：</span><a target="_blank"
					 * href="/detail/help.html" rel="nofollow">看片教程</a></li> <li>
					 * <span>移动端看片：</span><a target="_blank"
					 * href="/detail/mobile.html" rel="nofollow">看片教程</a></li>
					 * <li><div class="fl">
					 * <span>更新日期：</span>2016-12-10</div><a
					 * class="fr" href="#pl"
					 * style="text-align:right; margin-right:10px;"><img src=
					 * "//ww4.sinaimg.cn/large/005Z6lBIjw1eod4d93jasg302i00m3y9.gif"
					 * alt="查看评论" width="90" height="22" /></a></li> <li>
					 * <span>最新网址：</span>WWW.1111AV.CO</li>
					 */
					try {
						Element movieInfoElement = divElement.nextElementSibling();
						if (movieInfoElement != null) {
							mMovieDetailJson.setMovie_name(divElement.select("img").first().attr("alt"));

							try {
								String span = movieInfoElement.select("li").get(0).select("span").first().text();
								String aa = movieInfoElement.select("li").get(0).select("a").first().text();
								mMovieDetailJson.setMovie_type(span + aa);
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								String span = movieInfoElement.select("li").get(1).select("span").first().text();
								String aa = movieInfoElement.select("li").get(1).select("a").first().text();
								mMovieDetailJson.setMovie_time(span + aa );
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Element divElement = doc.select("div.downlist").first();
				if (divElement != null) {
					/**
					 * <div class="downlist">
					 * <ul>
					 * <li>
					 * <p>
					 * 最新加勒比 120716-319 性欲処理マゾマスク ～欲して闷える濡れた美熟女
					 * </p>
					 * <span> <a class="d1" href=
					 * "thunder://QUFlZDJrOi8vfGZpbGV8RjY0RjZERUU4NUJCN0Y2REM0NkZGRUQ2MzBCNTY0NjgubXA0fDE5MDA3NDcwODB8RjY0RjZERUU4NUJCN0Y2REM0NkZGRUQ2MzBCNTY0Njh8aD1LWkM1RE1SUUJOR1dPTTVDVUNOSkI0R0U2VTJVQ0lRVXwvWlo="
					 * target="_blank">迅雷下载</a> <a class="d2" href=
					 * "qqdl://ZWQyazovL3xmaWxlfGNhcmliLTEyMDcxNi0zMTkubXA0fDE5MDA3NDcwODB8RjY0RjZERUU4NUJCN0Y2REM0NkZGRUQ2MzBCNTY0Njh8aD1LWkM1RE1SUUJOR1dPTTVDVUNOSkI0R0U2VTJVQ0lRVXwv"
					 * target="_blank">旋风下载</a> <a class="d3" href=
					 * "flashget://W0ZMQVNIR0VUXWVkMms6Ly98ZmlsZXxjYXJpYi0xMjA3MTYtMzE5Lm1wNHwxOTAwNzQ3MDgwfEY2NEY2REVFODVCQjdGNkRDNDZGRkVENjMwQjU2NDY4fGg9S1pDNURNUlFCTkdXT001Q1VDTkpCNEdFNlUyVUNJUVV8L1tGTEFTSEdFVF0="
					 * target="_blank">快车下载</a> <a class="d4" href=
					 * "ed2k://|file|carib-120716-319.mp4|1900747080|F64F6DEE85BB7F6DC46FFED630B56468|h=KZC5DMRQBNGWOM5CUCNJB4GE6U2UCIQU|/"
					 * target="_blank">电驴下载</a></span></li>
					 * </ul>
					 * </div>
					 */
					Elements liElements = divElement.select("a");
					if (liElements != null && liElements.size() > 0) {
						List<NavMChildBean> listd = new ArrayList<NavMChildBean>();
						NavMChildBean cbean;
						for (int j = 0; j < liElements.size(); j++) {
							try {
								cbean = new NavMChildBean();
								try {
									Element aElement = liElements.get(j).select("a").first();
									if (aElement != null) {
										try {
											String ahref = aElement.attr("href");
											cbean.setHref(ahref);
											String title = aElement.text();
											Log.i(TAG, "j==" + j + ";ahref==" + ahref + ";title==" + title);
											cbean.setTitle(title);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								listd.add(cbean);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						mMovieDetailJson.setListd(listd);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Element divElement = doc.select("div.endtext").first();
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
										String src =   aElement.attr("src");
										if(src==null || src.length()==0){
											 src =  aElement.attr("data-cfsrc");
										}
										if (src.contains(UrlUtils.QIAN_BAI_LU_HTTP) || src.contains(UrlUtils.QIAN_BAI_LU_HTTPS)) {
										} else {
											src = UrlUtils.QIAN_BAI_LU_HTTP + src;
										}
										// http://i1.1100lu.xyz/month_1508/1508132048bc4c73dcb045d61e.jpg
										// http://i2.1100lu.xyz/month_1508/1508132048bc4c73dcb045d61e.jpg
//										src = src
//												.replace("http://i1.1100lu.xyz", "http://mi1.100av.org/m")
//												.replace("http://i2.1100lu.xyz", "http://mi2.100av.org/m")
//												.replace("http://i3.1100lu.xyz", "http://mi3.100av.org/m")
//												.replace("http://mi1.100av.org/m", "http://mi3.1100lu.xyz/m");
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
		mMovieDetailJson.setList(list);
		return mMovieDetailJson;
	}

	public static List<ShowBean> parseShow(String href) {
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
			Element divElement = doc.select("div.detailText").get(1);
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
										if(src==null || src.length()==0){
											 src =  aElement.attr("data-cfsrc");
										}
										movieBean.setSrc(src.replace("i3.1100lu.xyz", "mi3.1100lu.xyz"));
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

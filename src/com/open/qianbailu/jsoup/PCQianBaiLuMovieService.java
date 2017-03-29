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
import com.open.qianbailu.json.m.MovieJson;
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
							if(src==null || src.length()==0){
								 src = divElements.get(i).select("img").first().attr("data-cfsrc");
							}
							Log.i(TAG, "i==" + i + ";src==" + src);
							movieBean.setThumb(UrlUtils.QIAN_BAI_LU_HTTP + src.replace("i3.1100lu.xyz", "mi3.1100lu.xyz"));
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
	
	public static MovieJson parseMovieJson(String href, int pagerno) {
		MovieJson mMovieJson = new MovieJson();
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

			//pager
			/**
			 * <div id="pages">共6316部影片&nbsp;当前:1/452页&nbsp;
			 * <em>首页</em>&nbsp;
			 * <em>上一页</em>&nbsp;<span>1</span>&nbsp;
			 * <a href="/list/1-2.html">2</a>&nbsp;
			 * <a href="/list/1-3.html">3</a>&nbsp;
			 * <a href="/list/1-2.html" class="pagegbk">下一页</a>&nbsp;
			 * <a href="/list/1-452.html" class="pagegbk">尾页</a>&nbsp;
			 * 
			 * <input type="input" name="page" id="page" size=4 class="pagego"/>
			 * <input type="button" value="跳 转" onclick="pagego('/list/1-{!page!}.html',452)" class="pagebtn" /></div>
    </div>
			 **/
			Element inputElement = doc.select("input.pagebtn").first();
			if(inputElement!=null){
				Element divElement = inputElement.parent();
				Elements  aElements = divElement.select("a");
				for(int i=0;i<aElements.size();i++){
					Element aElement = aElements.get(i).select("a").first();
					String titlea = aElement.text();
					if(titlea!=null && titlea.contains("尾页")){
						try {
							mMovieJson.setMaxpageno(Integer.parseInt(aElement.attr("href").replace(".html", "")
									.replace("/list/1-", "").replace("/list/2-", "").replace("/list/3-", "").replace("/list/4-", "")
									.replace("/list/5-", "").replace("/list/6-", "").replace("/list/7-", "").replace("/list/8-", "")
									.replace("/list/9-", "").replace("/list/10-", "").replace("/list/11-", "").replace("/list/12-", "")
									.replace("/list/13-", "").replace("/list/14-", "").replace("/list/15-", "").replace("/list/16-", "")
									.replace("/list/17-", "").replace("/list/18-", "").replace("/list/19-", "").replace("/list/20-", "")
									)
									);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}
			  
			 /** <li>
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
							if(src==null || src.length()==0){
								 src = divElements.get(i).select("img").first().attr("data-cfsrc");
							}
							//http://i3.1100lu.xyz/vod/2017-03-29/58da9935ce722.jpg
							//http://mi3.1100lu.xyz/vod/2017-03-29/58da9935ce722.jpg
							//http://mi3.1100lu.xyz/m/vod/2017-03-29/58da9935ce722.jpg
							Log.i(TAG, "i==" + i + ";src==" + src);
							movieBean.setThumb(UrlUtils.QIAN_BAI_LU_HTTP + src.replace("i3.1100lu.xyz", "mi3.1100lu.xyz"));
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
		mMovieJson.setList(list);
		return mMovieJson;
	}
}

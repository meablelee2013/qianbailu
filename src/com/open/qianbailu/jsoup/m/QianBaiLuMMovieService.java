package com.open.qianbailu.jsoup.m;

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

public class QianBaiLuMMovieService extends CommonService {
	public static final String TAG = QianBaiLuMMovieService.class.getSimpleName();

	public static List<MovieBean> parseMovie(String href, int pagerno) {
		List<MovieBean> list = new ArrayList<MovieBean>();
		try {
			if(pagerno>0){
				href = href+"&page="+pagerno;
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
//			System.out.println(doc.toString());

			/**
			 * <div class="movieBlock"> 01-03 10:44:09.597: I/System.out(15800):
			 * <div class="movieList"> 01-03 10:44:09.597: I/System.out(15800):
			 * <a href="vshow.php?id=11137"><img
			 * src="//mi3.1100lu.xyz/m/vod/2016-12-31/586697fc8645a.jpg"></a>
			 * 01-03 10:44:09.597: I/System.out(15800): </div> 01-03
			 * 10:44:09.597: I/System.out(15800): <div
			 * class="movieDetail float"> 01-03 10:44:09.597:
			 * I/System.out(15800): <div class="movieTitle"> 01-03 10:44:09.597:
			 * I/System.out(15800): <a href="vshow.php?id=11137">最新pacopacomama
			 * 122816_232 露</a> 01-03 10:44:09.597: I/System.out(15800): </div>
			 * 01-03 10:44:09.597: I/System.out(15800): <a class="movieListPlay"
			 * href="vshow.php?id=11137">详 情</a> 01-03 10:44:09.597:
			 * I/System.out(15800): <div class="movieDetails"> 01-03
			 * 10:44:09.597: I/System.out(15800): <br>
			 * 01-03 10:44:09.597: I/System.out(15800): <br>
			 * 01-03 10:44:09.597: I/System.out(15800):
			 * <p>
			 * 类型：<span>亚洲</span>
			 * </p>
			 * 01-03 10:44:09.597: I/System.out(15800):
			 * <p>
			 * 时间：<span>12-31</span>
			 * </p>
			 * 01-03 10:44:09.597: I/System.out(15800): </div> 01-03
			 * 10:44:09.597: I/System.out(15800): </div> 01-03 10:44:09.597:
			 * I/System.out(15800): </div>
			 */
			Elements divElements = doc.select("div.movieBlock");
			if (divElements != null && divElements.size() > 0) {
				try {
					MovieBean movieBean;
					for (int i = 0; i < divElements.size(); i++) {
						movieBean = new MovieBean();
						try {
							Element movieElement = divElements.get(i).select("div.movieList").first();
							if (movieElement != null) {
								try {
									String hrefa = movieElement.select("a").first().attr("href");
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
									movieBean.setLinkurl(UrlUtils.QIAN_BAI_LU_M+hrefa);
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									String src = movieElement.select("img").first().attr("src");
									Log.i(TAG, "i==" + i + ";src==" + src);
//									movieBean.setThumb(UrlUtils.QIAN_BAI_LU_HTTP+src);
									//http://mi3.1100lu.xyz/m/vod/2017-05-27/59286ac5e65ab.jpg
									////mi3.1100lu.xyz/m/vod/2017-05-27/59286ac5e65ab.jpg
									movieBean.setThumb(UrlUtils.QIAN_BAI_LU_HTTP + src.replace("mi3.1100lu.xyz/m", "mi3.1100lu.xyz"));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						try {
							Element movieTitleElement = divElements.get(i).select("div.movieTitle").first();
							if (movieTitleElement != null) {
								try {
									String movieTitle = movieTitleElement.select("a").first().text(); 
									Log.i(TAG, "i==" + i + ";movieTitle==" + movieTitle);
									movieBean.setTitle(movieTitle);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
						try {
							Element movieDetailsElement = divElements.get(i).select("div.movieDetails").first();
							if (movieDetailsElement != null) {
								try {
									/**
									 * <p>类型：<span>亚洲</span></p> 
01-03 10:44:09.597: I/System.out(15800):        <p>时间：<span>12-31</span></p> 
									 */
									String type = movieDetailsElement.select("p").first().text().replace("<span>", "").replace("</span>", ""); 
									Log.i(TAG, "i==" + i + ";type==" + type);
									movieBean.setVmtype(type);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								try {
									/**
									 * <p>类型：<span>亚洲</span></p> 
01-03 10:44:09.597: I/System.out(15800):        <p>时间：<span>12-31</span></p> 
									 */
									String time = movieDetailsElement.select("p").get(1).text().replace("<span>", "").replace("</span>", ""); 
									Log.i(TAG, "i==" + i + ";time==" + time);
									movieBean.setProduceyear(time);
								} catch (Exception e) {
									e.printStackTrace();
								}
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

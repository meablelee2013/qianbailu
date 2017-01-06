package com.open.qianbailu.jsoup.m;

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

public class QianBaiLuMMovieDetailService extends CommonService {
	public static final String TAG = QianBaiLuMMovieDetailService.class.getSimpleName();

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
				Element divElement = doc.select("div.detailText").get(0);
				if(divElement!=null){
					mMovieDetailJson.setSec_info_intro(divElement.text());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Element divElement = doc.select("div.movieDetaiImg").first();
				if(divElement!=null){
					mMovieDetailJson.setMovieDetaiImg(UrlUtils.QIAN_BAI_LU_HTTP+divElement.select("img").first().attr("src"));
					/**
					 * <div class="movieInfo float">
                    <div class="movieInfos">
					    <p>片名：<span>最新pacopacomama 010317_235 新年女体盛~巫女多次疯狂被中出~[大咲萌]</span></p>
                        <p>类型：<span>亚洲</span></p>
                        <p>时间：<span>2017-01-05 02:59:17</span></p>
                    </div>                        
                </div>
					 */
					try {
						Element movieInfoElement = divElement.nextElementSibling();
						if(movieInfoElement!=null){
							mMovieDetailJson.setMovie_name(movieInfoElement.select("p").first().text().replace("</span>", "").replace("<span>", ""));
							mMovieDetailJson.setMovie_type(movieInfoElement.select("p").get(1).text().replace("</span>", "").replace("<span>", ""));
							mMovieDetailJson.setMovie_time(movieInfoElement.select("p").get(2).text().replace("</span>", "").replace("<span>", ""));
						}	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Element divElement = doc.select("div.playlist").first();
				if(divElement!=null){
					 /**
					  *  <div class="playlist">
			<ul>
			  <li><a href="ed2k://|file|paco-010317_001.mp4|1953264949|7598C2FA4FA8943DB6112C3D88F6759C|h=2LE3FDTICS3LPUQ2NKP4RF4K67GRMWLL|/" target="_blank">电驴下载</a></li>
			  <li><a href="thunder://QUFlZDJrOi8vfGZpbGV8cGFjby0wMTAzMTdfMDAxLm1wNHwxOTUzMjY0OTQ5fDc1OThDMkZBNEZBODk0M0RCNjExMkMzRDg4RjY3NTlDfGg9MkxFM0ZEVElDUzNMUFVRMk5LUDRSRjRLNjdHUk1XTEx8L1pa" target="_blank">迅雷下载</a></li>
			  <li><a href="xfplay://dna=BdydmZD2BdIcD0H2DGx3DxDWAHfcA0AfDZudm0e2AejgBdxWmZe3mD|dx=1953264949|mz=paco-010317_001.mp4|zx=nhE0pdOVlZe0mv41Ac4XBdDUmGH0BwxWBdaVrgMSnJ5R|zx=nhE0pdOVlZe0mv41Ac4XBdDUmGH0BwxWBdaVrgMSnJ5R" target="_blank">影音先锋第1集</a></li>
			</ul>
     　　</div>
					  */
					 Elements liElements = divElement.select("li");
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
		return list;
	}
}

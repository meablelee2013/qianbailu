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
import com.open.qianbailu.json.m.MovieJson;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class QianBaiLuMPictureService extends CommonService {
	public static final String TAG = QianBaiLuMPictureService.class.getSimpleName();
	
	public static List<MovieBean> parsePicture(String href, int pagerno) {
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
			 * <div id="pages">页次：1/232&nbsp;每页25&nbsp;总数5779&nbsp;&nbsp;&nbsp;&nbsp;首页&nbsp;&nbsp;上一页&nbsp;&nbsp;<a href='/html/article/jiqing/index_2.html'>下一页</a>&nbsp;&nbsp;<a href='/html/article/jiqing/index_232.html'>尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;转到:&nbsp;&nbsp;<input type="input" name="page" id="page" size=4 class="pagego"/><input type="button" value="跳 转" onclick="window.location='/html/article/jiqing/index_<{page}>.html'.replace('<{page}>', document.getElementById('page').value);" class="pagebtn" /></div>
  </div>
			 */

			/**
			 * <div class="list"> 
					<a href='show.php?id=390433&classid=12'>[10-28]最近网络疯传萝莉小奶牛女神童伊沫独家</a>
					<a href='show.php?id=390432&classid=12'>[10-28]情人在賓館染紅指甲，性趣來了，掰B漏鮑讓</a>
					<a href='show.php?id=390325&classid=12'>[10-24]大伙猜猜她几岁[38P]</a>
					<a href='show.php?id=390324&classid=12'>[10-24]大家自己看吧!喜欢玩的北京女人,露脸[4</a>
					<a href='show.php?id=390323&classid=12'>[10-24]18岁奶子大大，BB粉嫩水超多[34P]</a>
					<a href='show.php?id=390317&classid=12'>[10-24]港女4級私拍最新流出[20P]</a>
					<a href='show.php?id=390310&classid=12'>[10-24]嫩嫩的小穴 大咪咪居然还长毛毛[14P]</a>
					<a href='show.php?id=390305&classid=12'>[10-24]性急的老婆，我還沒開始肏呢，自己就開扣上</a>
					<a href='show.php?id=390304&classid=12'>[10-24]主人嫩B小咪仆來了[14P]</a>
					<a href='show.php?id=390301&classid=12'>[10-24]和黑丝情人激情自拍[31P]</a>
                    </div>

			 */
			Elements divElements = doc.select("div.moduleCont");
			if (divElements != null && divElements.size() > 0) {
				for (int j = 0; j < divElements.size(); j++) {
					Elements aElements = divElements.get(j).select("a");
					if (aElements != null && aElements.size() > 0) {
						try {
							MovieBean movieBean;
							for (int i = 0; i < aElements.size(); i++) {
								movieBean = new MovieBean();
								try {
									Element aElement = aElements.get(i).select("a").first();
									if (aElement != null) {
										try {
											String hrefa = aElement.attr("href");
											String title = aElement.text();
											Log.i(TAG, "i==" + i + ";hrefa==" + hrefa+";title=="+title);
											movieBean.setLinkurl(UrlUtils.QIAN_BAI_LU_M+hrefa);
											movieBean.setTitle(title);
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
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static MovieJson parsePictureJson(String href, int pagerno) {
		MovieJson mMovieJson = new   MovieJson();
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
			 *<ul class="pages"><li>
			 *<a href="/list.php?page=0&amp;classid=2&amp;bclassid=1" style="clolr:blue;" class="yes">首页</a>
			 *</li>&nbsp;<li><a href="/list.php?page=1&amp;classid=2&amp;bclassid=1" style="clolr:blue;" class="yes">上一页</a>
			 *</li>&nbsp;<li><a href="/list.php?page=3&amp;classid=2&amp;bclassid=1" style="clolr:blue;" class="yes">下一页</a>
			 *</li>&nbsp;<li><a href="/list.php?page=288&amp;classid=2&amp;bclassid=1" style="clolr:blue;" class="yes">尾页</a>
			 *</li>&nbsp;</ul></div>

			 */Element inputElement = doc.select("ul.pages").first();
				if(inputElement!=null){
					Elements  aElements = inputElement.select("a");
					for(int i=0;i<aElements.size();i++){
						Element aElement = aElements.get(i).select("a").first();
						String titlea = aElement.text();
						if(titlea!=null && titlea.contains("尾页")){
							try {
								mMovieJson.setMaxpageno(Integer.parseInt(aElement.attr("href") 
										.replace("/list.php?page=", "")
										.replace("&classid=2&bclassid=1", "") 
										.replace("&classid=1&bclassid=1", "")
										.replace("&classid=3&bclassid=1", "")
										.replace("&classid=4&bclassid=1", "")
										.replace("&classid=5&bclassid=1", "")
										.replace("&classid=6&bclassid=1", "")
										.replace("&classid=7&bclassid=1", "")
										.replace("&classid=12&bclassid=1", "")
										.replace("&classid=13&bclassid=1", "")
										.replace("&classid=14&bclassid=1", "")
										.replace("&classid=15&bclassid=1", "")
										.replace("&classid=16&bclassid=1", "")
										.replace("&classid=17&bclassid=1", "")
										.replace("&classid=18&bclassid=1", "")
										)
										);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
				}

			/**
			 * <div class="list"> 
					<a href='show.php?id=390433&classid=12'>[10-28]最近网络疯传萝莉小奶牛女神童伊沫独家</a>
					<a href='show.php?id=390432&classid=12'>[10-28]情人在賓館染紅指甲，性趣來了，掰B漏鮑讓</a>
					<a href='show.php?id=390325&classid=12'>[10-24]大伙猜猜她几岁[38P]</a>
					<a href='show.php?id=390324&classid=12'>[10-24]大家自己看吧!喜欢玩的北京女人,露脸[4</a>
					<a href='show.php?id=390323&classid=12'>[10-24]18岁奶子大大，BB粉嫩水超多[34P]</a>
					<a href='show.php?id=390317&classid=12'>[10-24]港女4級私拍最新流出[20P]</a>
					<a href='show.php?id=390310&classid=12'>[10-24]嫩嫩的小穴 大咪咪居然还长毛毛[14P]</a>
					<a href='show.php?id=390305&classid=12'>[10-24]性急的老婆，我還沒開始肏呢，自己就開扣上</a>
					<a href='show.php?id=390304&classid=12'>[10-24]主人嫩B小咪仆來了[14P]</a>
					<a href='show.php?id=390301&classid=12'>[10-24]和黑丝情人激情自拍[31P]</a>
                    </div>

			 */
			Elements divElements = doc.select("div.moduleCont");
			if (divElements != null && divElements.size() > 0) {
				for (int j = 0; j < divElements.size(); j++) {
					Elements aElements = divElements.get(j).select("a");
					if (aElements != null && aElements.size() > 0) {
						try {
							MovieBean movieBean;
							for (int i = 0; i < aElements.size(); i++) {
								movieBean = new MovieBean();
								try {
									Element aElement = aElements.get(i).select("a").first();
									if (aElement != null) {
										try {
											String hrefa = aElement.attr("href");
											String title = aElement.text();
											Log.i(TAG, "i==" + i + ";hrefa==" + hrefa+";title=="+title);
											movieBean.setLinkurl(UrlUtils.QIAN_BAI_LU_M+hrefa);
											movieBean.setTitle(title);
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
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		mMovieJson.setList(list);
		return mMovieJson;
	}
}

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

public class PCQianBaiLuPictureService extends CommonService {
	public static final String TAG = PCQianBaiLuPictureService.class.getSimpleName();
	public static int maxpageno;
	
	public static List<MovieBean> parsePicture(String href, int pagerno) {
		List<MovieBean> list = new ArrayList<MovieBean>();
		try {
			//http://www.1111av.co/html/tupian/toupai/index_2.html
			//http://www.1111av.co/html/tupian/toupai/ 
			//http://www.1111av.co/html/tupian/toupai/index.html
			if(pagerno>1){
				if(href.contains("index.html")){
					href = href.replace("index.html", "")+"index_"+pagerno+".html";
				}else if(href.contains("index.html?")){
					href = href.replace("index.html?", "")+"index_"+pagerno+".html";
				}else{
					href = href+"index_"+pagerno+".html";
				}
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
	 <!--/box-->
    <div id="pages">页次：1/202&nbsp;每页25&nbsp;总数5038&nbsp;&nbsp;&nbsp;&nbsp;首页&nbsp;&nbsp;上一页&nbsp;&nbsp;
    <a href='/html/tupian/qingchun/index_2.html'>下一页</a>&nbsp;&nbsp;
    <a href='/html/tupian/qingchun/index_202.html'>尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;转到:&nbsp;&nbsp;
    <input type="input" name="page" id="page" size=4 class="pagego"/><input type="button" value="跳 转" 
    onclick="window.location='/html/tupian/qingchun/index_<{page}>.html'.replace('<{page}>', 
    document.getElementById('page').value);" class="pagebtn" /></div>

			 */
			Element inputElement = doc.select("input.pagebtn").first();
				if(inputElement!=null){
					Elements  aElements = inputElement.parent().select("a");
					for(int i=0;i<aElements.size();i++){
						Element aElement = aElements.get(i).select("a").first();
						String titlea = aElement.text();
						if(titlea!=null && titlea.contains("尾页")){
							try {
								maxpageno = Integer.parseInt(aElement.attr("href") 
										.replace(".html", "")
										.replace("/html/tupian/qingchun/index_", "") 
										.replace("/html/tupian/dongman/index_", "")
										.replace("/html/tupian/mingxing/index_", "")
										.replace("/html/tupian/oumei/index_", "")
										.replace("/html/tupian/siwa/index_", "")
										.replace("/html/tupian/yazhou/index_", "")
										.replace("/html/tupian/toupai/index_", "")
										)
										;
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
				}
			/**
		<li><a href="/html/tupian/toupai/2016/1028/390433.html" target="_blank">
		最近网络疯传萝莉小奶牛女神童伊沫独家无底线全套 嫩不嫩你说的</a> <span>2016-10-28</span></li>
			 */
			Element divElement = doc.select("div.art_box").first();
			if (divElement != null  ) {
					Elements aElements = divElement.select("li");
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
											Log.i(TAG, "i==" + i + ";hrefa==" + hrefa );
											movieBean.setLinkurl(UrlUtils.QIAN_BAI_LU+hrefa);
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										try {
											String title = aElement.text() + "   "+aElements.get(i).select("span").first().text();
											Log.i(TAG, "i==" + i +  ";title=="+title);
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

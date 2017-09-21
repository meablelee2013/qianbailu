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

public class QianBaiLuMBIndicatorService extends CommonService {
	public static final String TAG = QianBaiLuMBIndicatorService.class.getSimpleName();

	 
	public static List<NavMBean> parseMHome(String href,int type) {
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
						
						//movieHome
						Elements listElements = moduleElements.get(i).nextElementSibling().select("div.list");
						if(listElements!=null && listElements.size()>0){
							List<NavMChildBean> listc = new ArrayList<NavMChildBean>();
							NavMChildBean childbean;
							for(int j=0;j<listElements.size();j++){
								Elements aElements = listElements.get(j).select("a");
								if(aElements!=null && aElements.size()>0){
									for(int y=0;y<aElements.size();y++){
										childbean = new NavMChildBean();
										try {
											/**
										<div class="screem640">
											    <div class="moduleCont">
							                	<div class="list">  
											<a href='show.php?classid=12&id=390433'>最近网络疯传萝莉小奶牛女神童伊</a>
											<a href='show.php?classid=12&id=390432'>情人在賓館染紅指甲，性趣來了，掰B</a>
											<a href='show.php?classid=12&id=390325'>大伙猜猜她几岁[38P]</a>
											<a href='show.php?classid=12&id=390324'>大家自己看吧!喜欢玩的北京女人,</a>
						                        </div>
						                    </div>

											 */
											Element aElement = aElements.get(y).select("a").first();
											String hrefa = aElement.attr("href");
											String title = aElement.text();
											childbean.setTitle(title);
											childbean.setType(type);
											Log.i(TAG, "i=="+i+";y=="+y+";title=="+title);
											childbean.setHref(UrlUtils.QIAN_BAI_LU_M+hrefa);
											Log.i(TAG, "i=="+i+";y=="+y+ ";ahref=="+hrefa);
										} catch (Exception e) {
											e.printStackTrace();
										}
										 
										listc.add(childbean);
									}
								}
							}
							navbean.setList(listc);
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

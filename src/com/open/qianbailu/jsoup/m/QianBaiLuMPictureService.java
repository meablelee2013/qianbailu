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
}

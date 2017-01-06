package com.open.qianbailu.jsoup.m;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

import com.open.qianbailu.json.m.XiaoShuoJson;
import com.open.qianbailu.jsoup.CommonService;
import com.open.qianbailu.utils.UrlUtils;

public class QianBaiLuMXiaoShuoService extends CommonService {
	public static final String TAG = QianBaiLuMXiaoShuoService.class.getSimpleName();

	public static XiaoShuoJson parseXiaoSHuo(String href) {
		XiaoShuoJson mXiaoShuoJson = new XiaoShuoJson();
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

			/**
			 * <div class="ljTitle">上一篇:<a href='show.php?id=388506&classid=13'
			 * style='color:#6f6f6f;'>割り切りバイト ささの遥[22P]</a></div> <div
			 * class="ljTitle">下一篇:很抱歉已经没有了</div>
			 */
			try {
				Element ljTitleElement = doc.select("div.ljTitle").first();
				if (ljTitleElement != null) {
					Element aElement = ljTitleElement.select("a").first();
					if (aElement != null) {
						mXiaoShuoJson.setPreHref(UrlUtils.QIAN_BAI_LU_M + aElement.attr("href"));
						mXiaoShuoJson.setPreTitle("上一篇:" + aElement.text());
					} else {
						mXiaoShuoJson.setPreTitle(ljTitleElement.text());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Element ljTitleElement = doc.select("div.ljTitle").get(1);
				if (ljTitleElement != null) {
					Element aElement = ljTitleElement.select("a").first();
					if (aElement != null) {
						mXiaoShuoJson.setNextHref(UrlUtils.QIAN_BAI_LU_M + aElement.attr("href"));
						mXiaoShuoJson.setNextTitle("上一篇:" + aElement.text());
					} else {
						mXiaoShuoJson.setNextTitle(ljTitleElement.text());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Element divElement = doc.select("div.detailText").first();
			if (divElement != null) {

				/**
				 * <div class="detail"> <div
				 * class="newsTitle">発情-密著接吻極上痴女-1[40P]</div>
				 * <p class="neswTime">
				 * 更新时间:2016-09-23
				 * </p>
				 * </div>
				 */
				try {
					Element detailElement = divElement.previousElementSibling();
					mXiaoShuoJson.setNewsTitle(detailElement.select("div.newsTitle").first().text());
					mXiaoShuoJson.setNeswTime(detailElement.select("p.neswTime").first().text());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					mXiaoShuoJson.setDetailText(divElement.text());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mXiaoShuoJson;
	}
}

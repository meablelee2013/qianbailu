package com.open.qianbailu.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

import com.open.qianbailu.bean.ContboxBean;
import com.open.qianbailu.utils.UrlUtils;

public class ContBoxScrollService extends CommonService {
	public static final String TAG = ContBoxScrollService.class.getSimpleName();

	public static ContboxBean parseContbox(String href) {
		ContboxBean contbox = new ContboxBean();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.qianbailuAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
					contbox.setCenterp(doc.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contbox;
	}
}

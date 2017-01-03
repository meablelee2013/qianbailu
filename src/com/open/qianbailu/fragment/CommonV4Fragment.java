package com.open.qianbailu.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.qianbailu.R;
import com.open.qianbailu.jsoup.CommonService;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :fengguangjing
 * @createTime: 16/11/18
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class CommonV4Fragment extends BaseV4Fragment {
	String url;
	boolean isVisibleToUser;
    public static CommonV4Fragment newInstance(String url,boolean isVisibleToUser) {
        CommonV4Fragment fragment = new CommonV4Fragment();
        fragment.setFragment(fragment);
        fragment.url  = url;
        fragment.isVisibleToUser = isVisibleToUser;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_v4, container, false);
        return view;
    }
 

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		volleyJson("http://video.coral.qq.com/user/0/msg/v2?msgtype=reply&callback=messages3&msgid=&pageflag=1&reqnum=10&type=3&_=1481509025355");
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#call()
	 */
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		if(isVisibleToUser){
			CommonService.parse(url);
		}
		return super.call();
	}

	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(Object result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
	}
	
	
	
//	@Override
//	public void volleyJson(final String href) {
//		System.out.println("href=" + href);
//		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Cookie", UrlUtils.getCookie());
//		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, headers, new Response.Listener<String>() {
//			@Override
//			public void onResponse(String response) {
//				response = response.replace("messages3({", "{").replace("})", "}");
//				System.out.println("response=" + response);
//				
//				JSONObject userReplyJson = JSONObject.fromObject(response);
//				int errCode = userReplyJson.getInt("errCode");
//				System.out.println("errCode=" + errCode);
//				
//				JSONObject info = userReplyJson.getJSONObject("info");
//				long time = info.getLong("time");
//				System.out.println("time=" + time);
//				
//				JSONObject data = userReplyJson.getJSONObject("data");
//				double first  = data.getDouble("first");
//				double last  = data.getDouble("last");
//				int reqnum  = data.getInt("reqnum");
//				int retnum  = data.getInt("retnum");
//				int total  = data.getInt("total");
//				System.out.println("first=" + first);
//				System.out.println("last=" + last);
//				System.out.println("reqnum=" + reqnum);
//				System.out.println("retnum=" + retnum);
//				System.out.println("total=" + total);
//				
//				JSONArray messages = userReplyJson.getJSONArray("messages");
//				for(int i=0;i<messages.size();i++){
//					JSONObject message = messages.getJSONObject(i);
//					String id = message.getString("id");
//					String parent = message.getString("parent");
//					String targetid = message.getString("targetid");
//					int tipstype  = data.getInt("tipstype");
//					double tipstime  = data.getDouble("tipstime");
//					System.out.println("id=" + id);
//					System.out.println("parent=" + parent);
//					System.out.println("targetid=" + targetid);
//					System.out.println("tipstype=" + tipstype);
//					System.out.println("tipstime=" + tipstime);
//				}
//				
//			}
//		}, RankV4Fragment.this);
//		requestQueue.add(jsonObjectRequest);
//	}
}

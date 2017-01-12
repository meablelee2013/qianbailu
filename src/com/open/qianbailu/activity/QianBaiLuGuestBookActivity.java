/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-12上午10:50:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.open.qianbailu.R;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-12上午10:50:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class QianBaiLuGuestBookActivity extends CommonFragmentActivity {
	private String url = "http://home.188lu.us/index.php?s=Gb-Insert";
	private EditText edit_content;
	private Button btn_commit;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.CommonFragmentActivity#onCreate(android.os
	 * .Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbailu_guest_book);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		edit_content = (EditText) findViewById(R.id.edit_content);
		btn_commit = (Button) findViewById(R.id.btn_commit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.CommonFragmentActivity#bindEvent()
	 */
	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		btn_commit.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.CommonFragmentActivity#onClick(android.view
	 * .View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_commit:
			if (edit_content.getText().toString().length() > 0) {
				// Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
				// Accept-Encoding:gzip, deflate
				// Accept-Language:zh-CN,zh;q=0.8
				// Cache-Control:max-age=0
				// Connection:keep-alive
				// Content-Length:78
				// Content-Type:application/x-www-form-urlencoded
				// Cookie:__cfduid=dd7909f362ca6b2c2b85695a3ec59426b1465118065;
				// PHPSESSID=f3149kd7604aaqmoejo2nb00a1; gbook-0=true
				// Host:home.188lu.us
				// Origin:http://www.1111av.co
				// Referer:http://www.1111av.co/detail/guestbook.html
				// Upgrade-Insecure-Requests:1
				// User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2)
				// AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75
				// Safari/537.36 QQBrowser/4.1.4132.400
				// Query String Parameters
				// view source
				// view URL encoded
				// s:Gb-Insert
				// Form Data
				// view source
				// view URL encoded
				// gb_cid:
				// gb_content:单独
				// sm1:提交留言
				
				Map<String, String> headers = new HashMap<String, String>();
//				headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//				headers.put("Accept-Encoding", "gzip, deflate");
//				headers.put("Accept-Language", "zh-CN,zh;q=0.8");
//				headers.put("Cache-Control", "max-age=0");
//				headers.put("Connection", "keep-alive");
//				headers.put("Content-Type", "application/x-www-form-urlencoded");
//				headers.put("Cookie", "__cfduid=dd7909f362ca6b2c2b85695a3ec59426b1465118065;PHPSESSID=f3149kd7604aaqmoejo2nb00a1; gbook-0=true");
//				headers.put("Host", "home.188lu.us");
//				headers.put("Origin", "http://www.1111av.co");
//				headers.put("Referer", "http://www.1111av.co/detail/guestbook.html");
//				headers.put("Upgrade-Insecure-Requests", "1");
//				headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75Safari/537.36 QQBrowser/4.1.4132.400");
 
				 
				RequestQueue requestQueue = Volley.newRequestQueue(QianBaiLuGuestBookActivity.this);
				String gb_content = null;
				try {
					gb_content = URLEncoder.encode(edit_content.getText().toString(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String sm1 = null;
				try {
					sm1 = URLEncoder.encode("提交留言", "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String body = "&gb_content="+gb_content+"&sm1="+sm1+"&gb_cid=";
				final Map<String, String> params = new HashMap<String, String>();  
				params.put("gb_cid","");  
				params.put("gb_content", gb_content);  
				params.put("sm1",sm1);  
				System.out.println(url+body);
				
//				StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, 
//						new Response.Listener<String>() {
//					@Override
//					public void onResponse(String response) {
//						System.out.println(response);
//						Toast.makeText(QianBaiLuGuestBookActivity.this, "留言成功，我们会尽快审核您的留言!", Toast.LENGTH_SHORT).show();
//					}
//				}, QianBaiLuGuestBookActivity.this )
//				{
//				    @Override
//				    protected Map<String, String> getParams() {
//				        //在这里设置需要post的参数
//				          return params;
//				    }
//				};  
				StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,headers ,params,
						new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						System.out.println(response);
						Toast.makeText(QianBaiLuGuestBookActivity.this, "留言成功，我们会尽快审核您的留言!", Toast.LENGTH_SHORT).show();
					}
				}, QianBaiLuGuestBookActivity.this);  
				requestQueue.add(jsonObjectRequest);
			}
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.BaseFragmentActivity#onErrorResponse(com.
	 * android.volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}

}

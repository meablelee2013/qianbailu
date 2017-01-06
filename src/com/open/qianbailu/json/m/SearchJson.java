/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午5:03:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.json.m;

import java.util.ArrayList;
import java.util.List;

import com.open.qianbailu.bean.SearchBean;
import com.open.qianbailu.json.CommonJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-6下午5:03:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SearchJson extends CommonJson{
	private List<SearchBean> hotlist = new ArrayList<SearchBean>();
	private List<SearchBean> typelist = new ArrayList<SearchBean>();
	private List<SearchBean> resultlist = new ArrayList<SearchBean>();

	public List<SearchBean> getHotlist() {
		return hotlist;
	}

	public void setHotlist(List<SearchBean> hotlist) {
		this.hotlist = hotlist;
	}

	public List<SearchBean> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<SearchBean> typelist) {
		this.typelist = typelist;
	}

	public List<SearchBean> getResultlist() {
		return resultlist;
	}

	public void setResultlist(List<SearchBean> resultlist) {
		this.resultlist = resultlist;
	}
	
	

}

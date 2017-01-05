/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:06:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.json.m;

import java.util.ArrayList;
import java.util.List;

import com.open.qianbailu.bean.m.ShowBean;
import com.open.qianbailu.json.CommonJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5下午5:06:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ShowJson extends CommonJson {
	private List<ShowBean> list = new ArrayList<ShowBean>();
	private String nextTitle;
	private String preTitle;
	private String nextHref;
	private String preHref;
	public List<ShowBean> getList() {
		return list;
	}

	public void setList(List<ShowBean> list) {
		this.list = list;
	}

	public String getNextTitle() {
		return nextTitle;
	}

	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}

	public String getPreTitle() {
		return preTitle;
	}

	public void setPreTitle(String preTitle) {
		this.preTitle = preTitle;
	}

	public String getNextHref() {
		return nextHref;
	}

	public void setNextHref(String nextHref) {
		this.nextHref = nextHref;
	}

	public String getPreHref() {
		return preHref;
	}

	public void setPreHref(String preHref) {
		this.preHref = preHref;
	}

	

}

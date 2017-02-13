/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:50:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.json.m;

import java.util.ArrayList;
import java.util.List;

import com.open.qianbailu.bean.m.MovieBean;
import com.open.qianbailu.json.CommonJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:50:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MovieJson extends CommonJson {
	private List<MovieBean> list = new ArrayList<MovieBean>();
	private int maxpageno;

	public List<MovieBean> getList() {
		return list;
	}

	public void setList(List<MovieBean> list) {
		this.list = list;
	}

	public int getMaxpageno() {
		return maxpageno;
	}

	public void setMaxpageno(int maxpageno) {
		this.maxpageno = maxpageno;
	}

}

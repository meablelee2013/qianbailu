/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午2:31:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.json.db;

import java.util.ArrayList;
import java.util.List;

import com.open.qianbailu.bean.db.OpenDBBean;
import com.open.qianbailu.json.CommonJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午2:31:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class OpenDBJson extends CommonJson {
	private List<OpenDBBean> list = new ArrayList<OpenDBBean>();

	public List<OpenDBBean> getList() {
		return list;
	}

	public void setList(List<OpenDBBean> list) {
		this.list = list;
	}

}

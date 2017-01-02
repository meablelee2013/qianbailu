package com.open.qianbailu.json.m;

import java.util.ArrayList;
import java.util.List;

import com.open.qianbailu.bean.m.NavMBean;
import com.open.qianbailu.json.CommonJson;

/**
 * 
 * @author think
 *
 */
public class NavMJson extends CommonJson {
	private List<NavMBean> list = new ArrayList<NavMBean>();

	public List<NavMBean> getList() {
		return list;
	}

	public void setList(List<NavMBean> list) {
		this.list = list;
	}
	
	
 
}

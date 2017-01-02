package com.open.qianbailu.bean.m;

import java.util.ArrayList;
import java.util.List;

import com.open.qianbailu.bean.CommonBean;

public class NavMBean extends CommonBean {
	private String title;
	private String href;
	private List<PicKuFilmBean> picKuL = new ArrayList<PicKuFilmBean>();
	private List<NavMChildBean> list = new ArrayList<NavMChildBean>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<PicKuFilmBean> getPicKuL() {
		return picKuL;
	}

	public void setPicKuL(List<PicKuFilmBean> picKuL) {
		this.picKuL = picKuL;
	}

	public List<NavMChildBean> getList() {
		return list;
	}

	public void setList(List<NavMChildBean> list) {
		this.list = list;
	}

}

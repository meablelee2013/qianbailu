package com.open.qianbailu.bean.m;

import com.open.qianbailu.bean.CommonBean;

public class PicKuFilmBean extends CommonBean {
	private String title;
	private String href;
	private String src;
	private int type;//0 m 1pc

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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

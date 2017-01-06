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

import android.widget.ImageView;
import android.widget.TextView;

import com.open.qianbailu.bean.m.NavMChildBean;
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
public class MovieDetailJson extends CommonJson {
	private List<ShowBean> list = new ArrayList<ShowBean>();
	private String sec_info_intro;

	//下载
	private List<NavMChildBean> listd = new ArrayList<NavMChildBean>();
	private String  movieDetaiImg;
	private String  movie_name;
	private String  movie_type;
	private String  movie_time;
	
	public List<ShowBean> getList() {
		return list;
	}

	public void setList(List<ShowBean> list) {
		this.list = list;
	}

	public String getSec_info_intro() {
		return sec_info_intro;
	}

	public void setSec_info_intro(String sec_info_intro) {
		this.sec_info_intro = sec_info_intro;
	}

	public List<NavMChildBean> getListd() {
		return listd;
	}

	public void setListd(List<NavMChildBean> listd) {
		this.listd = listd;
	}

	public String getMovieDetaiImg() {
		return movieDetaiImg;
	}

	public void setMovieDetaiImg(String movieDetaiImg) {
		this.movieDetaiImg = movieDetaiImg;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public String getMovie_type() {
		return movie_type;
	}

	public void setMovie_type(String movie_type) {
		this.movie_type = movie_type;
	}

	public String getMovie_time() {
		return movie_time;
	}

	public void setMovie_time(String movie_time) {
		this.movie_time = movie_time;
	}
	 

	

}

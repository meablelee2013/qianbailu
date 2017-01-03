/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:45:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.bean.m;

import com.open.qianbailu.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * <div class="movieBlock"> 01-03 10:44:09.597: I/System.out(15800): <div
 * class="movieList"> 01-03 10:44:09.597: I/System.out(15800): <a
 * href="vshow.php?id=11136"><img
 * src="//mi3.1100lu.xyz/m/vod/2016-12-31/586697fa5e66f.jpg"></a> 01-03
 * 10:44:09.597: I/System.out(15800): </div> 01-03 10:44:09.597:
 * I/System.out(15800): <div class="movieDetail float"> 01-03 10:44:09.597:
 * I/System.out(15800): <div class="movieTitle"> 01-03 10:44:09.597:
 * I/System.out(15800): <a href="vshow.php?id=11136">最新HEYZO 1362 性欲爆発-[成宫</a>
 * 01-03 10:44:09.597: I/System.out(15800): </div> 01-03 10:44:09.597:
 * I/System.out(15800): <a class="movieListPlay" href="vshow.php?id=11136">详
 * 情</a> 01-03 10:44:09.597: I/System.out(15800): <div class="movieDetails">
 * 01-03 10:44:09.597: I/System.out(15800): <br>
 * 01-03 10:44:09.597: I/System.out(15800): <br>
 * 01-03 10:44:09.597: I/System.out(15800):
 * <p>
 * 类型：<span>亚洲</span>
 * </p>
 * 01-03 10:44:09.597: I/System.out(15800):
 * <p>
 * 时间：<span>12-31</span>
 * </p>
 * 01-03 10:44:09.597: I/System.out(15800): </div> 01-03 10:44:09.597:
 * I/System.out(15800): </div> 01-03 10:44:09.597: I/System.out(15800): </div>
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3上午10:45:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MovieBean extends CommonBean {
	private String href;
	private String src;
	private String movieTitle;
	private String movieListPlay;
	private String type;
	private String time;

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

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getMovieListPlay() {
		return movieListPlay;
	}

	public void setMovieListPlay(String movieListPlay) {
		this.movieListPlay = movieListPlay;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}

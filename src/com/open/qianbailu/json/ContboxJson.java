/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-13下午1:51:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.json;

import com.open.qianbailu.bean.ContboxBean;


/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-13下午1:51:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ContboxJson extends CommonJson {

	private  ContboxBean  contbox = new ContboxBean();

	public ContboxBean getContbox() {
		return contbox;
	}

	public void setContbox(ContboxBean contbox) {
		this.contbox = contbox;
	}
	
}

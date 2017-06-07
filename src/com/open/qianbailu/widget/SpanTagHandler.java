/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午2:47:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.qianbailu.widget;

import org.xml.sax.XMLReader;

import android.content.Context;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午2:47:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SpanTagHandler implements TagHandler {
	private int sIndex = 0;
    private int eIndex = 0;
    private final Context mContext;
    
    public SpanTagHandler(Context context) {
        mContext = context;
    }

	/* (non-Javadoc)
	 * @see android.text.Html.TagHandler#handleTag(boolean, java.lang.String, android.text.Editable, org.xml.sax.XMLReader)
	 */
	@Override
	public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
		// TODO Auto-generated method stub
		if (tag.toLowerCase().equals("img")) {
            if (opening) {
                sIndex = output.length();
            } else {
                eIndex = output.length();
                output.setSpan(new LinkClickableSpan(mContext,output.subSequence(sIndex, sIndex).toString()), sIndex, eIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } 
	}

}

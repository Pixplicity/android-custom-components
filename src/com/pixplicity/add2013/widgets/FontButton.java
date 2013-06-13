package com.pixplicity.add2013.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.pixplicity.add2013.util.FontUtil;

public class FontButton extends Button {

	public FontButton(Context context) {
		super(context);
		FontTextView.init(this, context, null, 0);
	}

	public FontButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontTextView.init(this, context, attrs, 0);
	}

	public FontButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		FontTextView.init(this, context, attrs, defStyle);
	}

	public void setTypeface(String typeface) {
		FontUtil.setTypeface(this, typeface);
	}

}
